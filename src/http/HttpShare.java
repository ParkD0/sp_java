package http;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class HttpShare {
	private static Integer seqNo = 0;
	
	private static ConcurrentHashMap<Integer, String> contentMap = new ConcurrentHashMap<Integer, String>(); 
	private static ConcurrentHashMap<Integer, Thread> threadMap = new ConcurrentHashMap<Integer, Thread>();
	
    public static Integer increaseSeqNo() {
        seqNo++;
        return seqNo;
    }
    
    public static void storeContent(String contentId, String content) {
    	contentMap.put(Integer.parseInt(contentId), content);
    }
    
    public static void removeContent(String contentId) {
        System.out.println("Remove : " + contentId);
        contentMap.remove(Integer.parseInt(contentId)); 
    }   
    
    public static boolean saveContentFile(String contentId, String type) throws IOException {
        if (!contentMap.containsKey(Integer.parseInt(contentId)))
            return false;

        String path = "..\\SERVER\\REPORT";
		File destFolder = new File(path);
		if(!destFolder.exists()) {
		    destFolder.mkdirs(); 
		}
		       
        String filename = String.format("%s\\%s_%s.TXT", path, type, contentId);
        
		FileWriter fw = null;
		fw = new FileWriter(filename);			
		fw.write(contentMap.get(Integer.parseInt(contentId)).toString());
		fw.close();
		
        return true;
    }   
    
	public static void startTimer(int id, Runnable task) {
		threadMap.put(id, new Thread(task));
		threadMap.get(id).start(); 
	}
	
	public static void cancelTimer(int id) {
		threadMap.get(id).interrupt();
	}
}
