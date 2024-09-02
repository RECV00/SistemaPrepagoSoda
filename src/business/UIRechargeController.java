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
import domain.Recharge;
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

	
	  public void recoveredData(StudentRecharge studentRecharge) {
	        if (studentRecharge != null) {
	        	tfCarnetStudent.setText(studentRecharge.getCarnetStudent());
	        	dpDateEntry.setValue(studentRecharge.getRechargeDate());
	            tfNewAmount.setText(String.valueOf(studentRecharge.getRechargeAmount()));
	        }
	    }
	 @FXML
	    public void searchRechargeByCarnet(ActionEvent event) {
	        String carnet = tfCarnetStudent.getText();
	        
	        // Buscar la recarga por cédula
	        Recharge recharge = RechargeData.getRechargeList().stream()
	            .filter(r -> r.getCarnetStudent().equalsIgnoreCase(carnet))
	            .findFirst()
	            .orElse(null);
	        
	        if (recharge != null) {
	            // Mostrar los datos en los campos de texto
	            tfNewAmount.setText(String.valueOf(recharge.getAmount()));
	            dpDateEntry.setValue(recharge.getDateEntry());
	        } else {
	            JOptionPane.showMessageDialog(null, "Recarga no encontrada para el carnet: " + carnet);
	        }
	    }
	    
	    // Método para guardar los cambios en la recarga
	    @FXML
	    public void saveRecharge(ActionEvent event) {
	        String carnet = tfCarnetStudent.getText().trim();
	        double newAmount = Double.parseDouble(tfNewAmount.getText());
	        LocalDate newDateEntry = dpDateEntry.getValue();

	        // Crear un nuevo objeto recarga con los valores actualizados
	        Recharge updatedRecharge = new Recharge(carnet, newAmount, newDateEntry);
	        
	        boolean success = RechargeData.updateRecharge(updatedRecharge, carnet);
	        
	        if (success) {
	            JOptionPane.showMessageDialog(null, "Recarga actualizada exitosamente.");
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
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
	        Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.show();
	        
	        Stage temp = (Stage) bBack.getScene().getWindow();
	        temp.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
