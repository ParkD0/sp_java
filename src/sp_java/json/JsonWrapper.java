package sp_java.json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

/*
 * usage example
 	JsonWrapper<Lesson> jw = new JsonWrapper<Lesson>() {};  //Must be used as an anonymous class
	Lesson l = jw.jsonStrToObj(lessonJsonStr);
	jw.ObjToJsonFile(l, "./Lesson.json");
	Lesson l2 = jw.JsonFileToObj("./Lesson.json");
 */
public class JsonWrapper <T>{
	 private final Type type;
	 Gson gson;
	 
	 protected JsonWrapper() {
		 Type superclass = getClass().getGenericSuperclass();
	     if (superclass instanceof Class) {
	    	 throw new RuntimeException("Missing type parameter.");
	     }
	     this.type = ((ParameterizedType) superclass).getActualTypeArguments()[0];
	     this.gson = new Gson();
	 }
	 
	 public Type getType() {
		 return this.type;
	 }
	 
	 public JsonObject jsonStrToJsonObj(String jsonStr) {
		 JsonObject jsonObj = JsonParser.parseString(jsonStr).getAsJsonObject();
		 return jsonObj;
	 }
		
	 public JsonArray jsonStrToJsonAry(String jsonStr) {
		 JsonArray jsonAry = JsonParser.parseString(jsonStr).getAsJsonArray();
		 return jsonAry;
	 }
		
	 public T jsonStrToObj(String jsonStr) {
		 return gson.fromJson(jsonStr, this.type);
	 }
		
	 public ArrayList<T> jsonAryStrToArrayList(String jsonStr) {
		 return gson.fromJson(jsonStr, TypeToken.getParameterized(ArrayList.class, this.type).getType());
	 }
	 
	 public String jsonObjToJsonStr(JsonObject jsonObj) {
		 return jsonObj.getAsString();
	 }
	 
	 public T jsonObjToObj(JsonObject jsonObj) {
		 return gson.fromJson(jsonObj, this.type);
	 }
	 
	 public String objToJsonStr(T obj) {
		 return gson.toJson(obj);
	 }
	 
	 public JsonObject objToJsonObj(T obj) {
		 JsonObject jsonObj = JsonParser.parseString(gson.toJson(obj)).getAsJsonObject();
		 return jsonObj;
	 }
	 
	 public void objToJsonFile(T obj, String filePath) {
		 Gson gsonUseNull = new GsonBuilder().serializeNulls().create();
		 FileWriter fw;
		 try {
			 fw = new FileWriter(filePath);
			 gsonUseNull.toJson(obj, fw);
			 fw.flush();
			 fw.close();
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	}

	 public T jsonFileToObj(String filePath) {
		 try {
			 Reader reader = new FileReader(filePath);
			 return gson.fromJson(reader, type);
		 } catch (FileNotFoundException e) {
			 e.printStackTrace();
			 return null;
		}
	 }
	 
	 public void jsonObjToJsonFile(JsonObject obj, String path) {
		 FileWriter fw;
		 try {
			Gson gsonUseNull = new GsonBuilder().serializeNulls().create();
			fw = new FileWriter(path);
			gsonUseNull.toJson(obj, fw);
		    fw.flush();
		    fw.close();
		 } catch (IOException e) {
			e.printStackTrace();
		 }
	}
	 
	public JsonObject jsonFileToJsonObj(String filePath) {
		Reader reader;
		JsonObject obj = null;
		try {
			reader = new FileReader(filePath);
			obj = JsonParser.parseReader(reader).getAsJsonObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
