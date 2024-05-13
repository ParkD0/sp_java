package com.test;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ServerCommandInfoJson {
	@SerializedName("serverCommandInfo")
	List<ServerCommandInfo> serverCommandInfoList;
	
	public ServerCommandInfoJson(List<ServerCommandInfo> serverCommandInfoList) {
		this.serverCommandInfoList = serverCommandInfoList;
	}
	
	public List<ServerCommandInfo> getServerCommandInfoList() {
		return serverCommandInfoList;
	}
	
	public class ServerCommandInfo {
		String command;
		String forwardCommand;
		
		public ServerCommandInfo(String command, String forwardCommand) {
			this.command = command;
			this.forwardCommand = forwardCommand;
		}
		
		public String getCommand() {
			return command;
		}

		public String getForwardCommand() {
			return forwardCommand;
		}
	}
}
