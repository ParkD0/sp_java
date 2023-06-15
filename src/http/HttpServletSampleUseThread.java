package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import sp_java.SpUtils;

public class HttpServletSampleUseThread {
	static ReentrantLock lockSeq = new ReentrantLock();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//System.out.println("Request : "+ req.getRequestURL());
		
		String [] words = req.getPathInfo().toString().split("/"); 
		String command = words[1];
		
		if (command.equals("REPORT")) {
            final String managerId = words[2];

            String report = "";

            lockSeq.lock();
            final String contentId = HttpShare.increaseSeqNo().toString();
            lockSeq.unlock();

            JsonObject resJson = new JsonObject();
            resJson.addProperty("Result", "Ok");
            resJson.addProperty("ReportID", contentId);
            resJson.addProperty("Report", report);

            HttpShare.storeContent(contentId, report);

            // start timeout thread
            HttpShare.startTimer(Integer.parseInt(contentId), new Runnable() {
				@Override
				public void run() {
					System.out.println("REPORT ID : " + contentId);
					try {
						Thread.sleep(5000); // 5sec
						System.out.println("TIMEOUT : " + contentId);
						
						if (HttpShare.saveContentFile(contentId, "TIMEOUT"))
						{
							HttpShare.removeContent(contentId);
						    SpUtils.writeLog(managerId, "TIMEOUT", contentId);
						}						
					} catch (InterruptedException e) {
						System.out.println("Timeout Canceled - " + contentId); 
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
            });

            // LOG
            SpUtils.writeLog(managerId, command, contentId);
			
			res.setStatus(200);
			res.setContentType("application/json");
			res.getWriter().print(resJson.toString());
			res.getWriter().flush();			
		}
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//System.out.println("Request : "+ req.getRequestURL());
		
		Gson gson = new Gson();
		JsonObject resJson = new JsonObject();
		
		///////////////////////////////////////////////////////////////////////////////////////////////
		///read body
        BufferedReader input = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String buffer;
        StringBuilder sb = new StringBuilder();
        while ((buffer = input.readLine()) != null) {
        	sb.append(buffer + "\n");
        }
        input.close();
        String strBody = sb.toString();
		
		JsonObject jsonBody = gson.fromJson(strBody, JsonObject.class);
		String managerId = jsonBody.get("ManagerID").getAsString();
		String reportId = jsonBody.get("ReportID").getAsString();
		///////////////////////////////////////////////////////////////////////////////////////////////
		
		String [] words = req.getPathInfo().toString().split("/"); 
		String command = words[1];
		
		switch(command) {
		case "FINISH":
			resJson.addProperty("Result", "Ok");
			SpUtils.writeLog(managerId, command, reportId);
			break;
		case "FAIL":
			resJson.addProperty("Result", "Ok");
			SpUtils.writeLog(managerId, command, reportId);
			break;
		}		
		
		res.setStatus(200);
		res.setContentType("application/json");
		res.getWriter().print(resJson.toString());
		res.getWriter().flush();
	}
}
