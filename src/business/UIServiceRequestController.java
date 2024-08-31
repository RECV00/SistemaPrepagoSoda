package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
//import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import java.io.IOException;
import javax.swing.JOptionPane;
import data.DisheData;
import data.LogicData;
import data.StudentData;
import domain.Dishe;
import domain.Student;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class UIServiceRequestController {

    @FXML
    private ComboBox<Student> cbStudentsList;
    @FXML
    private ComboBox<String> cbServiceDay;
    @FXML
    private RadioButton rbBreakfastDishe;
    @FXML
    private ToggleGroup Tipo;
    @FXML
    private RadioButton rbLunchDishe;
    @FXML
    private TableView<Dishe> tvDisheData;
    @FXML
    private TableColumn<Dishe, String> tcDataDishe;
    @FXML
    private TableColumn<Dishe, Double> tcPriceDishe;
    @FXML
    private TableColumn<Dishe, String> tcRequestDishe;
    private Button btnRegisterDishe;
    @FXML
    private Button btnBack;
	@FXML
	private Button bDelete;
	@FXML
	private Button bUpdate;

    private ObservableList<Dishe> disheList;
    private LogicData logicData = new LogicData(); // Instancia de LogicData

    @FXML
    public void initialize() {
//        // Configurar ComboBox de estudiantes
       cbStudentsList.setItems(FXCollections.observableArrayList(StudentData.getStudentList()));
//        // Configurar ComboBox de días
       cbServiceDay.getItems().addAll("Lunes", "Martes", "Miércoles", "Jueves", "Viernes");
//        // Configurar columnas de la TableView
        tcDataDishe.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getServiceName()));
        tcPriceDishe.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getServicePrice()));
        
//        boolean serviceHours = (rbLunchDishe.isSelected())? true : false;
//        loadDisheList(cbServiceDay.getSelectionModel().getSelectedItem(),serviceHours );
    }


    public void loadDisheList(String serviceDay, boolean serviceHours) {
        try {
            if (serviceHours && serviceDay.equals("Lunes")) {
                disheList = FXCollections.observableArrayList(DisheData.getMonday_LunchList());
            } else if (!serviceHours && serviceDay.equals("Lunes")) {
                disheList = FXCollections.observableArrayList(DisheData.getMonday_BreakfastList());
            } else if (serviceHours && serviceDay.equals("Martes")) {
                disheList = FXCollections.observableArrayList(DisheData.getTuesday_LunchList());
            } else if (!serviceHours && serviceDay.equals("Martes")) {
                disheList = FXCollections.observableArrayList(DisheData.getTuesday_BreakfastList());
            } else if (serviceHours && serviceDay.equals("Miércoles")) {
                disheList = FXCollections.observableArrayList(DisheData.getWednesday_LunchList());
            } else if (!serviceHours && serviceDay.equals("Miércoles")) {
                disheList = FXCollections.observableArrayList(DisheData.getWednesday_BreakfastList());
            } else if (serviceHours && serviceDay.equals("Jueves")) {
                disheList = FXCollections.observableArrayList(DisheData.getThursday_LunchList());
            } else if (!serviceHours && serviceDay.equals("Jueves")) {
                disheList = FXCollections.observableArrayList(DisheData.getThursday_BreakfastList());
            } else if (serviceHours && serviceDay.equals("Viernes")) {
                disheList = FXCollections.observableArrayList(DisheData.getFriday_LunchList());
            } else if (!serviceHours && serviceDay.equals("Viernes")) {
                disheList = FXCollections.observableArrayList(DisheData.getFriday_BreakfastList());
            }

            tvDisheData.setItems(disheList);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar los datos de los Alimentos.");
        }
    }

    @FXML
    public void registerNewDishe(ActionEvent event) {
        // Lógica para registrar un nuevo platillo
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIRegisterDishes.fxml"));
			Parent root = loader.load();
			UIRegisterDishesController controller = loader.getController();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			
			stage.setOnCloseRequest(e -> controller.closeWindows());
			Stage temp = (Stage) this.btnRegisterDishe.getScene().getWindow();
			temp.close();
			
		}catch(IOException e){
			
		}
    }

    
	// Event Listener on Button[#bDelete].onAction
	@FXML
	public void deleteDishes(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#bUpdate].onAction
	@FXML
	public void updateDishes(ActionEvent event) {
		// TODO Autogenerated
	}
    @FXML
    public void returnUIStart(ActionEvent event) {
        // Lógica para regresar al inicio
    	closeWindows();
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
	        Stage temp = (Stage) btnBack.getScene().getWindow();
	        temp.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}