package com.test;

import com.google.gson.Gson;

public class Command {
	public String toJson(Gson gson) {
		return gson.toJson(this);
	}
}
