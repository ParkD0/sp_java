package http;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

public class HttpServerSample {
	int port;
	Server server;
	
	public HttpServerSample(int port) {
		this.port = port;
	}
	
	public void run() {
		server = new Server();
		ServerConnector http = new ServerConnector(server);
		http.setHost("127.0.0.1");
		http.setPort(this.port);
		server.addConnector(http);

		ServletHandler servletHandler = new ServletHandler();
		servletHandler.addServletWithMapping(HttpServletSample.class, "/*");
		server.setHandler(servletHandler);

		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public void quit() {
		try {
			server.stop();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
