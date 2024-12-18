package business;

import data.LogicUIServiceRequestController;
import data.ServerConnection;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.JOptionPane;
import java.io.IOException;

public class UIViewDishesController {

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
    private Label lErrorVa;
    private ServerConnection serverConnection;
    private ObservableList<Dishe> disheList;
    private LogicUIServiceRequestController lServiceRequest = new LogicUIServiceRequestController(); // Instancia de LogicData

    @FXML
    public void initialize() {
        // Configurar ComboBox de días
        cbServiceDay.getItems().addAll("Lunes", "Martes", "Miércoles", "Jueves", "Viernes");
        // Configurar columnas de la TableView
        tcDataDishe.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getServiceName()));
        tcPriceDishe.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getServicePrice()));

        tcRequestDishe.setCellValueFactory(cellData -> {
            Dishe dishe = cellData.getValue();
            SimpleBooleanProperty property = new SimpleBooleanProperty(dishe.isRequested());
            property.addListener((observable, oldValue, newValue) -> dishe.setRequested(newValue));
            return property;
        });

        validateForm();
        setupCheckBox();

        // Event listener para el ComboBox de días
        cbServiceDay.setOnAction(event -> updateTableView());
        rbBreakfastDishe.setOnAction(event -> updateTableView());
        rbLunchDishe.setOnAction(event -> updateTableView());
    }

    private void setupCheckBox() {
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
    }

    private void handleCheckBoxAction(Dishe selectedDishe) {
        String[] options = {"Eliminar", "Actualizar"};
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
                lServiceRequest.deleteDishes(selectedDishe, rbLunchDishe.isSelected(), cbServiceDay.getSelectionModel().getSelectedItem());
                updateTableView();
                notifyAction("Platillo eliminado correctamente");
                tvDisheData.refresh();
                break;

            case 1: // Actualizar
                updateDishes(selectedDishe);
                break;

            default:
                break;
        }
    }

    private void updateTableView() {
        boolean serviceHours = rbLunchDishe.isSelected(); // Determina si es almuerzo o desayuno
        String selectedDay = cbServiceDay.getSelectionModel().getSelectedItem(); // Día seleccionado

        // Solo cargar los datos si ambos ComboBox y RadioButton están seleccionados
        if (selectedDay != null && !selectedDay.isEmpty() && (rbBreakfastDishe.isSelected() || rbLunchDishe.isSelected())) {
            lServiceRequest.loadDisheList(selectedDay, serviceHours, disheList, tvDisheData);
        } else {
            // Limpiar la tabla si no se han realizado las selecciones
            tvDisheData.setItems(FXCollections.observableArrayList());
        }
    }

    private void notifyAction(String message) {
        lErrorVa.setText(message);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> lErrorVa.setText("")));
        timeline.setCycleCount(1);
        timeline.play();
    }

    private String validateForm() {
        String messageError = "";
        if (cbServiceDay.getValue() == null) {
            messageError += "El día del servicio es requerido.";
        }
        return messageError;
    }

    @FXML
    public void registerNewDishe(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIRegisterDishes.fxml"));
            Parent root = loader.load();
            UIRegisterDishesController controller = loader.getController();
            controller.setServerConnection(serverConnection);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> controller.closeWindows());
            Stage temp = (Stage) this.btnRegisterDishe.getScene().getWindow();
            temp.hide();

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al abrir la ventana de registro de platillos.");
        }
    }

    public void updateDishes(Dishe selectedDishe) {
        if (selectedDishe != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIRegisterDishes.fxml"));
                Parent root = loader.load();
                UIRegisterDishesController controller = loader.getController();
                controller.setDisheData(selectedDishe); // Pasar el platillo seleccionado al controlador de actualización
                controller.setServerConnection(serverConnection);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

                stage.setOnCloseRequest(e -> controller.closeWindows());
                Stage temp = (Stage) tvDisheData.getScene().getWindow();
                temp.close();

            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al abrir la ventana de actualización de platillos.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un platillo para actualizar.");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIProfile.fxml"));
            Parent root = loader.load();
            // Obtener el controlador de la nueva ventana
            UIProfileController profileController = loader.getController();
            // Pasar la conexión del servidor al controlador de perfil
            profileController.setServerConnection(serverConnection);
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

    public void setServerConnection(ServerConnection serverConnection) {
        this.serverConnection = serverConnection; // Establecer la conexión del servidor
    }
}