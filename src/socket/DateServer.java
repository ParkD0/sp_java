package socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;

public class DateServer extends Thread{
	ServerSocket listener;
	boolean quit;
	
	public DateServer() {
		quit = false;
	}
	
	@Override
	public void run() {
		try {
			listener = new ServerSocket(9090);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Socket socket = null;
		while(!quit) {
			try {
				socket  = listener.accept();
				String date = new Date().toString();
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.println(date);
			} catch(SocketException e) {
				//e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(socket != null)
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void close() {
		try {
			quit = true;
			listener.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}