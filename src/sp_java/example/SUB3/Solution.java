package com.test;

import java.io.File;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.google.gson.Gson;

import com.test.ServerCommandInfoJson.ServerCommandInfo;
import com.test.DeviceInfoJson.DeviceInfo;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Solution {
	private Gson gson;
	private Map<String, ServerCommandInfo> serverCommandInfoMap;
	private Map<String, DeviceInfo> deviceInfoMap;
	
	public void run() throws Exception{
		loadServerCommandInfo();
		loadDeviceInfo();
		
		Server svr = createServer();
		svr.start();
	}
	
	private void loadServerCommandInfo() throws Exception {
		ServerCommandInfoJson svrCmdInfoJson = gson.fromJson(
				String.join("", Files.readAllLines(Paths.get("INFO/SERVER_COMMAND.JSON"))),
				ServerCommandInfoJson.class);
		
		this.serverCommandInfoMap = new HashMap<>();
		
		for(ServerCommandInfo serverCommandInfo : svrCmdInfoJson.getServerCommandInfoList()) {
			this.serverCommandInfoMap.put(serverCommandInfo.getCommand(), serverCommandInfo);
		}
	}
	
	private void loadDeviceInfo() throws Exception {
		DeviceInfoJson deviceInfoJson = gson.fromJson(
				String.join("", Files.readAllLines(Paths.get("INFO/DEVICE_INFO.JSON"))),
				DeviceInfoJson.class);
		
		this.deviceInfoMap = new HashMap<>();
		
		for(DeviceInfo deviceInfo : deviceInfoJson.getDeviceInfoList()) {
			this.deviceInfoMap.put(deviceInfo.getDevice(), deviceInfo);
		}
	}
	
	private Server createServer() {
		Server svr = new Server();
		ServerConnector http = new ServerConnector(svr);
		http.setHost("127.0.0.1");
		http.setPort(8010);
		svr.addConnector(http);
		
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/*");
		context.addServlet(new ServletHolder(new NodeServlet(serverCommandInfoMap, deviceInfoMap)), "/*");
		svr.setHandler(context);
			
		return svr;
	}
	
	
}