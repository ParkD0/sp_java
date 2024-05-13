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
	private ServerCommandInfoJson serverCommandInfoJson;
	private DeviceInfoJson deviceInfoJson;
	
	public void run() throws Exception{
		loadServerCommandInfo();
		loadDeviceInfo();
		
		Server svr = createServer();
		svr.start();
	}
	
	private void loadServerCommandInfo() throws Exception {
		serverCommandInfoJson = gson.fromJson(String.join("", Files.readAllLines(Paths.get("INFO/SERVER_COMMAND>JSON"))),
				ServerCommandInfoJson.class);
	}
	
	private void loadDeviceInfo() throws Exception {
		deviceInfoJson = gson.fromJson(String.join("", Files.readAllLines(Paths.get("INFO/DEVICE.JSON"))),
				DeviceInfoJson.class);
	}
	
	private Server createServer() {
		Server svr = new Server();
		ServerConnector http = new ServerConnector(svr);
		http.setHost("127.0.0.1");
		http.setPort(8010);
		svr.addConnector(http);
		
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/*");
		context.addServlet(new ServletHolder(new NodeServlet(serverCommandInfoJson, deviceInfoJson)), "/*");
		svr.setHandler(context);
			
		return svr;
	}
	
	
}