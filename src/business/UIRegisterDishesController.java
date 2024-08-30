package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.TextField;

import java.io.IOException;

import javax.swing.JOptionPane;

import data.DisheData;
import domain.Dishe;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.ComboBox;

import javafx.scene.control.RadioButton;

public class UIRegisterDishesController {
	@FXML
	private Label lServiceHours;
	@FXML
	private Label lServiceDay;
	@FXML
	private Label lServiceName;
	@FXML
	private Label lServicePrice;
	@FXML
	private RadioButton rbBreakfastDishe;
	@FXML
	private ToggleGroup Tipo;
	@FXML
	private RadioButton rbLunchDishe;
	@FXML
	private ComboBox<String> cbServiceDayDishe;
	@FXML
	private TextField tfServiceNameDishe;
	@FXML
	private TextField tfServicePriceDishe;
	@FXML
	private Label lErrorValidate;
	@FXML
	private Button btnSaveDishe;
	@FXML
	private Button btnBack;
	
	private Dishe disheToEdit = null;
	private boolean isEditing = false;
	
	@FXML
	public void initialize() {
		cbServiceDayDishe.getItems().addAll("Lunes","Martes","Miercoles","Jueves","Viernes");
		cbServiceDayDishe.getSelectionModel().selectFirst();
	}
	// Event Listener on Button[#btnSaveDishe].onAction
	@FXML
	public boolean saveDataDishe(ActionEvent event) {
		
		String messageError = validateForm();
		
		if(!messageError.isEmpty()) {
			notifyAction(messageError);
			return false;
		}
		
		Dishe dishe = new Dishe();
		boolean serviceHourse = (rbLunchDishe.isSelected())? true : false;
		dishe.setServiceHours(serviceHourse);
		dishe.setServiceDay(cbServiceDayDishe.getSelectionModel().getSelectedItem());
		dishe.setServiceName(tfServiceNameDishe.getText());
		dishe.setServicePrice(Double.parseDouble(tfServicePriceDishe.getText()));
		
		Object[] options = {"NO","SÍ"};
		
		int confirmOption = 
				JOptionPane.showOptionDialog(
				null, "¿Esta seguro que desea guardar el alimento?","",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options,
				options[0]
						);
		
		if (confirmOption == 1) {
			if(isEditing) {//actualiza el platillo
				if(DisheData.updateDishesByServiceName(dishe, disheToEdit.getServiceName(), serviceHourse,cbServiceDayDishe.getSelectionModel().getSelectedItem() )) {
					notifyAction("Platillo actualizado correctamente");
				}else {
					notifyAction("Error al  actualizadar el Alimento");
				}
				
			}else {//guardar el platillo
				if(DisheData.saveDishe(dishe, serviceHourse, cbServiceDayDishe.getSelectionModel().getSelectedItem())){
					notifyAction("Platillo registrada correctamente");
					clearForm();	
				}else {
					notifyAction("Error al guardar Platillo");
					
				}
			}
		}else {
			notifyAction("Se canceló el registro del Platillo");
		}
		
		return true;
		
	}
	// Event Listener on Button[#btnBack].onAction
	@FXML
	public void returnMain(ActionEvent event) {
		closeWindows();
	}
	
	//--------------------------------------------------------------------------------------------------
			private void clearForm() {
				tfServiceNameDishe.setText("");
				tfServicePriceDishe.setText("");
				cbServiceDayDishe.getSelectionModel().selectFirst();
				
			}
	//--------------------------------------------------------------------------------------------------		
			private void notifyAction(String message) {
				lErrorValidate.setText(message);
				Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3),e -> lErrorValidate.setText("")));
				timeline.setCycleCount(1);
				timeline.play();
			}
	//----------------------------------------------------------------------------------------------------
			private String validateForm() {
				
				String messageError = "";
				if(tfServiceNameDishe.getText().isEmpty()) {
					messageError += "  El Nombre del servicio es Requerido";
				}
				if(tfServicePriceDishe.getText().isEmpty()) {
					messageError += "  El Precio del servicio es Requerido";
				}
				
				return messageError;
			}
	//----------------------------------------------------------------------------------------------		  
			@FXML
			public void closeWindows() {
				
				try {
					 FXMLLoader loader = new FXMLLoader (getClass().getResource("/presentation/UIServiceRequest.fxml"));
			        Parent root = loader.load();
					Scene scene = new Scene(root);
			        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				
			        Stage stage = new Stage();
			        stage.setScene(scene);
			        stage.show();
			        
			        Stage temp = (Stage) btnBack.getScene().getWindow();
			        temp.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
}
