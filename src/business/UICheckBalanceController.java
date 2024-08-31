package business;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
import data.StudentData;
import domain.Recharge;
import domain.Student;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class UICheckBalanceController {
	@FXML
	private Button bBack;
	@FXML
	private Button bRegisterNewStudent;
	@FXML
	private Button btnEditable;
	@FXML
	private Button btnDelete;
	@FXML
	private Button bCheckBalance;
	@FXML
	private TextField tfSearchStudent;
	@FXML
	private TableView<Object> tvDataStudent;
	@FXML
	private TableColumn<Student, String> carnetColumn;
	@FXML
	private TableColumn<Student, String> studentColumn;
	@FXML
	private TableColumn<Recharge, LocalDate> dateRechargesColumn;
	@FXML
	private TableColumn<Recharge, Double> amountColumn;

	
	  @FXML
	    public void initialize() {
		  carnetColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCarnetStudent()));
		  studentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
	      dateRechargesColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDataEntry()));
	      amountColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAmount()));
	    }
	  
//---------------------------------------------------------------------------------------------------------------------------------------------------------	   

		public void loadConsultaList() {
		    
		    List<Student> students = StudentData.getStudentList();		    
		    if (students != null) {		    
		        ObservableList<Object> observableList = FXCollections.observableArrayList(students);
		        tvDataStudent.setItems(observableList);
		    }
		}
	// Event Listener on Button[#bCheckBalance].onAction
			@FXML
			 private void SearchBalance(ActionEvent event) {
		        // Implementación de la búsqueda de saldo
		    }
			
		@FXML
		public void editStudent(ActionEvent event) {		
			Object selectedStudent = tvDataStudent.getSelectionModel().getSelectedItem();
	        
			if (tvDataStudent != null) {
	            try {
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIRegisterStudent.fxml"));
	                Parent root = loader.load();
	                UIRegisterStudentController controller = loader.getController();
	                controller.populateForm(selectedStudent); // Pasar la mascota seleccionada
	                Stage stage = new Stage();
	                stage.setScene(new Scene(root));
	                stage.show();
	                // Cerrar la ventana de reporte
	                Stage currentStage = (Stage) tvDataStudent.getScene().getWindow();
	                currentStage.close();

	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
		}
		
		@FXML
		public void deleteStudent(ActionEvent event) {
			 Object selectedStudent = tvDataStudent.getSelectionModel().getSelectedItem();		       		       
			 if (selectedStudent != null) {
		            int confirmOption = JOptionPane.showConfirmDialog(
		                null, "¿Está seguro de que desea eliminar este estudiante?", "Confirmar eliminación", 
		                JOptionPane.YES_NO_OPTION
		            );
		            if (confirmOption == JOptionPane.YES_OPTION) {
		                // Eliminar la mascota de la lista y del archivo JSON
		                if (StudentData.deleteStudent((Student) selectedStudent)) {
		                	loadConsultaList();
		                    JOptionPane.showMessageDialog(null, "Mascota eliminada correctamente.");
		                } else {
		                    JOptionPane.showMessageDialog(null, "Error al eliminar la mascota.");
		                }
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Por favor, seleccione una mascota para eliminar.");
		        }
		}
		// Event Listener on Button[#bBack].onAction
		@FXML
		 private void returnMain(ActionEvent event) {
	        closeWindows();
	    }
		// Event Listener on Button[#bRegisterNewStudent].onAction
		@FXML
	    private void registerNewStudent(ActionEvent event) {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIRegisterStudent.fxml"));
	            Parent root = loader.load();
	            UIRegisterStudentController controller = loader.getController();
//	            controller.initialize();
	            Scene scene = new Scene(root);
	            Stage stage = new Stage();
	            stage.setScene(scene);
	            stage.show();
	            stage.setOnCloseRequest(e -> controller.closeWindows());
	            Stage currentStage = (Stage) bBack.getScene().getWindow();
	            currentStage.close();

	        } catch (IOException e) {
	            e.printStackTrace(); 
	        }
	    }
		
		@FXML
		public void closeWindows() {		
			try {
				 FXMLLoader loader = new FXMLLoader (getClass().getResource("/presentation/UIStart.fxml"));
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
