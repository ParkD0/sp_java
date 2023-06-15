package http;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class HttpUseParamServer {
	Server server;
	int port;

	HttpUseParamServer(int port) {
		this.server = new Server();
		this.port = port;
	}
	
	public void start() {
		server = new Server();
		ServerConnector http = new ServerConnector(server);
		http.setHost("127.0.0.1");
		http.setPort(this.port);
		server.addConnector(http);
	
		HttpUseParamServlet servlet = new HttpUseParamServlet("Test Server");
		ServletHolder holder = new ServletHolder(servlet);
		ServletHandler servletHandler = new ServletHandler();
		servletHandler.addServletWithMapping(holder, "/*");
		server.setHandler(servletHandler);
		
		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void quit() throws Exception {
		server.stop();
		server.join();
	}
}
