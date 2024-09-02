package data;

import javax.swing.JOptionPane;

import domain.Dishe;
import domain.Student;
import javafx.scene.control.ComboBox;


public class LogicUICheckBalanceController {

	public LogicUICheckBalanceController() {
	}

	//cuando se elija el boton de solicitar 
	public void handleRequestStudent(Dishe dishe,ComboBox<Student> cbStudentsList ) {
		
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
	            selectedStudent.setMoneyAvailable(studentBalance - dishe.getServicePrice()); // Actualiza el saldo del estudiante
	           
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
	
	
}
