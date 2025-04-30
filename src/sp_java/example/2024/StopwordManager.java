package com.lgcns.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StopwordManager {
	private static HashMap<String, String> stopwordMap;
	
	public static void loadStopMapFromFile(String filePath) {
		stopwordMap = new HashMap<>();
		String line = null;
		
		try {
			FileReader fileReader = new FileReader(filePath);
			BufferedReader bufferdReader = new BufferedReader(fileReader);
			
			while((line = bufferdReader.readLine()) != null) {
				stopwordMap.put(line,  line);
			}
			bufferdReader.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public static boolean iscontain(String s) {
		if(stopwordMap.containsValue(s))
			return true;
		else
			return false;
	}
}
