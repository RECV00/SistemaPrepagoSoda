package business;

import data.ServerConnection;
import data.UserData;
import domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Optional;

public class UIViewStudentController {

    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Integer> idColumn;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, Integer> phoneColumn;
    @FXML
    private TableColumn<User, Boolean> genderColumn;
    @FXML
    private TableColumn<User, Boolean> isActiveColumn;
    @FXML
    private TableColumn<User, Double> moneyAvailableColumn;
    @FXML
    private TableColumn<User, LocalDate> dateEntryColumn;
    @FXML
    private TableColumn<User, String> photoColumn;
    @FXML
    private TableColumn<User, String> tipeColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnRecharge;
    @FXML
    private Button btnBack;

    private ObservableList<User> userList;

    private ServerConnection serverConnection;

    @FXML
    public void initialize() {
        // Obtener la instancia Singleton de ServerConnection
        this.serverConnection = ServerConnection.getInstance();

        // Inicializar las columnas de la tabla
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        // Personalizar columna de género
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        genderColumn.setCellFactory(column -> new TableCell<User, Boolean>() {
            protected void updateItem(Boolean gender, boolean empty) {
                super.updateItem(gender, empty);
                setText(empty || gender == null ? null : gender ? "Femenino" : "Masculino");
            }
        });

        // Personalizar columna de estado activo
        isActiveColumn.setCellValueFactory(new PropertyValueFactory<>("isActive"));
        isActiveColumn.setCellFactory(column -> new TableCell<User, Boolean>() {
            protected void updateItem(Boolean isActive, boolean empty) {
                super.updateItem(isActive, empty);
                setText(empty || isActive == null ? null : isActive ? "Activo" : "Inactivo");
            }
        });

        moneyAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("moneyAvailable"));
        dateEntryColumn.setCellValueFactory(new PropertyValueFactory<>("dateEntry"));
        photoColumn.setCellValueFactory(new PropertyValueFactory<>("photoRoute"));
        tipeColumn.setCellValueFactory(new PropertyValueFactory<>("tipe"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        loadUsers();
        btnRecharge.setOnAction(event -> handleRecharge());
    }

    private void loadUsers() {
        userList = FXCollections.observableArrayList();
        LinkedList<User> users = UserData.getUsers();
        for (User user : users) {
            if ("Estudiante".equals(user.getTipe())) {
                userList.add(user);
            }
        }
        userTable.setItems(userList);
    }

    @FXML
    private void handleDelete() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Estás seguro de que deseas eliminar este usuario?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                UserData.deleteUser(selectedUser.getId_tbuser());
                loadUsers(); // Recargar la lista de usuarios
            }
        }
    }

    @FXML
    private void handleUpdate() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIRegisterUsers.fxml"));
            Parent root = loader.load();
            UIRegisterUsersController controller = loader.getController();
            controller.setServerConnection(serverConnection);
            controller.loadUserData(selectedUser);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnCloseRequest(e -> controller.closeWindows());
            ((Stage) btnUpdate.getScene().getWindow()).close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRecharge() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIRecharge.fxml"));
            Parent root = loader.load();
            UIRechargeController controller = loader.getController();
            controller.setServerConnection(serverConnection);
            controller.recoveredData(selectedUser);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnCloseRequest(e -> controller.closeWindows());
            ((Stage) btnRecharge.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void returnMain(ActionEvent event) {
        closeWindows();
    }

    @FXML
    private void closeWindows() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIProfile.fxml"));
            Parent root = loader.load();
            UIProfileController profileController = loader.getController();
            profileController.setServerConnection(serverConnection);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Stage) btnBack.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public void setServerConnection(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }
}