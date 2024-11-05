package data;
import java.util.ArrayList;
import domain.Student;

public class StudentData {
	public static ArrayList<Student> StudentList = new ArrayList<Student>();
	private static final String fileName = "Student.json";
	private static JSONUtils<Student> jsonUtils = new JSONUtils<>(fileName);

}
