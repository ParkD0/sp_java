package com.lgcns.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DictManager {
	private static HashMap<String, Dict> dictMap;
	
	public static void loadDictMapFromFile(String filePath) {
		dictMap = new HashMap<>();
		String line = null;
		
		try {
			FileReader fileReader = new FileReader(filePath);
			BufferedReader bufferdReader = new BufferedReader(fileReader);
			
			while((line = bufferdReader.readLine()) != null) {
				Dict dict = new Dict();
				String[] dict_ary = line.split("#");
				dict.token = new String(dict_ary[0]);
				dict.vector = new String(dict_ary[1]);
				dictMap.put(dict.token, dict);
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
	
	public static Dict getDict(String s) {
		Dict d = dictMap.get(s);
		return d;
	}

}
