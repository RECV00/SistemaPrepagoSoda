package business;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import data.StudentData;
import data.RechargeData;
import domain.Recharge;
import domain.Student;
import domain.StudentRecharge;
import javafx.beans.property.SimpleObjectProperty;
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
	private TableView<StudentRecharge> tvDataStudent;
	@FXML
	private TableColumn<StudentRecharge, String> carnetColumn;
	@FXML
	private TableColumn<StudentRecharge, String> studentColumn;
	@FXML
	private TableColumn<StudentRecharge, LocalDate> dateRechargesColumn;
	@FXML
	private TableColumn<StudentRecharge, Double> amountColumn;
	
	private ObservableList<StudentRecharge> observableList;

	
	  @FXML
	    public void initialize() {
		  carnetColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCarnetStudent()));
		  studentColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStudentName()));
	      dateRechargesColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRechargeDate()));
	      amountColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRechargeAmount()));
	      loadConsultaList();
	  }
	  
//---------------------------------------------------------------------------------------------------------------------------------------------------------	   
	  @FXML
	  public void loadConsultaList() {
		    List<Student> students = StudentData.getStudentList();
		    List<Recharge> recharges = RechargeData.getRechargeList();

		    
		    observableList = FXCollections.observableArrayList();

//		    recorrer la lista de estudiantes
		    for (Student student : students) {
//		         ruscar la recarga de estudiante
		        for (Recharge recharge : recharges) {
		            if (recharge.getCarnetStudent().equals(student.getCarnetStudent())) {
//		                combina datos de los dos json
		                StudentRecharge studentRecharge = new StudentRecharge(
		                    student.getCarnetStudent(),
		                    student.getName(),
		                    recharge.getDateEntry(),
		                    recharge.getAmount()
		                );
		                observableList.add(studentRecharge);
		                break;
		            }
		        }
		    }

		   
		    System.out.println(observableList);
		    tvDataStudent.setItems(observableList);
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
	                controller.populateForm(selectedStudent); // Pasar la estudiante seleccionada
	                Scene scene = new Scene(root);
					Stage stage = new Stage();
					stage.setScene(scene);
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
		                // Eliminar la estudiante de la lista y del archivo JSON
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
