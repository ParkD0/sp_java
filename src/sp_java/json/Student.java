package sp_java.json;

public class Student {
	public int id;
	public String name;
	
	public Student() {
	}
	
	public Student(int id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + "]";
	}

}
