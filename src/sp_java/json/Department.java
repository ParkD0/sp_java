package sp_java.json;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class Department {
	@SerializedName(value = "id")
	public int id; //when key name start number ex) 1st , use @SerializedName 
	@SerializedName(value = "name")
	public String name;
	public ArrayList<String> users;
	
	public Department() {
	}
	
	public Department(int id, String name) {
		this.id = id;
		this.name = name;
		users = new ArrayList<String>();
		users.add("park");
		users.add("kim");
	}
	
	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + ", users=" + users + "]";
	}

}
