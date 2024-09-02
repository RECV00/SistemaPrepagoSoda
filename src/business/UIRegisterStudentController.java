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
import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
import data.LogicData;
import data.StudentData;
import data.RechargeData;
import domain.Recharge;
import domain.Student;
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
	private Student studentToEdit =null;
	private boolean isEditing = false;// si es o no editado
	@FXML
	public void initialize(){
		cbGenderStudent.getItems().addAll("Masculino", "Femenino");
		cbGenderStudent.getSelectionModel().selectFirst();
		dpDateEntryStudent.setValue(LocalDate.now());
	}
	
	// Event Listener on Button[#bRegisterStudent].onAction
	@FXML
	public boolean saveDataStudent(ActionEvent event) throws IOException {
	    String messageError = validateForm();

	    if (!messageError.isEmpty()) {
	        notifyAction(messageError);
	        return false;
	    }

	    // Crear y configurar el objeto Student
	    Student student = new Student();
	    student.setCarnetStudent(tfCarnetStudent.getText());
	    student.setName(tfNameStudent.getText());
	    student.setEmail(tfEmailStudent.getText());
	    student.setPhone(Integer.parseInt(tfNumPhoneStudent.getText()));
	    student.setActive(rbYesStudent.isSelected());
	    student.setDateEntry(dpDateEntryStudent.getValue());
	    student.setGender(cbGenderStudent.getSelectionModel().getSelectedIndex() == 0 ? 'M' : 'F');
	    student.setMoneyAvailable(Double.parseDouble(tfMoneyAvailableStudent.getText()));

	    // Crear y configurar el objeto Recharge
	    Recharge recharge = new Recharge();
	    recharge.setCarnetStudent(tfCarnetStudent.getText());
	    recharge.setAmount(Double.parseDouble(tfMoneyAvailableStudent.getText()));
	    recharge.setDateEntry(dpDateEntryStudent.getValue());

	    // Confirmar el registro
	    Object[] options = {"Cancelar", "Registrar"};
	    int confirmOption = JOptionPane.showOptionDialog(
	            null, "¿Está seguro de guardar?",
	            "", JOptionPane.DEFAULT_OPTION,
	            JOptionPane.PLAIN_MESSAGE, null, options,
	            options[0]
	    );

	    if (confirmOption == 1) {
	        if (isEditing) {
	        	// Actualizar el estudiante y la recarga
	            boolean studentUpdated = StudentData.updateStudent(student, studentToEdit.getCarnetStudent());
	            boolean rechargeUpdated = RechargeData.updateRecharge(recharge, studentToEdit.getCarnetStudent());
	            
			    // Actualizar el estudiante y el recargo
			    if (studentUpdated && rechargeUpdated) {
			        notifyAction("Estudiante Actualizado Correctamente");
			    } else {
			        notifyAction("Error al actualizar Estudiante");
			    }
			} else {
				
				boolean studentSaved = StudentData.saveStudent(student);
	            boolean rechargeSaved = RechargeData.saveRecharge(recharge);
			    // Guardar el nuevo estudiante y el recargo
			    if (studentSaved && rechargeSaved) {
//			    	 && RechargeData.saveRecharge(recharge)
			        notifyAction("Estudiante Registrado Correctamente");
			        clearForm();
			    } else {
			        notifyAction("Error al Registrar");
			    }
			}
	    } else {
	        notifyAction("Se canceló el registro del estudiante");
	    }
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
			dpDateEntryStudent.setValue(LocalDate.now());
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
		    StringBuilder messageError = new StringBuilder();
		    String carnet= tfCarnetStudent.getText().trim();
		    
		    if (tfNameStudent.getText().isEmpty()) {
		        messageError.append("El Nombre es Requerido\n");
		    }
	   
		    if (carnet.isEmpty()) {		    	
		        messageError.append("El Carnet es Requerido\n");    
		        
		    } else if(!isEditing){
		    	//valida si el carnet existe
		    	List<Student> existingStudent = StudentData.getStudentList();
		    	for(Student student : existingStudent) {
		    		if(student.getCarnetStudent().equalsIgnoreCase(carnet)) {
		    		messageError.append("El Carnet ya existe, por favor ingresa un carnet diferente");
		    		break;
		    		}
		    	}
		    	
		    }else if (!carnet.matches("[a-zA-Z0-9]{1,10}")) {  // Permite letras y números, máxima de 10 caracteres
		        messageError.append("El Carnet debe contener solo letras y números y tener un máximo de 10 caracteres\n");
		    }
		    
		    
		    String email = tfEmailStudent.getText();
		    if (email.isEmpty()) {
		        messageError.append("El Correo es Requerido\n");
		    }

		    String phone = tfNumPhoneStudent.getText();
		    if (phone.isEmpty()) {
		        messageError.append("El Número Telefónico es Requerido\n");
		    } else if (!phone.matches("\\d{8,10}")) {  // Debe contener entre 8 y 10 dígitos
		        messageError.append("El Teléfono debe contener entre 8 y 10 dígitos\n");
		    }

		    String money = tfMoneyAvailableStudent.getText();
		    if (money.isEmpty()) {
		        messageError.append("El Monto es Requerido\n");
		    } else {
		        try {
		            double balance = Double.parseDouble(money);
		            if (balance <= 5000 || balance >= 15000) {
		                messageError.append("El Saldo debe estar entre 5000 y 15000\n");
		            }
		        } catch (NumberFormatException e) {
		            messageError.append("El Monto debe ser un número válido\n");
		        }
		    }

		    if (dpDateEntryStudent.getValue() == null) {
		        messageError.append("La Fecha de Ingreso es Requerida\n");
		    }

		    return messageError.toString();
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
		
		//se llena el formulario con los datos del estudiante que se va editar
		public void setStudentData(Student selectedStudent) {
			tfNameStudent.setText(( selectedStudent).getName());
			tfCarnetStudent.setText((selectedStudent).getCarnetStudent());
			tfCarnetStudent.setEditable(false);// no permite editar 
			tfEmailStudent.setText(( selectedStudent).getEmail());
			tfNumPhoneStudent.setText(String.valueOf(( selectedStudent).getPhone()));			
			tfMoneyAvailableStudent.setText(String.valueOf((selectedStudent).getMoneyAvailable()));
			tfMoneyAvailableStudent.setEditable(false);
			cbGenderStudent.getSelectionModel().select(( selectedStudent).getGender() == 'M' ? "Masculino" : "Femenino");
			dpDateEntryStudent.setValue((selectedStudent).getDateEntry());
			rbYesStudent.setSelected(( selectedStudent).isActive());
			rbNoStudent.setSelected(!( selectedStudent).isActive());
		    isEditing = true;
		    studentToEdit = selectedStudent;
		    }
		
}
