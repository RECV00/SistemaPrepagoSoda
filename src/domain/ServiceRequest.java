package domain;

import java.util.Arrays;

public class ServiceRequest {

	private Student student;
	private Dishe[] dishe;
	
	public ServiceRequest() {}

	public ServiceRequest(Student student, Dishe[] dishe) {
		super();
		this.student = student;
		this.dishe = dishe;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Dishe[] getDishe() {
		return dishe;
	}

	public void setDishe(Dishe[] dishe) {
		this.dishe = dishe;
	}

	@Override
	public String toString() {
		return   student + "-" + Arrays.toString(dishe);
	}

	
}
