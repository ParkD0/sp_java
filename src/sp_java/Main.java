package sp_java;

import java.util.HashMap;

import org.eclipse.jetty.client.api.ContentResponse;

import com.google.gson.JsonObject;

import sp_java.http.HttpClientSample;
import sp_java.http.HttpServerSample;
import sp_java.json.JsonWrapper;
import sp_java.json.Lesson;

public class Main {

	public static void main(String[] args) {
		HttpServerSample svr = new HttpServerSample(8080);
		svr.run();
		System.out.println("start server done");
		
		HashMap<String, String> hash = new HashMap<String, String>();
		hash.put("Content-Type", "application/json");
		ContentResponse res = HttpClientSample.doGet("http://127.0.0.1:8080/LESSON");
		HttpClientSample.printRes(res);
		
		String jsonStr = res.getContentAsString();
		JsonWrapper<Lesson> jw = new JsonWrapper<Lesson>() {};
		JsonObject jsonObj = jw.jsonStrToJsonObj(jsonStr);
		JsonObject lessonJson = jsonObj.get("Lesson").getAsJsonObject();
		Lesson lesson = jw.jsonObjToObj(lessonJson);
		System.out.println(lesson);
		svr.quit();
	}
}
