package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import javax.swing.JOptionPane;
import data.DisheData;
import data.LogicData;
import data.StudentData;
import domain.Dishe;
import domain.Student;
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
    private TableColumn<Dishe, CheckBox> tcRequestDishe;
    @FXML
    private Button btnRegisterDishe;
    @FXML
    private Button btnBack;

    private ObservableList<Dishe> disheList;
    private LogicData logicData = new LogicData(); // Instancia de LogicData

    @FXML
    public void initialize() {
//        // Configurar ComboBox de estudiantes
       cbStudentsList.setItems(FXCollections.observableArrayList(StudentData.getStudentList()));
//        // Configurar ComboBox de días
       cbServiceDay.getItems().addAll("Lunes", "Martes", "Miércoles", "Jueves", "Viernes");
//        // Configurar columnas de la TableView
//        tcDataDishe.setCellValueFactory(new PropertyValueFactory<Dishe, String>("serviceName"));
//        tcPriceDishe.setCellValueFactory(new PropertyValueFactory<Dishe, Double>("servicePrice"));

//        // Configurar columna de CheckBox
//        tcRequestDishe.setCellFactory(col -> {
//            TableCell<Dishe, CheckBox> cell = new TableCell<Dishe, CheckBox>() {
//                private final CheckBox checkBox = new CheckBox();
//
//                @Override
//                protected void updateItem(CheckBox item, boolean empty) {
//                    super.updateItem(item, empty);
//                    if (empty) {
//                        setGraphic(null);
//                    } else {
//                        Dishe dishe = getTableView().getItems().get(getIndex());
//                        checkBox.selectedProperty().setValue(dishe.isSelected());
//                        // Obtener el estudiante seleccionado en el ComboBox
//                        Student selectedStudent = cbStudentsList.getSelectionModel().getSelectedItem();
//                        // Llamar al método handleCheckBox de LogicData
//                        logicData.handleCheckBox(checkBox, dishe, selectedStudent);
//
//                        setGraphic(checkBox);
//                    }
//                }
//            };
//            return cell;
//        });
        
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