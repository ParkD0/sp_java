package com.lgcns.test;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;

public class ModelManager {
	private static ModelAry modelAry;
	
	public static void loadJsonFile(String filePath) {
		Gson gson = new Gson();
		// json file to object
		 try {
			FileReader reader = new FileReader(filePath);
			ModelAry modelAryT = gson.fromJson(reader, ModelAry.class);
			System.out.println(modelAry);
			modelAry = modelAryT;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
