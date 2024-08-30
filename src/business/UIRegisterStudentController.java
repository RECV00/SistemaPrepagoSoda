package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.ComboBox;

import javafx.scene.control.RadioButton;

import javafx.scene.control.DatePicker;

public class UIRegisterStudentController {
	@FXML
	private Button bRegisterStudent;
	@FXML
	private Button bBackStudent;
	@FXML
	private ComboBox<String> cbGenderStudent;
	@FXML
	private DatePicker dpDateEntryStudent;
	@FXML
	private Label lNameStudent;
	@FXML
	private Label lCarnetStudent;
	@FXML
	private Label lDateEntry;
	@FXML
	private Label lNumPhone;
	@FXML
	private Label lemail;
	@FXML
	private Label lIsActive;
	@FXML
	private Label lGender;
	@FXML
	private TextField tfNameStudent;
	@FXML
	private TextField tfCarnetStudent;
	@FXML
	private TextField tfEmailStudent;
	@FXML
	private TextField tfNumPhoneStudent;
	@FXML
	private TextField tfMoneyAvailableStudent;
	@FXML
	private RadioButton rbYesStudent;
	@FXML
	private ToggleGroup TIPO;
	@FXML
	private RadioButton rbNoStudent;
	@FXML
	private Label lMoneyAvailable;
	@FXML
	private Label lLineError;
	@FXML
	private Label lTitule;

	@FXML
	public void initialize(){
		cbGenderStudent.getItems().addAll("Masculino", "Femenino");
		cbGenderStudent.getSelectionModel().selectFirst();
	}
	// Event Listener on Button[#bRegisterStudent].onAction
	@FXML
	public boolean saveDataStudent(ActionEvent event) {
		return true;
	}
	// Event Listener on Button[#bBackStudent].onAction
	@FXML
	public void returnMain(ActionEvent event) {
		closeWindows();
	}
	
	
	
	
//--------------------------------------------------------------------------------------------------
		private void clearForm() {
			tfNameStudent.setText("");
			tfCarnetStudent.setText("");
			tfEmailStudent.setText("");
			tfNumPhoneStudent.setText("");
			tfMoneyAvailableStudent.setText("");
			cbGenderStudent.getSelectionModel().selectFirst();
			dpDateEntryStudent.setValue(null);
		}
//--------------------------------------------------------------------------------------------------		
		private void notifyAction(String message) {
			lLineError.setText(message);
			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3),e -> lLineError.setText("")));
			timeline.setCycleCount(1);
			timeline.play();
		}
//----------------------------------------------------------------------------------------------------
		private String validateForm() {
			
			String messageError = "";
			if(tfNameStudent.getText().isEmpty()) {
				messageError += "  El Nombre es Requerido";
			}
			if(tfCarnetStudent.getText().isEmpty()) {
				messageError += "  El Carnet es Requerido";
			}
			if(tfEmailStudent.getText().isEmpty()) {
				messageError += "  El Correo es Requerido";
			}
			if(tfNumPhoneStudent.getText().isEmpty()) {
				messageError += "  El Numero Telefonico es Requerido";
			}
			if(tfMoneyAvailableStudent.getText().isEmpty()) {
				messageError += "  El Monto es Requerido";
			}
			if(dpDateEntryStudent.getValue() == null) {
				messageError += "  La Fecha de Ingreso es Requerido";
			}
			return messageError;
		}
//----------------------------------------------------------------------------------------------		  
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
		        
		        Stage temp = (Stage) bBackStudent.getScene().getWindow();
		        temp.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
}
