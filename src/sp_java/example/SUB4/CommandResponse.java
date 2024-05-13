package com.test;

import java.util.List;

public class CommandResponse extends Command{
	List<String> result;
	
	public CommandResponse(List<String> result) {
		this.result = result;
	}

	public List<String> getResult() {
		return result;
	}
}
