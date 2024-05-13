package com.test;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpHeader;

import com.google.gson.Gson;
import com.test.DeviceInfoJson.DeviceInfo;
import com.test.DeviceInfoJson.DeviceInfo.Device;
import com.test.ServerCommandInfoJson.ServerCommandInfo;



public class NodeServlet extends HttpServlet{
	private Gson gson = new Gson();
	private ServerCommandInfoJson svrCmdInfoJson;
	private DeviceInfoJson deviceInfoJson;
	
	private Map<String, Long> parallelCntMap = new ConcurrentHashMap<>();
	private Map<String, Set<String>> processingMap = new ConcurrentHashMap<>();
	
	
	public NodeServlet(ServerCommandInfoJson svrCmdInfoJson, DeviceInfoJson deviceInfoJson) {
		this.svrCmdInfoJson = svrCmdInfoJson;
		this.deviceInfoJson = deviceInfoJson;
		
		for(DeviceInfo deviceInfo : deviceInfoJson.getDeviceInfoList()) {
			for(Device device : deviceInfo.getDeviceList()) {
				this.parallelCntMap.put(device.getDevice(), deviceInfo.getParallelProcessingCount());
				this.processingMap.put(device.getDevice(), new HashSet<String>());
				
			}
		}
	}
	
	private String sendPostReqeust(String url, String content) throws Exception {
		HttpClient httpClient = new HttpClient();
		httpClient.start();
		try {
			org.eclipse.jetty.client.api.Request req = httpClient.POST(url);
			req.header(HttpHeader.CONTENT_TYPE, "application/json");
			req.content(new StringContentProvider(content, "utf-8"));
			ContentResponse res = req.send();
			return new String(res.getContent());
		}catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			httpClient.stop();
		}
		return null;
	}
	
	private List<String> chainedDeviceCommand(String targetDevice, String command, String requestedParam) {
		String deviceType = deviceInfoJson.getType(targetDevice);
		Device device = deviceInfoJson.getDevice(targetDevice);
		List<String> chainedCommand = svrCmdInfoJson.getForwardCommand(command, deviceType);
		
		String param = requestedParam;
		List<String> resultList = null;
		
		for(String chainedCommandString : chainedCommand) {
			while (this.processingMap.get(targetDevice).size() >= this.parallelCntMap.get(targetDevice)) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			while (this.processingMap.get(targetDevice).contains(chainedCommandString)) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			this.processingMap.get(targetDevice).add(chainedCommandString);
			
			String commandResponse = null;
			try {
				commandResponse = sendPostReqeust(
						String.format("http://%s:%d/fromEdge", device.getHostname(), device.getPort()),
						new CommandRequest(chainedCommandString, null, param).toJson(gson));
						
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			CommandResponse responseFromDevice = gson.fromJson(commandResponse, CommandResponse.class);
			resultList = responseFromDevice.getResult();
			param = resultList.get(0);
			
			this.processingMap.get(targetDevice).remove(chainedCommandString);
		}
		return resultList;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String reqData = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		
		CommandRequest cmdReq = this.gson.fromJson(reqData, CommandRequest.class);
		
		
		
		switch(request.getPathInfo()) {
		case "/fromServer":
			List<CompletableFuture<List<String>>> deviceCommandFutureList = new ArrayList<>();
			List<String> result = new ArrayList<>();
			
			for (String targetDevice : cmdReq.getTargetDevice()) {
				CompletableFuture<List<String>> deviceCommandFuture = CompletableFuture.supplyAsync(()->{
					return chainedDeviceCommand(targetDevice, cmdReq.getCommand(), cmdReq.getParameter());
				});
				deviceCommandFutureList.add(deviceCommandFuture);
			}
			
			for(CompletableFuture<List<String>> deviceCommandFuture : deviceCommandFutureList) {
				try {
					result.addAll(deviceCommandFuture.get());
				}catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
			
			response.setStatus(200);
			response.getWriter().write(new CommandResponse(result).toJson(gson));
		}
	}
}
