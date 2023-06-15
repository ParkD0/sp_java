package sp_java.http_server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class ServerSample {
	Server server;
	int port;
	
	ServerSample(int port) {
		this.server = new Server();
		this.port = port;
	}
	
	public void start() {
		server = new Server();
		ServerConnector http = new ServerConnector(server);
		http.setHost("127.0.0.1");
		http.setPort(this.port);
		server.addConnector(http);

		//ServletHandler servletHandler = new ServletHandler();
		//servletHandler.addServletWithMapping(ServletSample.class, "/*");
		//server.setHandler(servletHandler);
		
		ServletSample servlet = new ServletSample("Test Server");
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
	
	public void join() throws Exception {
		server.stop();
		server.join();
	}
}
