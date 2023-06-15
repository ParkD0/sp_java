package sp_java.http_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletSample extends HttpServlet {
	private String name;
	private static final long serialVersionUID = 1L;
	
	ServletSample(String name) {
		this.name = name;
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		if (req.getRequestURI().equals("/mypath")) {
			res.setStatus(200);
			res.getWriter().write("Hello - Get!");
		}
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		if (req.getRequestURI().equals("/mypath")) {
			BufferedReader input = new BufferedReader(new InputStreamReader(req.getInputStream()));
	        String buffer;
	        StringBuilder sb = new StringBuilder();
	        
	        while ((buffer = input.readLine()) != null) {
	            sb.append(buffer + "\n");
	        }
	        
	        String strBody = sb.toString();
	        System.out.print("client say / ");
	        System.out.println(strBody);
	        
	        res.setStatus(200);
	        res.getWriter().write("Hello! - Post");
		}
	}
}
