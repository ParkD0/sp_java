package socket;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/* usage
FileCopyServer fileCopyServer = new FileCopyServer();		
Thread threadSocket = new Thread(fileCopyServer);
threadSocket.start();

fileCopyServer.close();
threadSocket.join();
*/

public class FileCopyServer  implements Runnable {
	public static final int BUF_SIZE = 4096;
	private ServerSocket serverSocket;
	private boolean quit;
	private int port;
	
	FileCopyServer(int port) {
		this.port = port;
		this.quit = false;
	}
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	
	@Override
	public void run() {
		serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			
			byte[] buffer = new byte[BUF_SIZE];
			int length;
			
			while (!quit) {
				Socket socket = null;
				DataInputStream is = null;
				try {
					socket = serverSocket.accept();
					
					is = new DataInputStream(socket.getInputStream());
					while (true) {
						String fileName = is.readUTF();
						int fileSize = is.readInt();
						
						while (fileSize > 0) {
							length = is.read(buffer, 0, Math.min(fileSize, buffer.length));
							fileSize -= length;
							writeFile(fileName, buffer, 0, length);
						}
					}
				} catch(SocketException e) {
					// When Socket Closed
				}
				catch (EOFException e) {
					// Do Nothing
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (socket != null) { socket.close(); }
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (serverSocket != null)
					serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void writeFile(String fileName, byte[] buffer, int offset, int length) throws IOException {
		FileOutputStream fw = null;
		try {
			fw = new FileOutputStream("..//SERVER//" + fileName, true);
			fw.write(buffer, offset, length);
		} finally {
			if (fw != null) { fw.close(); }
		}
	}

	public synchronized void close() {
		quit = true;
		
		try {
			if (serverSocket != null) { 
				serverSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

