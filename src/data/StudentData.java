package data;

import java.util.ArrayList;
import java.io.IOException;
import java.util.List;

import domain.Student;

public class StudentData {
	public static ArrayList<Student> StudentList = new ArrayList();
	private static final String fileName = "Student.json";
	private static JSONUtils<Student> jsonUtils = new JSONUtils<>(fileName);
	
	
	public static List<Student> getStudentList(){
		try {
				return jsonUtils.getElements(Student.class);
		}catch(IOException e) {
			
		}
		return null;
		
	}
	
	public static String getStudentStringFormat(Student s) {
		return "\n Carnet: "+ s.getCarnetStudent().toUpperCase()+"\n Student: "+ s.getName().toUpperCase()+"Correo: "+ s.getEmail()+"Celular: "+s.getPhone()+ "\n Fecha Ingreso: " + s.getDateEntry()
		+"\n Genero: "+((s.getGender() == 'M')?"Masculino":"Femenino")+"Dinero Disponible: "+ s.getMoneyAvailable();		
	}
	
	public static boolean saveStudent(Student s) {
		System.out.print(s);
		try {
			jsonUtils.saveElement(s);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean deleteStudent(Student s) {
		System.out.println("deleteStudent"+s);
	    try {
	    	List<Student> list = getStudentList();
	    	int index = 0;
	    	for(Student student: list) {
	    		
	    		if(s.getCarnetStudent().equals(student.getCarnetStudent())) {
	    			jsonUtils.deleteElement(s,index);
	    			return true;
	    		}
	    		index++;
	    	}
	        
	        return true;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public static boolean updateStudent(Student s,String carnet) {
		System.out.print(s);
		try {
			jsonUtils.updateElementStudent(s,carnet);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	// Nuevo método para obtener un estudiante por su carnet
    public static Student getStudentByCarnet(String carnet) {
        List<Student> students = getStudentList();
        for (Student student : students) {
            if (student.getCarnetStudent().equals(carnet)) {
                return student;
            }
        }
        return null;
    }
	
	
}
