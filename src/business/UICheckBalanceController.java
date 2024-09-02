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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class UICheckBalanceController {
	@FXML
	private Button bBack;
	@FXML
	private Button bRegisterNewStudent;
	@FXML
	private Label  lErrorValidate;
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
	@FXML
    private TableColumn<StudentRecharge, Boolean> tcRequestStudent;
	
	private ObservableList<StudentRecharge> observableList;

	  @FXML
	    public void initialize() {
		  carnetColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCarnetStudent()));
		  studentColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStudentName()));
	      dateRechargesColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRechargeDate()));
	      amountColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRechargeAmount()));
	        
	      // Configura la columna de CheckBox
	      setupCheckBoxColumn();
	      
	      loadConsultaList();
	  }
	  
//	  Manejo de CheckBox
	  private void setupCheckBoxColumn() {
		  tcRequestStudent.setCellValueFactory(cellData -> new SimpleObjectProperty<>(false)); 
		  tcRequestStudent.setCellFactory(col -> new TableCell<StudentRecharge, Boolean>() {
	            private final CheckBox checkBox = new CheckBox();

	            {
	            	checkBox.setOnAction(event -> {
	                    StudentRecharge item = getTableRow().getItem();
	                    if (item != null) {
	                        handleCheckBoxAction(item);
	                    }
	                });
	            }

	            @Override
	            protected void updateItem(Boolean item, boolean empty) {
	                super.updateItem(item, empty);
	                if (empty) {
	                    setGraphic(null);
	                } else {
	                    setGraphic(checkBox);
	                    checkBox.setSelected(item != null && item);
	                }
	            }
	        });
	    }
	// Aqui para el ACCION del JOptionPane cuando haya  seleccionado un platillo
	    private void handleCheckBoxAction(StudentRecharge selectedStudentRecharge) {
	        if(selectedStudentRecharge != null) {
	        	
	        	String[] options = {"Eliminar", "Actualizar", "Recargar saldo"};
		        int choice = JOptionPane.showOptionDialog(null, 
		                "Seleccione una acción para el estudiante seleccionado:", 
		                "Acción del Estudiante", 
		                JOptionPane.DEFAULT_OPTION, 
		                JOptionPane.INFORMATION_MESSAGE, 
		                null, 
		                options, 
		                options[0]);
		      
		        switch (choice) {
		            case 0: // Eliminar
		            	deleteStudent(selectedStudentRecharge);	            	
		            	loadConsultaList();// Actualiza la tabla después de eliminar		            	
		                tvDataStudent.refresh();
		                break;
		                
		            case 1: // Actualizar            
		            	 editStudent(selectedStudentRecharge); 
		                break;
		                
		            case 2: // Recargar
		            	RechargeStudent(selectedStudentRecharge);
		                break;
		                
		            default:
		                break;
	        	
		        	}	        
       }
 } 
//---------------------------------------------------------------------------------------------------------------------------------------------------------	   
	    @FXML
	  public void loadConsultaList() {
		    List<Student> students = StudentData.getStudentList();
		    List<Recharge> recharges = RechargeData.getRechargeList();

		    observableList = FXCollections.observableArrayList();


		    for (Student student : students) {
		        for (Recharge recharge : recharges) { // Validar el carnet es igual
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
		    tvDataStudent.setItems(observableList);
		}

	// Event Listener on Button[#bCheckBalance].onAction
	  @FXML
	  private void SearchBalance(ActionEvent event) throws IOException {
	      String carnet = tfSearchStudent.getText().trim();
	      
	      if (carnet.isEmpty()) {
	          JOptionPane.showMessageDialog(null, "Por favor, ingrese un número de carnet.");
	          return;
	      }

	      List<Student> students = StudentData.getStudentList();
	      List<Recharge> recharges = RechargeData.getRechargeList();

	      observableList = FXCollections.observableArrayList();
//	      ASIGNACION DE CLAVE PARA LA BUSQUEDA DE DATO
	      Map<String, Recharge> rechargeMap = recharges.stream()
	          .collect(Collectors.toMap(Recharge::getCarnetStudent, recharge -> recharge));
	      
	      Student student = students.stream().filter(s -> s.getCarnetStudent().equalsIgnoreCase(carnet)).findFirst().orElse(null);

	      if (student != null) {
	          Recharge recharge = rechargeMap.get(carnet);
	          if (recharge != null) {
	              StudentRecharge studentRecharge = new StudentRecharge(
	                  student.getCarnetStudent(),
	                  student.getName(),
	                  recharge.getDateEntry(),
	                  recharge.getAmount()
	              );
	              observableList.add(studentRecharge);
	          } else {
	              JOptionPane.showMessageDialog(null, "No se encontraron recargas para el estudiante con carnet " + carnet + ".");
	              loadConsultaList();
	          }
	      } else {
	          JOptionPane.showMessageDialog(null, "Estudiante con carnet " + carnet + " no encontrado.");
	          loadConsultaList();
	      }

	      tvDataStudent.setItems(observableList);
	  }

//	  Levanta ventana de Recargas
	  public void RechargeStudent(StudentRecharge  selectedStudent) {		
	        
			if (selectedStudent != null) {
	            try {
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIRecharge.fxml"));
	                Parent root = loader.load();
	                UIRechargeController controller = loader.getController();
	                controller.recoveredData(selectedStudent); // Pasar la estudiante seleccionada
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
		
//	  Levanta Ventana para editar
	  public void editStudent(StudentRecharge selectedStudentRecharge) {
			 
		    if (selectedStudentRecharge != null) {
		        
		    	String carnet = selectedStudentRecharge.getCarnetStudent();		        
		        // Buscar el estudiante en el archivo JSON
		        Student student = StudentData.getStudentByCarnet(carnet);
		        
		        if (student != null) {
		            try {
		                FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIRegisterStudent.fxml"));
		                Parent root = loader.load();		                
		                UIRegisterStudentController controller = loader.getController();		                		              
		                controller.setStudentData(student);	
		                Scene scene = new Scene(root);
		                Stage stage = new Stage();
		                stage.setScene(scene);
		                stage.show();		           
		                Stage currentStage = (Stage) tvDataStudent.getScene().getWindow();
		                currentStage.close();
		                
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Estudiante no encontrado.");
		        }
		    }
		}
		
		public void deleteStudent(StudentRecharge selectedStudentRecharge) {
			 if (selectedStudentRecharge != null) {
				 String carnet = selectedStudentRecharge.getCarnetStudent();		        
			        // Buscar el estudiante en el archivo JSON
			     Student student = StudentData.getStudentByCarnet(carnet);
				 
				 if(student != null) {
					 
					 int confirmOption = JOptionPane.showConfirmDialog(
				                null, "¿Está seguro de que desea eliminar este estudiante?", "Confirmar eliminación", 
				                JOptionPane.YES_NO_OPTION
				            );
				            if (confirmOption == JOptionPane.YES_OPTION) {
				                // Eliminar la estudiante de la lista y del archivo JSON
				                if (StudentData.deleteStudent( student)) {
				                	loadConsultaList();
				                    JOptionPane.showMessageDialog(null, "Estudiante eliminado correctamente.");
				                } else {
				                    JOptionPane.showMessageDialog(null, "Error al eliminar al Estudiante.");
				                }
				            } 
				  }else {
			            JOptionPane.showMessageDialog(null, "Estudiante no encontrado..");
			        }

			 }else {
				 
				 JOptionPane.showMessageDialog(null, "Por favor, seleccione un estudiante para eliminar.");
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
