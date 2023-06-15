package http;

import java.util.HashMap;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpMethod;

public class HttpClientSample {
	public static void printRes(ContentResponse res) {
		System.out.println(res.getHeaders());
		System.out.println(res.getContentAsString());
	}
	
	public static ContentResponse doGet(String url){
		HttpClient httpClient = new HttpClient();
		
		ContentResponse contentRes = null;
		try {
			httpClient.start();
			contentRes = httpClient.newRequest(url).method(HttpMethod.GET).send();
			httpClient.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return contentRes;
	}
	
	public static ContentResponse doPost(String url, String content) {
		HttpClient httpClient = new HttpClient();
		ContentResponse contentRes = null;
		try {
			httpClient.start();
			Request req = httpClient.newRequest(url).method(HttpMethod.POST);
			//req.header(HttpHeader.CONTENT_TYPE, "application/json");
			if(content != null)
				req.content(new StringContentProvider(content, "utf-8"));
			
			contentRes = req.send();
			httpClient.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return contentRes;
	}
	
	public static ContentResponse doPost(String url, HashMap<String, String> headerMap, String content) {
		HttpClient httpClient = new HttpClient();
		ContentResponse contentRes = null;
		try {
			httpClient.start();
			Request req = httpClient.newRequest(url).method(HttpMethod.POST);
			
			if(headerMap != null) {
				req.header("abc", "abc");
				headerMap.forEach((key, value)->{
					req.header(key, value);
				});
			}
			
			if(content != null)
				req.content(new StringContentProvider(content, "utf-8"));
			
			contentRes = req.send();
			httpClient.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return contentRes;
	}
}
