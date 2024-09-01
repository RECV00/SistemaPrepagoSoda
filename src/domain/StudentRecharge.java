package domain;

import java.time.LocalDate;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class StudentRecharge {

	private  String carnetStudent;
    private String studentName;
    private  LocalDate rechargeDate;
    private  double rechargeAmount;
    
    private BooleanProperty selected = new SimpleBooleanProperty(false); // Nuevo atributo
    private boolean requested;// saber si hay una solicitud 
	
    public StudentRecharge(String carnetStudent, String studentName, LocalDate rechargeDate,
			double rechargeAmount) {
		super();
		this.carnetStudent = carnetStudent;
		this.studentName = studentName;
		this.rechargeDate = rechargeDate;
		this.rechargeAmount = rechargeAmount;
	}
	public StudentRecharge() {
		super();
	}
	public String getCarnetStudent() {
		return carnetStudent;
	}
	public void setCarnetStudent(String carnetStudent) {
		this.carnetStudent = carnetStudent;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public LocalDate getRechargeDate() {
		return rechargeDate;
	}
	public void setRechargeDate(LocalDate rechargeDate) {
		this.rechargeDate = rechargeDate;
	}
	public double getRechargeAmount() {
		return rechargeAmount;
	}
	public void setRechargeAmount(double rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}
//-------------------------------------------------	
	 public boolean isSelected() {
	        return selected.get();
	    }

	    public void setSelected(boolean selected) {
	        this.selected.set(selected);
	    }

	    public BooleanProperty selectedProperty() {
	        return selected;
	    }
	    // Getter y Setter para requested
	    public boolean isRequested() {
	        return requested;
	    }

	    public void setRequested(boolean requested) {
	        this.requested = requested;
	    }
//---------------------------------------------
	@Override
	public String toString() {
		return carnetStudent + "-" + studentName + "-"+ rechargeDate + "-" + rechargeAmount;
	}
    
    

}
