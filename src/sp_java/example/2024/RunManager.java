package com.lgcns.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;

public class RunManager {
	public static String getConsoleOneLine() {
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();
		sc.close();
		return line;
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//String in = getConsoleOneLine().toLowerCase();
		//String[] in_ary = in.split(" ");
		DictManager.loadDictMapFromFile("DICTIONARY.TXT");
		StopwordManager.loadStopMapFromFile("STOPWORD.TXT");
		ModelManager.loadJsonFile("MODELS.JSON");
		
		PreProcessingServer svr = new PreProcessingServer();
		svr.start();
	}

}
