package data;

import javax.swing.JOptionPane;

import domain.Dishe;
import domain.Student;
import javafx.scene.control.CheckBox;

public class LogicData {

	public LogicData() {
		// TODO Auto-generated constructor stub
	}
	
	public void deleteDishes(Dishe d,boolean serviceHours, String serviceDay) {
		
		if(serviceHours == true && serviceDay.equals("Lunes")) {
			DisheData.deleteMonday_Lunch(d);
		}else if(serviceHours == false && serviceDay.equals("Lunes")) {// desayuno
			DisheData.deleteMonday_Breakfast(d);
		}else if(serviceHours == true && serviceDay.equals("Martes")) {
			DisheData.deleteTuesday_Lunch(d);
		}else if(serviceHours == false && serviceDay.equals("Martes")) {// desayuno
			DisheData.deleteTuesday_Breakfast(d);
		}else if(serviceHours == true && serviceDay.equals("Miercoles")) {
			DisheData.deleteWednesday_Lunch(d);
		}else if(serviceHours == false && serviceDay.equals("Miercoles")) {// desayuno
			DisheData.deleteWebnesday_Breakfast(d);
		}else if(serviceHours == true && serviceDay.equals("Jueves")) {
			DisheData.deleteThursday_Lunch(d);
		}else if(serviceHours == false && serviceDay.equals("Jueves")) {// desayuno
			DisheData.deleteThursday_Breakfast(d);
		}else if(serviceHours == true && serviceDay.equals("Viernes")) {
			DisheData.deleteFriday_Lunch(d);
		}else if(serviceHours == false && serviceDay.equals("Viernes")) {// desayuno
			DisheData.deleteFriday_Breakfast(d);
		}else {
			
		}
		
	}
	
	 public void handleCheckBox(CheckBox checkBox, Dishe dishe, Student selectedStudent) {
	        checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
	            dishe.setSelected(isNowSelected);

	            if (selectedStudent != null) {
	                if (isNowSelected && selectedStudent.getMoneyAvailable() >= dishe.getServicePrice()) {
	                    int response = JOptionPane.showConfirmDialog(null,
	                            "Al estudiante le alcanza para pagar este platillo. ¿Deseas registrar la selección?",
	                            "Registrar Selección",
	                            JOptionPane.YES_NO_OPTION);

	                    if (response == JOptionPane.YES_OPTION) {
	                        JOptionPane.showMessageDialog(null, "Platillo registrado exitosamente.");
	                        // Aquí podrías agregar la lógica para registrar el ServiceRequest
	                    } else {
	                        checkBox.setSelected(false);
	                        dishe.setSelected(false);
	                    }
	                } else if (isNowSelected) {
	                    JOptionPane.showMessageDialog(null, "Saldo insuficiente para pagar este platillo.");
	                    checkBox.setSelected(false);
	                    dishe.setSelected(false);
	                }
	            } else {
	                JOptionPane.showMessageDialog(null, "Por favor, selecciona un estudiante primero.");
	                checkBox.setSelected(false);
	                dishe.setSelected(false);
	            }
	        });
	    }
	}

