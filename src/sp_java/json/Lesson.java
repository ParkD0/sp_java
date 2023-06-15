package sp_java.json;

import java.util.ArrayList;


public class Lesson {
	public String name;
	public ArrayList<Student> students;
	
	public Lesson() {
	}
	
	public Lesson(String name) {
		this.name = name;
		students = new ArrayList<Student>();
		Student st1 = new Student(1, "park");
		Student st2 = new Student(2, "kim");
		students.add(st1);
		students.add(st2);
	}
	
	@Override
	public String toString() {
		return "Lesson [name=" + name + ", students=" + students + "]";
	}

}
