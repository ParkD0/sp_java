package sp_java.json;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class JsonSample {
	public void jsonParsing() {
		//1. parsing json
		//1-1 default
		System.out.println("json string parsing");
		String jsonStr = "{\"id\":1,\"name\":\"Park\"}";
		JsonObject obj = JsonParser.parseString(jsonStr).getAsJsonObject();
		System.out.print("id : ");
		System.out.print(obj.get("id").getAsInt());
		System.out.print(" / name : ");
		System.out.println(obj.get("name").getAsString());
		System.out.println("--------------");
		
		//1-2 parsing json array
		System.out.println("json array string parsing");
		String studentArryStr = "[{'id':1, 'name': 'park'}, {'id':2, 'name':'kim'}]";
		JsonArray jsonAry = JsonParser.parseString(studentArryStr).getAsJsonArray();
		for(int i = 0; i < jsonAry.size(); i++) {
			JsonObject tmpObj = jsonAry.get(i).getAsJsonObject();
			System.out.print("id : ");
			System.out.print(tmpObj.get("id").getAsInt());
			System.out.print(" / name : ");
			System.out.println(tmpObj.get("name").getAsString());
		}
		System.out.println("----------");
		
		//1-3 parsing json include array member
		System.out.println("json string include array member parsing");
		String departmentJson = "{'id' : 1, 'name' : 'HR', 'users' : ['park', 'lee', 'kim' ]}";
		JsonObject departJsonObj = JsonParser.parseString(departmentJson).getAsJsonObject();
		System.out.print("id : ");
		System.out.print(departJsonObj.get("id").getAsInt());
		System.out.print(" / name : ");
		System.out.print(departJsonObj.get("name").getAsString());
		System.out.print(" / users : ");
		JsonArray departUserAry = departJsonObj.get("users").getAsJsonArray();
		for(int i = 0; i < departUserAry.size(); i++) {
			System.out.print(departUserAry.get(i).getAsString());
			if(i < departUserAry.size() -1) {
				System.out.print(", ");
			}
			else {
				System.out.println();
			}
		}
	}
	
	public void makeJsonObject() {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("name", "spiderman");
		jsonObject.addProperty("age", 45);
		jsonObject.addProperty("married", true);
		JsonArray jsonArySpecial = new JsonArray();
		jsonArySpecial.add("martial art");
		jsonArySpecial.add("gun");
		jsonObject.add("speciality", jsonArySpecial);
		 
		JsonObject jsonObjectVaccine = new JsonObject();
		jsonObjectVaccine.addProperty("1st", "done");
		jsonObjectVaccine.addProperty("2nd", "expected");
		jsonObjectVaccine.add("3rd", null);
		 
		jsonObject.add("vaccine", jsonObjectVaccine);
		 
		JsonArray jsonAryChildren = new JsonArray();
		JsonObject children1 = new JsonObject();
		children1.addProperty("name", "sipderboy");
		children1.addProperty("age", 10);
		 
		JsonObject children2 = new JsonObject();
		children2.addProperty("name", "spidergirl");
		children2.addProperty("age", 8);
		jsonAryChildren.add(children2);
		jsonAryChildren.add(children1);
		jsonObject.add("children", jsonAryChildren);
		 
		jsonObject.add("address", null);	
		
		//write file
		FileWriter fw;
		try {
			fw = new FileWriter("sample.json");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(jsonObject.toString());
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void jsonToObject() {
		Gson gson = new Gson();
		//2. json to object
		//2-1 default
		System.out.println("json string to object");
		String jsonStr2 = "{\"id\":1,\"name\":\"Park\"}";
		Student student1 = gson.fromJson(jsonStr2, Student.class);
		System.out.println(student1);
		System.out.println("-------------");
		
		//2-2 json array to object
		System.out.println("json array to array list");
		String studentArryStr2 = "[{'id':1, 'name': 'park'}, {'id':2, 'name':'kim'}]";
		ArrayList<Student> studentAry = gson.fromJson(studentArryStr2, new TypeToken<ArrayList<Student>>(){}.getType());
		for(Student tmpStudent : studentAry) {
			System.out.println(tmpStudent);
		}
		System.out.println("-------------");
		
		//2-3 json include array member to object
		System.out.println("json include array member(string) to object include array list");
		String departmentJson2 = "{'id' : 1, 'name' : 'HR', 'users' : ['park', 'lee', 'kim' ]}";
		Department department = gson.fromJson(departmentJson2, Department.class);
		System.out.println(department);
		System.out.println("-------------");
		
		System.out.println("json include array member(object) to object include array list");
		String lessonJson = "{'name':'math', 'students' : [ {'id':1,'name':'park'}, {'id':2,'name':'kim'} ] }";
		Lesson lesson = gson.fromJson(lessonJson, Lesson.class);
		System.out.println(lesson);
		System.out.println("-------------");
		
	}
	
	public void objectToJson() {
		Gson gson = new Gson();
		//3. object to json
		//3-1 default
		System.out.println("object to json");
		Student student2 = new Student(2, "kim");
		String studentJson = gson.toJson(student2);
		System.out.println(studentJson);
		System.out.println("------------");
		
		//3-2 arrayList to json
		System.out.println("ArrayList to json");
		ArrayList<Student> studentAry2 = new ArrayList<Student>();
		Student st1 = new Student(1, "park2");
		Student st2 = new Student(2, "kim2");
		studentAry2.add(st1);
		studentAry2.add(st2);
		String studentAryJsonStr = gson.toJson(studentAry2);
		System.out.println(studentAryJsonStr);
		System.out.println("------------");
		
		//3-3 object include ArrayList member to json
		System.out.println("object inclue ArrayList member(string) to json");
		Department department3 = new Department(2, "R&d");
		String departmentJson3 = gson.toJson(department3);
		System.out.println(departmentJson3);
		System.out.println("------------");
		
		System.out.println("object inclue ArrayList member(object) to json");
		Lesson lesson2 = new Lesson("english");
		String lessonJson2 = gson.toJson(lesson2);
		System.out.println(lessonJson2);
		System.out.println("------------");
	}
	
	public void objectToJsonFile() {
		 Gson gsonUseNull = new GsonBuilder().serializeNulls().create();
		//4. object to json file
		System.out.println("object to json file");
		Student student4 = new Student(4, "lee");
		FileWriter fw;
		try {
			fw = new FileWriter("./student.json");
			gsonUseNull.toJson(student4, fw);
	        fw.flush();
	        fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void jsonObjToJsonFile() {
		 Gson gsonUseNull = new GsonBuilder().serializeNulls().create();
		 System.out.println("json object to json file");
		 JsonObject jsonObject = new JsonObject();
		 jsonObject.addProperty("name", "japanes");
		 
		 JsonObject jsonObjSt1 = new JsonObject();
		 jsonObjSt1.addProperty("id", 5);
		 jsonObjSt1.addProperty("name", "hwang");
		 JsonObject jsonObjSt2 = new JsonObject();
		 jsonObjSt2.addProperty("id", 6);
		 jsonObjSt2.addProperty("name", "woo");
		 JsonArray jsonArySt = new JsonArray();
		 jsonArySt.add(jsonObjSt1);
		 jsonArySt.add(jsonObjSt2);
		 
		 jsonObject.add("students", jsonArySt);
		 
		 FileWriter fw;
		 try {
			fw = new FileWriter("./lesson.json");
			gsonUseNull.toJson(jsonObject, fw);
		    fw.flush();
		    fw.close();
		 } catch (IOException e) {
			e.printStackTrace();
		 }
		 //아래 방법도 가능
		 FileWriter fw2;
		 try {
			fw2 = new FileWriter("sample.json");
			BufferedWriter bw = new BufferedWriter(fw2);
			bw.write(jsonObject.toString());
			bw.close();
			fw2.close();
		 } catch (IOException e) {
			e.printStackTrace();
		 }
	}
	
	public void jsonFileToObject() {
		Gson gson = new Gson();
		// json file to object
		System.out.println("json file to object");
		 try {
			Reader reader = new FileReader("./student.json");
			Student student = gson.fromJson(reader, Student.class);
			System.out.println(student);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void jsonFileToJsonObject() {
		try {
			Reader reader = new FileReader("./sample.json");
			JsonObject obj = JsonParser.parseReader(reader).getAsJsonObject();
			
			//print key set
			System.out.println(obj.keySet());
			for(String key : obj.keySet()) {
				System.out.print("Key : " + key +" / Value Type : ");
				switch(obj.get(key).getClass().getName()) {
				case "com.google.gson.JsonPrimitive" :
					//System.out.println("primitive");
					if(obj.get(key).getAsJsonPrimitive().isString()) {
						System.out.println("String");
					}else if(obj.get(key).getAsJsonPrimitive().isBoolean()) {
						System.out.println("Bool");
					}else if(obj.get(key).getAsJsonPrimitive().isNumber()) {
						System.out.println("Number");
					}
					break;
				case "com.google.gson.JsonArray":
					System.out.println("JsonArray");
					break;
				case "com.google.gson.JsonObject":
					System.out.println("JsonObject");
					break;
				case "com.google.gson.JsonNull":
					System.out.println("JsonNull");
					break;
				}
				//Also available in the form below
				/*
				JsonElement je = jsonObj.get(key);
				if (je.isJsonPrimitive()) {
				}
				else if (je.isJsonArray()) {
				}
				*/
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//reference
	//https://howtodoinjava.com/gson/gson-parse-json-array/
	//https://hianna.tistory.com/629#gson3
	//https://hianna.tistory.com/630
	//https://kim-jong-hyun.tistory.com/61
	//https://mikelim.mintocean.com/entry/Gson%EC%9D%84-%EC%9D%B4%EC%9A%A9%ED%95%98%EC%97%AC-JSON-Array%EB%A5%BC-List%EB%A1%9C-%EB%A7%88%EC%83%AC%EB%A7%81
	
	//@SerializedName(value = "1st")
	//https://cherrypick.co.kr/gson-serializedname-annotation/
}
