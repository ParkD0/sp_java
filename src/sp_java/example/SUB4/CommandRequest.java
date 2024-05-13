package com.test;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CommandRequest extends Command{
	String command;
	
	@SerializedName("targetDevice")
	List<String> targetDevice;
	
	String parameter;
	
	public CommandRequest(String command, List<String> targetDevice, String param) {
		this.command = command;
		this.targetDevice = targetDevice;
		this.parameter = param;
	}
	
	public String getCommand() {
		return command;
	}
	public List<String> getTargetDevice() {
		return targetDevice;
	}
	public String getParameter() {
		return parameter;
	}
}