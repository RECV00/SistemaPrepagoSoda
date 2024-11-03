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
		cbServiceDayDishe.getItems().addAll("Lunes","Martes","Miércoles","Jueves","Viernes");
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
		boolean serviceHours = (rbLunchDishe.isSelected())? true : false;
		dishe.setServiceHours(serviceHours);
		dishe.setServiceDay(cbServiceDayDishe.getSelectionModel().getSelectedItem());
		dishe.setServiceName(tfServiceNameDishe.getText());
		dishe.setServicePrice(Double.parseDouble(tfServicePriceDishe.getText()));
		
		String serviceHoursText = serviceHours ? "Almuerzo" : "Desayuno";
		
		int confirmOption = 
				JOptionPane.showConfirmDialog(
				null, 
				"¿Está seguro de agregar un nuevo servicio para el día " + cbServiceDayDishe.getSelectionModel().getSelectedItem() +
			    " al horario " + serviceHoursText + "?", 
			    "", 
			    JOptionPane.YES_NO_OPTION, 
			    JOptionPane.QUESTION_MESSAGE
						);
		
		if (confirmOption == JOptionPane.YES_OPTION) {
		    if (isEditing) { // actualiza el platillo
		        if (DisheData.updateDishesByServiceName(dishe, dishe.getServiceName(), serviceHours, cbServiceDayDishe.getSelectionModel().getSelectedItem())) {
		            notifyAction("Platillo actualizado correctamente");
		        } else {
		            notifyAction("Error al actualizar el Platillo");
		        }
		    } else { // guarda el platillo
		        if (DisheData.saveDishe(dishe, serviceHours, cbServiceDayDishe.getSelectionModel().getSelectedItem())) {
		            notifyAction("Platillo registrado correctamente");
		            clearForm();
		        } else {
		            notifyAction("Error al guardar el Platillo");
		        }
		    }
		} else {
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
			//se llena el formulario con los datos del platillo que se va editar
			public void setDisheData(Object selectedDishe) {
				tfServiceNameDishe.setText(((Dishe) selectedDishe).getServiceName());
				tfServiceNameDishe.setEditable(false);
				cbServiceDayDishe.getSelectionModel().select(((Dishe) selectedDishe).getServiceDay());
				rbLunchDishe.setSelected(((Dishe) selectedDishe).isServiceHours());			
				rbBreakfastDishe.setSelected(!((Dishe) selectedDishe).isServiceHours());						
				tfServicePriceDishe.setText(String.valueOf(((Dishe) selectedDishe).getServicePrice()));			    
				isEditing = true;
			    disheToEdit = (Dishe) selectedDishe;
			    }
	//----------------------------------------------------------------------------------------------		  
			@FXML
			public void closeWindows() {
				
				try {
					 FXMLLoader loader = new FXMLLoader (getClass().getResource("/presentation/UIViewDishes.fxml"));
			        Parent root = loader.load();
					Scene scene = new Scene(root);
					Stage stage = new Stage();
			        stage.setScene(scene);
			        stage.show();			        
			        Stage temp = (Stage) btnBack.getScene().getWindow();
			        temp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
}
