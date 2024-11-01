package data;

import java.util.LinkedList;

import javax.swing.JOptionPane;
import domain.Dishe;
import domain.Recharge;
import domain.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

public class LogicUIServiceRequestController {
	
	public LogicUIServiceRequestController() {
	}
	//cuando se elija el boton de solicitar 
	public void handleRequestDishe(Dishe dishe,ComboBox<Student> cbStudentsList ) {
		
	    Student selectedStudent = cbStudentsList.getSelectionModel().getSelectedItem();
	    
	    if (selectedStudent == null) {
	        JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún estudiante.");
	        return;
	    }

	    double studentBalance = selectedStudent.getMoneyAvailable(); // Obtén el saldo del estudiante

	    if (studentBalance >= dishe.getServicePrice()) {
	        int response = JOptionPane.showConfirmDialog(null, 
	                "¿Desea solicitar el platillo seleccionado?", 
	                "Confirmación", 
	                JOptionPane.YES_NO_OPTION);
	        
	        if (response == JOptionPane.YES_OPTION) {
	        	
	        	double newAmount = studentBalance - dishe.getServicePrice();
	           selectedStudent.setMoneyAvailable(newAmount); // Actualiza el saldo del estudiante
	           
	           Recharge recharge =RechargeData.getRechargeByCarnet(selectedStudent.getCarnetStudent());
	           recharge.setAmount(newAmount);
	          
	            // Guardar los cambios en el archivo JSON
	            if (StudentData.updateStudent(selectedStudent, selectedStudent.getCarnetStudent())) {
	                JOptionPane.showMessageDialog(null, 
	                        "Solicitud confirmada.\n" +
	                        "Platillo: " + dishe.getServiceName() + "\n" +
	                        "Precio: " + dishe.getServicePrice() + "\n" +
	                        "Saldo restante: " + selectedStudent.getMoneyAvailable());
	               
	            } else {
	                JOptionPane.showMessageDialog(null, "Error al actualizar el saldo del estudiante.");
	            }
	        }
	    } else {
	        JOptionPane.showMessageDialog(null, 
	                "Saldo insuficiente para solicitar este platillo.");
	    }
	}
	
	public void updateComboBox(ComboBox<Student> cbStudentsList) {

	    cbStudentsList.setItems(FXCollections.observableArrayList(StudentData.getStudentList()));
	    cbStudentsList.getSelectionModel().clearSelection();
	    cbStudentsList.setPromptText("Seleccione el estudiante");
	   	     
	}
	// Se llena la tabla 
		 public void loadDisheList(String serviceDay, boolean serviceHours, ObservableList<Dishe> disheList,TableView<Dishe> tvDisheData ){
		        try {
		            if (serviceHours && serviceDay.equals("Lunes")) {
		                disheList = FXCollections.observableArrayList(DisheData.getMonday_LunchList());
		            } else if (!serviceHours && serviceDay.equals("Lunes")) {
		                disheList = FXCollections.observableArrayList(DisheData.getMonday_BreakfastList());
		            } else if (serviceHours && serviceDay.equals("Martes")) {
		                disheList = FXCollections.observableArrayList(DisheData.getTuesday_LunchList());
		            } else if (!serviceHours && serviceDay.equals("Martes")) {
		                disheList = FXCollections.observableArrayList(DisheData.getTuesday_BreakfastList());
		            } else if (serviceHours && serviceDay.equals("Miércoles")) {
		                disheList = FXCollections.observableArrayList(DisheData.getWednesday_LunchList());
		            } else if (!serviceHours && serviceDay.equals("Miércoles")) {
		                disheList = FXCollections.observableArrayList(DisheData.getWednesday_BreakfastList());
		            } else if (serviceHours && serviceDay.equals("Jueves")) {
		                disheList = FXCollections.observableArrayList(DisheData.getThursday_LunchList());
		            } else if (!serviceHours && serviceDay.equals("Jueves")) {
		                disheList = FXCollections.observableArrayList(DisheData.getThursday_BreakfastList());
		            } else if (serviceHours && serviceDay.equals("Viernes")) {
		                disheList = FXCollections.observableArrayList(DisheData.getFriday_LunchList());
		            } else if (!serviceHours && serviceDay.equals("Viernes")) {
		                disheList = FXCollections.observableArrayList(DisheData.getFriday_BreakfastList());
		            }

		            tvDisheData.setItems(disheList);

		        } catch (Exception e) {
		            e.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Error al cargar los datos de los Alimentos.");
		        }
		    }
		 
		
		 
	//Eliminar un platillo
	public void deleteDishes(Dishe d,boolean serviceHours, String serviceDay) {
			try {
			if(serviceHours == true && serviceDay.equals("Lunes")) {
				DisheData.deleteMonday_Lunch(d);
			}else if(serviceHours == false && serviceDay.equals("Lunes")) {// desayuno
				DisheData.deleteMonday_Breakfast(d);
			}else if(serviceHours == true && serviceDay.equals("Martes")) {
				DisheData.deleteTuesday_Lunch(d);
			}else if(serviceHours == false && serviceDay.equals("Martes")) {// desayuno
				DisheData.deleteTuesday_Breakfast(d);
			}else if(serviceHours == true && serviceDay.equals("Miércoles")) {
				DisheData.deleteWednesday_Lunch(d);
			}else if(serviceHours == false && serviceDay.equals("Miércoles")) {// desayuno
				DisheData.deleteWednesday_Breakfast(d);
			}else if(serviceHours == true && serviceDay.equals("Jueves")) {
				DisheData.deleteThursday_Lunch(d);
			}else if(serviceHours == false && serviceDay.equals("Jueves")) {// desayuno
				DisheData.deleteThursday_Breakfast(d);
			}else if(serviceHours == true && serviceDay.equals("Viernes")) {
				DisheData.deleteFriday_Lunch(d);
			}else if(serviceHours == false && serviceDay.equals("Viernes")) {// desayuno
				DisheData.deleteFriday_Breakfast(d);
			}else {
	            JOptionPane.showMessageDialog(null, "No se pudo encontrar la combinación de día y tipo de servicio.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al eliminar el platillo.");
	    }
			
	}
	public static LinkedList<Dishe> getDishesForDayAndTime(String serviceDay, String serviceHours) {
		ObservableList<Dishe> disheList = FXCollections.observableArrayList(); // Inicializa la lista

	    try {
	        // Lógica para llenar disheList según el día y las horas de servicio
	        if (serviceHours.equals("almuerzo") && serviceDay.equals("Lunes")) {
	            disheList = FXCollections.observableArrayList(DisheData.getMonday_LunchList());
	        } else if (serviceHours.equals("desayuno") && serviceDay.equals("Lunes")) {
	            disheList = FXCollections.observableArrayList(DisheData.getMonday_BreakfastList());
	        } else if (serviceHours.equals("almuerzo") && serviceDay.equals("Martes")) {
	            disheList = FXCollections.observableArrayList(DisheData.getTuesday_LunchList());
	        } else if (serviceHours.equals("desayuno") && serviceDay.equals("Martes")) {
	            disheList = FXCollections.observableArrayList(DisheData.getTuesday_BreakfastList());
	        } else if (serviceHours.equals("almuerzo") && serviceDay.equals("Miércoles")) {
	            disheList = FXCollections.observableArrayList(DisheData.getWednesday_LunchList());
	        } else if (serviceHours.equals("desayuno") && serviceDay.equals("Miércoles")) {
	            disheList = FXCollections.observableArrayList(DisheData.getWednesday_BreakfastList());
	        } else if (serviceHours.equals("almuerzo") && serviceDay.equals("Jueves")) {
	            disheList = FXCollections.observableArrayList(DisheData.getThursday_LunchList());
	        } else if (serviceHours.equals("desayuno") && serviceDay.equals("Jueves")) {
	            disheList = FXCollections.observableArrayList(DisheData.getThursday_BreakfastList());
	        } else if (serviceHours.equals("almuerzo") && serviceDay.equals("Viernes")) {
	            disheList = FXCollections.observableArrayList(DisheData.getFriday_LunchList());
	        } else if (serviceHours.equals("desayuno") && serviceDay.equals("Viernes")) {
	            disheList = FXCollections.observableArrayList(DisheData.getFriday_BreakfastList());
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al cargar los datos de los Alimentos.");
	    }

	    // Convertir ObservableList a LinkedList y devolver
	    return new LinkedList<>(disheList); // Devolver la LinkedList de platos
	}	
}
