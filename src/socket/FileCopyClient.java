package socket;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class FileCopyClient {
	int port;
	FileCopyClient(int port) {
		this.port = port;
	}

	public void connect() {
		Socket socket = null;
		DataOutputStream os = null;
		try {
			socket = new Socket("127.0.0.1", port);
			os = new DataOutputStream(socket.getOutputStream());
			
			byte[] buffer = new byte[4096];
			int length;
			
			// get all the files from a directory
			File directory = new File("..//");
			File[] fList = directory.listFiles();
			for (File file : fList) {
				if (file.isFile()) {
					os.writeUTF(file.getName());
					os.writeInt((int) file.length());
					
					FileInputStream is = null;
					try {
						is = new FileInputStream(file.getPath());
						while ((length = is.read(buffer)) != -1) {
							os.write(buffer, 0, length);
						}
					} finally {
						if (is != null) { is.close(); }
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) 
					os.close();
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
