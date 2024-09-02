package domain;

import java.time.LocalDate;

public class Student {

	private String carnetStudent;
	private String name; 
	private String email;
	private int phone;
	boolean isActive;
	private LocalDate dateEntry;
	private char gender;
	private double moneyAvailable;
	
	public Student() {
		
	}

	public Student(String carnetStudent, String name, String email, int phone, boolean isActive, LocalDate dateEntry,
			char gender, double moneyAvailable) {
		super();
		this.carnetStudent = carnetStudent;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.isActive = isActive;
		this.dateEntry = dateEntry;
		this.gender = gender;
		this.moneyAvailable = moneyAvailable;
	}

	public String getCarnetStudent() {
		return carnetStudent;
	}

	public void setCarnetStudent(String carnetStudent) {
		this.carnetStudent = carnetStudent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public LocalDate getDateEntry() {
		return dateEntry;
	}

	public void setDateEntry(LocalDate dateEntry) {
		this.dateEntry = dateEntry;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public double getMoneyAvailable() {
		return moneyAvailable;
	}

	public void setMoneyAvailable(double moneyAvailable) {
		this.moneyAvailable = moneyAvailable;
	}
	
	@Override
	public String toString() {
		return carnetStudent + "-" + name + "-" + email + "-" + phone + "-" + ((isActive)?"Activo":"Inactivo") + "-" + dateEntry + "-" + gender + "-"+ moneyAvailable;
	}

}
