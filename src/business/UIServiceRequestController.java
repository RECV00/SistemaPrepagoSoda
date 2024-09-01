package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
//import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Vector;

import javax.swing.JOptionPane;
import data.DisheData;
import data.LogicData;
import data.StudentData;
import domain.Dishe;
import domain.Student;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    private TableColumn<Dishe, Boolean> tcRequestDishe;
    @FXML
    private Button btnRegisterDishe;
    @FXML
    private Button btnBack;
	@FXML
	private Button bDelete;
	@FXML
	private Button bUpdate;
	@FXML
	private Label lErrorVa;
	
	private Vector<Dishe> disheListM;

    private ObservableList<Dishe> disheList;
    
    private LogicData logicData = new LogicData(); // Instancia de LogicData

    @FXML
    public void initialize() {
//        // Configurar ComboBox de estudiantes
       cbStudentsList.setItems(FXCollections.observableArrayList(StudentData.getStudentList()));
       cbStudentsList.getSelectionModel().selectFirst();
//        // Configurar ComboBox de días
       cbServiceDay.getItems().addAll("Lunes", "Martes", "Miércoles", "Jueves", "Viernes");
       cbServiceDay.getSelectionModel().selectFirst();
//        // Configurar columnas de la TableView
        tcDataDishe.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getServiceName()));
        tcPriceDishe.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getServicePrice()));
        
        tcRequestDishe.setCellValueFactory(cellData -> {
            Dishe dishe = cellData.getValue();
            SimpleBooleanProperty property = new SimpleBooleanProperty(dishe.isRequested());
            property.addListener((observable, oldValue, newValue) -> dishe.setRequested(newValue));
            return property;
        });
        
        validateForm();
        tcRequestDishe.setCellFactory(column -> new CheckBoxTableCell<Dishe, Boolean>() {
            @Override
            public void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    CheckBox checkBox = new CheckBox();
                    checkBox.setSelected(item);
                    checkBox.setOnAction(event -> {
                        Dishe dishe = getTableView().getItems().get(getIndex());
                        boolean isSelected = checkBox.isSelected();
                        dishe.setRequested(isSelected);
                     // Mostrar el JOptionPane solo si el CheckBox es seleccionado
                        if (isSelected) {
                            handleCheckBoxAction(dishe);
                        }
                        
                    });
                    setGraphic(checkBox);
                }
            }
        });
        //llenar la tabla inicialmente
        //updateTableView();    
     // Event listener para el ComboBox de días
        cbServiceDay.setOnAction(event -> updateTableView());
        rbBreakfastDishe.setOnAction(event -> updateTableView());
        rbLunchDishe.setOnAction(event -> updateTableView());
        
    }
    
    private void handleCheckBoxAction(Dishe selectedDishe) {
        String[] options = {"Eliminar", "Actualizar", "Solicitar"};
        int choice = JOptionPane.showOptionDialog(null, 
                "Seleccione una acción para el platillo seleccionado:", 
                "Acción del Platillo", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.INFORMATION_MESSAGE, 
                null, 
                options, 
                options[0]);

        switch (choice) {
            case 0: // Eliminar
            	// Llama al método para eliminar el platillo
            	logicData.deleteDishes(selectedDishe, rbLunchDishe.isSelected(), cbServiceDay.getSelectionModel().getSelectedItem());
                updateTableView(); // Actualiza la tabla después de eliminar
                notifyAction("Platillo eliminado correctamente");
                break;
              
                
            case 1: // Actualizar
                // Implementa la lógica de actualización según tus necesidades
                JOptionPane.showMessageDialog(null, "Actualizar platillo no implementado aún.");
                
                break;
                
            case 2: // Solicitar
                handleRequestDishe(selectedDishe);
                break;
                
            default:
                break;
        }
    }
    // Método que actualiza la tabla según la selección
    private void updateTableView() {
        boolean serviceHours = rbLunchDishe.isSelected(); // Determina si es almuerzo o desayuno
        String selectedDay = cbServiceDay.getSelectionModel().getSelectedItem(); // Día seleccionado
     // Solo cargar los datos si ambos ComboBox y RadioButton están seleccionados
        if (selectedDay != null && !selectedDay.isEmpty() && (rbBreakfastDishe.isSelected() || rbLunchDishe.isSelected())) {
            loadDisheList(selectedDay, serviceHours); // Carga los datos según la selección
        } else {
            // Limpiar la tabla si no se han realizado las selecciones
            tvDisheData.setItems(FXCollections.observableArrayList());
        }
    }
    
    private void handleRequestDishe(Dishe dishe) {
        Student selectedStudent = cbStudentsList.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún estudiante.");
            return;
        }

        double studentBalance = selectedStudent.getMoneyAvailable(); // Obtén el saldo del estudiante

        if (studentBalance >= dishe.getServicePrice()) {
            int response = JOptionPane.showConfirmDialog(null, 
                    "¿Desea solicitar el platillo seleccionado?", 
                    "Confirmación", 
                    JOptionPane.YES_NO_OPTION);
            
            if (response == JOptionPane.YES_OPTION) {
                selectedStudent.setMoneyAvailable(studentBalance - dishe.getServicePrice()); // Actualiza el saldo del estudiante
               
                // Guardar los cambios en el archivo JSON
                if (StudentData.updateStudent(selectedStudent, selectedStudent.getCarnetStudent())) {
                    JOptionPane.showMessageDialog(null, 
                            "Solicitud confirmada.\n" +
                            "Platillo: " + dishe.getServiceName() + "\n" +
                            "Precio: " + dishe.getServicePrice() + "\n" +
                            "Saldo restante: " + selectedStudent.getMoneyAvailable());
                } else {
                    JOptionPane.showMessageDialog(null, "Error al actualizar el saldo del estudiante.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, 
                    "Saldo insuficiente para solicitar este platillo.");
        }
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
  //--------------------------------------------------------------------------------------------------		
	private void notifyAction(String message) {
		lErrorVa.setText(message);
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3),e -> lErrorVa.setText("")));
		timeline.setCycleCount(1);
		timeline.play();
	}
//----------------------------------------------------------------------------------------------------
	private String validateForm() {
		
		String messageError = "";
		if(cbServiceDay.getValue() == null) {
			messageError += "  El día del servicio es Requerido";
		}
		
		
		return messageError;
	}
    @FXML
    public void registerNewDishe(ActionEvent event) {
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

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al abrir la ventana de registro de platillos.");
        }
    }



//    @FXML
//    public void updateDishes(ActionEvent event) {
//        Dishe selectedDishe = tvDisheData.getSelectionModel().getSelectedItem();
//        if (selectedDishe != null) {
//            // Aquí podrías abrir una ventana de edición similar a la de registro
//            // para modificar los datos del platillo seleccionado.
//            try {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIUpdateDishes.fxml"));
//                Parent root = loader.load();
//                UIRegisterDishesController controller = loader.getController();
//                controller.setDisheData(selectedDishe); // Pasar el platillo seleccionado al controlador de actualización
//                Scene scene = new Scene(root);
//                Stage stage = new Stage();
//                stage.setScene(scene);
//                stage.show();
//
//                stage.setOnCloseRequest(e -> controller.closeWindows());
//                Stage temp = (Stage) this.bUpdate.getScene().getWindow();
//                temp.close();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                JOptionPane.showMessageDialog(null, "Error al abrir la ventana de actualización de platillos.");
//            }
//        } else {
//            JOptionPane.showMessageDialog(null, "Seleccione un platillo para actualizar.");
//        }
//    }

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