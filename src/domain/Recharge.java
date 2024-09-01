package domain;

import java.time.LocalDate;

public class Recharge {
	private String carnetStudent;
	private double amount;
	private LocalDate dateEntry;
	
	public Recharge() {
		
	}

	public Recharge(String carnetStudent, double amount, LocalDate dateEntry) {
		super();
		this.carnetStudent = carnetStudent;
		this.amount = amount;
		this.dateEntry = dateEntry;
	}

	public String getCarnetStudent() {
		return carnetStudent;
	}

	public void setCarnetStudent(String carnetStudent) {
		this.carnetStudent = carnetStudent;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDate getDateEntry() {
		return dateEntry;
	}

	public void setDateEntry(LocalDate dateEntry) {
		this.dateEntry = dateEntry;
	}

	@Override
	public String toString() {
		return carnetStudent + "-" + amount + "-" + dateEntry;
	}

}
