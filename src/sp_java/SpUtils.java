package sp_java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class SpUtils {
	
	public static String getConsoleOneLine() {
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine(); //next는 공백으로도 끊음
		sc.close();
		return line;
	}
	
	////////////////////////////////////////////////////////////
	//related file
	public static void getFileListRecursive(String path, ArrayList<File> fileList) {
		File directory = new File(path);
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isDirectory()) {
				getFileListRecursive(file.getPath(), fileList);
			}
			else {
				fileList.add(file);
			}
		}
	}
	
	public static void makeDirRecursive(String path) {
		File dst = new File(path);
		if(!dst.exists() ) {
			dst.mkdirs();
		}
	}
	
	public static String getCurAbsolutePath() {
		Path currentPath = Paths.get("");
        String absolutePath = currentPath.toAbsolutePath().toString();
        return absolutePath;
	}
	
	public static void printFile(String fileName) {
		String line = null;
		
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferdReader = new BufferedReader(fileReader);
			
			while((line = bufferdReader.readLine()) != null) {
				System.out.println(line);
			}
			bufferdReader.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void appendFile(String path, String content) {
		FileWriter fw;
		 try {
			 fw = new FileWriter(path, true);
			 fw.write(content);
			 fw.flush();
			 fw.close();
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	}
	
	public static void writeFile(String path, String content) {
		FileWriter fw;
		 try {
			 fw = new FileWriter(path);
			 fw.write(content);
			 fw.flush();
			 fw.close();
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	}
	
	public static void CopyFile(String inputFile, String outputFile) {
		final int BUFFER_SIZE = 4096;
		int readLen;
		//String[] paths = outputFile.split("\\\\"); //경로는 split \\\\ 으로 해야함
		try {
			InputStream inputStream = new FileInputStream(inputFile);
			OutputStream outputStream = new FileOutputStream(outputFile);
			
			byte[] buffer = new byte[BUFFER_SIZE];
			while((readLen = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, readLen);;
			}
			inputStream.close();
			outputStream.close();
		}
		catch ( FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static boolean moveFile(String newPath, String oldPath) {
        File oldFile = new File(oldPath);
        File newFile = new File(newPath);
        boolean result = oldFile.renameTo(newFile);
        return result;
	}
	
	public static synchronized void writeLog(String dirPath, String...paramArray) throws IOException
    {
		File destFolder = new File(dirPath);
		if(!destFolder.exists()) {
		    destFolder.mkdirs(); 
		}

		LocalDateTime now = LocalDateTime.now();

        String filePath = String.format("%s\\LOG_%02d%02d%02d.TXT", dirPath, now.getHour(), now.getMinute(), now.getSecond());

        PrintWriter out = new PrintWriter(new FileWriter(filePath, true));        
        String strDT = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")); 
        String strParam = String.join(" | ", paramArray);
        String strLog = String.format("[%s] %s",strDT, strParam);
        out.println(strLog);
        out.close();         
    }
	
	////////////////////////////////////////////////////////////
	//related time
	public static String getDateFormat(String format) {//if format "yyyy-MM-dd HH:mm:ss.SS" use, same result getDate()
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		String strNowDate = simpleDateFormat.format(date); 
		
		return strNowDate;
	}
	
	public static String getDate() {
		LocalDateTime now = LocalDateTime.now();
		String strDT = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
		return strDT;
	}
	
	public static long diffTimeSec(String strTime2, String strTime1) { //use strTime format yyyyMMddHHmmss
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		
		long gap = 0;
		try {
			java.util.Date date1 = transFormat.parse(strTime1);
			java.util.Date date2 = transFormat.parse(strTime2);
			gap = date2.getTime() - date1.getTime();
		} catch ( ParseException e) {
			e.printStackTrace();
		}

		return gap / 1000;
	}
	
	////////////////////////////////////////////////////////////
	//related hasing encoding decoding
	public static String base64Encoding(String src){
		Encoder encoder = Base64.getEncoder();
		String encodedStr= null;
		try {
			encodedStr = encoder.encodeToString(src.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return encodedStr;
	}
	public static String base64Decoding(String src) {
		Decoder decoder = Base64.getDecoder();
		byte[] decodedBytes = decoder.decode(src);
		String decodedString= null;
		try {
			decodedString = new String(decodedBytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return decodedString;
	}
	
	public static String sha256Hasing(String input) {
		MessageDigest mDigest = null;
		try {
			mDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] result = mDigest.digest(input.getBytes());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < result.length; i ++) {
			sb.append(Integer.toString( (result[i] & 0xFF)+0x100, 16).substring(1));
		}
		return sb.toString();
	}
	
	////////////////////////////////////////////////////////////
	//related process
	//usage String output = ProcessExample.getProcessOutput(Arrays.asList("processname", "arg1", "arg2"));
	public static String getProcessOutput(List<String> cmdList) throws IOException, InterruptedException{
		ProcessBuilder builder = new ProcessBuilder(cmdList);
		Process process = builder.start();
		InputStream psout = process.getInputStream();
		byte[] buffer = new byte[1024];
		psout.read(buffer);
		return (new String(buffer));
	}
}
