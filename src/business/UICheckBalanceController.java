package business;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;

import domain.Recharge;
import domain.Student;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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

//		public void loadConsultaList() {
//		    
//		    List<Pet> pets = PetData.getPetList();
//		    
//		    if (pets != null) {
//		    
//		        ObservableList<Pet> observableList = FXCollections.observableArrayList(pets);
//		        tvDataStudent.setItems(observableList);
//		    }
//		}
	// Event Listener on Button[#bCheckBalance].onAction
			@FXML
			 private void SearchBalance(ActionEvent event) {
		        // Implementación de la búsqueda de saldo
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
