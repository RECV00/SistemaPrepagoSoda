package domain;

import java.time.LocalDate;

public class Recharge {
	private String carnetStudent;
	private double amount;
	private LocalDate dataEntry;
	
	public Recharge() {
		
	}

	public Recharge(String carnetStudent, double amount, LocalDate dataEntry) {
		super();
		this.carnetStudent = carnetStudent;
		this.amount = amount;
		this.dataEntry = dataEntry;
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

	public LocalDate getDataEntry() {
		return dataEntry;
	}

	public void setDataEntry(LocalDate dataEntry) {
		this.dataEntry = dataEntry;
	}

	@Override
	public String toString() {
		return carnetStudent + "-" + amount + "-" + dataEntry;
	}

}
