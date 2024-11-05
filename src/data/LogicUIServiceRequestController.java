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
	        if (serviceHours.equals("Almuerzo") && serviceDay.equals("Lunes")) {
	            disheList = FXCollections.observableArrayList(DisheData.getMonday_LunchList());
	        } else if (serviceHours.equals("Desayuno") && serviceDay.equals("Lunes")) {
	            disheList = FXCollections.observableArrayList(DisheData.getMonday_BreakfastList());
	        } else if (serviceHours.equals("Almuerzo") && serviceDay.equals("Martes")) {
	            disheList = FXCollections.observableArrayList(DisheData.getTuesday_LunchList());
	        } else if (serviceHours.equals("Desayuno") && serviceDay.equals("Martes")) {
	            disheList = FXCollections.observableArrayList(DisheData.getTuesday_BreakfastList());
	        } else if (serviceHours.equals("Almuerzo") && serviceDay.equals("Miércoles")) {
	            disheList = FXCollections.observableArrayList(DisheData.getWednesday_LunchList());
	        } else if (serviceHours.equals("Desayuno") && serviceDay.equals("Miércoles")) {
	            disheList = FXCollections.observableArrayList(DisheData.getWednesday_BreakfastList());
	        } else if (serviceHours.equals("Almuerzo") && serviceDay.equals("Jueves")) {
	            disheList = FXCollections.observableArrayList(DisheData.getThursday_LunchList());
	        } else if (serviceHours.equals("Desayuno") && serviceDay.equals("Jueves")) {
	            disheList = FXCollections.observableArrayList(DisheData.getThursday_BreakfastList());
	        } else if (serviceHours.equals("Almuerzo") && serviceDay.equals("Viernes")) {
	            disheList = FXCollections.observableArrayList(DisheData.getFriday_LunchList());
	        } else if (serviceHours.equals("Desayuno") && serviceDay.equals("Viernes")) {
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
