package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import data.RechargeData;
import data.StudentData;
import domain.Recharge;
import domain.Student;
import domain.StudentRecharge;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.DatePicker;

public class UIRechargeController {
	@FXML
	private Label lTitule;
	@FXML
	private TextField tfCarnetStudent;
	@FXML
	private TextField tfNewAmount;
	@FXML
	private DatePicker dpDateEntry;
	@FXML
	private Label lCarnetStudent;
	@FXML
	private Label lAmountNew;
	@FXML
	private Button bRecharge;
	@FXML
	private Button bBack;

	private double saldo = 0.0;
	
//	Se optiene los datos del estuante seleccionado en la controller de Consultar Saldo
	  public void recoveredData(StudentRecharge studentRecharge) {
	        if (studentRecharge != null) {
	        	tfCarnetStudent.setText(studentRecharge.getCarnetStudent());
	        	tfCarnetStudent.setEditable(false);
	        	dpDateEntry.setValue(studentRecharge.getRechargeDate());
	           saldo = studentRecharge.getRechargeAmount();
	        }
	    }
	  
	 @FXML
	    public void searchRechargeByCarnet(ActionEvent event) {
	        String carnet = tfCarnetStudent.getText();
	        
//	        buscar la cedula
	        Recharge recharge = RechargeData.getRechargeList().stream()
	            .filter(r -> r.getCarnetStudent().equalsIgnoreCase(carnet))
	            .findFirst()
	            .orElse(null);
	        
	        if (recharge != null) {
	            tfNewAmount.setText(String.valueOf(recharge.getAmount()));
	            dpDateEntry.setValue(recharge.getDateEntry());
	        } else {
	            JOptionPane.showMessageDialog(null, "Recarga no encontrada para el carnet: " + carnet);
	        }
	    }
	    
	    @FXML
	    public void saveRecharge(ActionEvent event) {
	        String carnet = tfCarnetStudent.getText().trim();
	        double newAmount = Double.parseDouble(tfNewAmount.getText())+ saldo;
	        LocalDate newDateEntry = dpDateEntry.getValue();

	        if (Double.parseDouble(tfNewAmount.getText()) <= 1000 || Double.parseDouble(tfNewAmount.getText()) >= 10000) {
	            JOptionPane.showMessageDialog(null, "El monto debe estar entre 1,000 y 10,000.");
	            return;
	        }
	        Recharge updatedRecharge = new Recharge(carnet, newAmount, newDateEntry);
	        boolean rechargeUpdated = RechargeData.updateRecharge(updatedRecharge, carnet);

	        if (rechargeUpdated) {
	        	Student student = StudentData.getStudentByCarnet(carnet);
	            if (student != null) {
	            	
	                student.setMoneyAvailable(newAmount);
	                
	                if (StudentData.updateStudent(student,student.getCarnetStudent())) {
	                    JOptionPane.showMessageDialog(null, "Recarga y monto del estudiante actualizados exitosamente.");
	                } else {
	                    JOptionPane.showMessageDialog(null, "Error al actualizar el monto del estudiante.");
	                }
	            } else {
	                JOptionPane.showMessageDialog(null, "Estudiante con carnet " + carnet + " no encontrado.");
	            }
	        } else {
	            JOptionPane.showMessageDialog(null, "Error al actualizar la recarga.");
	        }
	    }
	// Event Listener on Button[#bBack].onAction
	@FXML
	public void returnMain(ActionEvent event) {
		closeWindows();
	}
	
	@FXML
	public void closeWindows() {		
		try {
			 FXMLLoader loader = new FXMLLoader (getClass().getResource("/presentation/UICheckBalance.fxml"));
	        Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.show();
	        Stage temp = (Stage) bBack.getScene().getWindow();
	        temp.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}