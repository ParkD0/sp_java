package com.test;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;
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
import com.test.ServerCommandInfoJson.ServerCommandInfo;


public class NodeServlet extends HttpServlet{
	private Gson gson = new Gson();
	private Map<String, ServerCommandInfo> svrCmdInfoMap;
	private Map<String, DeviceInfo> deviceInfoMap;
	
	public NodeServlet(Map<String, ServerCommandInfo> svrCmdInfoMap, Map<String, DeviceInfo> deviceInfoMap) {
		this.svrCmdInfoMap = svrCmdInfoMap;
		this.deviceInfoMap = deviceInfoMap;
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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String reqString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		
		CommandRequest reqData = this.gson.fromJson(reqString, CommandRequest.class);
		
		switch(request.getPathInfo()) {
		case "/fromServer":
			List<String> result = new ArrayList<>();
			
			for(String targetDevice : reqData.getTargetDevice()) {
				DeviceInfo deviceInfo = this.deviceInfoMap.get(targetDevice);
				String forwardCmd = this.svrCmdInfoMap.get(reqData.getCommand()).getForwardCommand();
				
				String cmdResponse = null;
				try {
					cmdResponse = sendPostReqeust(
							String.format("http://%s:%d/fromEdge", deviceInfo.getHostname(), deviceInfo.getPort()),
							new CommandRequest(forwardCmd, null, reqData.getParameter()).toJson(gson));							
				} catch (Exception e) {
					e.printStackTrace();
				}
			    CommandResponse responseFromDevice = gson.fromJson(cmdResponse, CommandResponse.class);
			    result.addAll(responseFromDevice.getResult());
			}
			response.setStatus(200);
			response.getWriter().write(new CommandResponse(result).toJson(gson));
			break;
		}
	}
}
