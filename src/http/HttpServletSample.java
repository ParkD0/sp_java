package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import sp_java.json.JsonWrapper;
import sp_java.json.Lesson;
import sp_java.json.Student;

public class HttpServletSample  extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//System.out.println("Request : "+ req.getRequestURL());
		String [] words = req.getPathInfo().toString().split("/"); 
		String command = words[1];
		
		//http://127.0.0.1:prot/REPORT/~~
		if (command.equals("REPORT")) {
            final String managerId = words[2];
		
            JsonObject resJson = new JsonObject();
            resJson.addProperty("Result", "Ok");
            resJson.addProperty("ManagerID", managerId);
            
			res.setStatus(200);
			res.setContentType("application/json");
			res.getWriter().print(resJson.toString());
			res.getWriter().flush();			
		} 
		else if(command.equals("LESSON")) { //http://127.0.0.1:prot/LSESSON/~~
			JsonObject resJson = new JsonObject();
			resJson.addProperty("Result", "Ok");
			
			Lesson lesson = new Lesson("Math");
			JsonWrapper<Lesson> jw = new JsonWrapper<Lesson>() {};
			resJson.add("Lesson", jw.objToJsonObj(lesson));
			
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
		///////////////////////////////////////////////////////////////////////////////////////////////
		
		String [] words = req.getPathInfo().toString().split("/"); 
		String command = words[1];
		
		switch(command) {
		case "FINISH":
			resJson.addProperty("Result", "Ok");
			break;
		case "FAIL":
			resJson.addProperty("Result", "Ok");
			break;
		}		
		
		res.setStatus(200);
		res.setContentType("application/json");
		res.getWriter().print(resJson.toString());
		res.getWriter().flush();
	}
}
