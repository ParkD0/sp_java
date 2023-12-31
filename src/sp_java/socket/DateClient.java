package sp_java.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class DateClient extends Thread{
	@Override
	public void run() {
		Socket s;
		try {
			s = new Socket("127.0.0.1", 9090);
			BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String answer = input.readLine();
			System.out.println(answer);
			s.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}