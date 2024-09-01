package data;

import java.util.List;
import domain.Student;

public class LogicData {

	public LogicData() {
		// TODO Auto-generated constructor stub
	}
	
	 public boolean carnetAlreadyExists(String carnet) {
		    // Supongamos que tienes una lista de estudiantes llamada studentList
		   List<Student> listStudent =StudentData.getStudentList();
		   
		 for (Student student : listStudent) {
		        if (student.getCarnetStudent().equalsIgnoreCase(carnet)) {
		            return true;  // El carnet ya existe
		        }
		    }
		    return false;  // El carnet no existe
		} 
	 
	 
	 
}

