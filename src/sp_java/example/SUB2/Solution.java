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

public class Solution {
	
	private Map<String, String> commandMap;
	
	public void run() throws Exception{
		loadCommandMap();
		
		try (Scanner sc = new Scanner(System.in)) {
			String[] reqAry = sc.next().split("#");
			
			String command = reqAry[0];
			String[] targetDeviceAry = reqAry[1].split(",");
			String param = reqAry[2];
			
			//���Ͽ� ���� 500ms �����ѵ� ����
			List<String> resList = new ArrayList<>();
			for (String targetDevice : targetDeviceAry) {
				writeFile(String.format("DEVICE/REQ_TO_%S.TXT", targetDevice), 
						String.format("%s#%s", this.commandMap.get(command), param));
				
				Thread.sleep(500);
				
				String res = readFile(String.format("DEVICE/RES_FORM_$S.TXT", targetDevice));
				resList.add(res);
			}
			
			System.out.println(String.join(",", resList));		
		}
	}
	
	private void loadCommandMap() throws Exception {
		this.commandMap = new HashMap<>();
		
		try (Scanner sc = new Scanner(new File("INFO/SERVER_COMMAND.TXT"))) {
			while(sc.hasNext()) {
				String[] stringAry = sc.next().split("#");
				this.commandMap.put(stringAry[0], stringAry[1]);
			}
		}
	}
	
	private void writeFile(String fileName, String content) throws Exception {
		Files.write(Paths.get(fileName), (content + "\n").getBytes(), StandardOpenOption.CREATE);
	}
	
	private String readFile(String fileName) throws Exception {
		return Files.readAllLines(Paths.get(fileName)).get(0);
	}
	
}