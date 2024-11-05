package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;
import data.PasswordHasher;
import data.ServerConnection;
import data.UserData;
import domain.User;

import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JOptionPane;

public class UILoginController {

    @FXML
    private Button btnRegister;
    @FXML
    private Button btnLogin;
    @FXML
    private TextField tfUserID;
    @FXML
    private PasswordField tfPassword;

    private ServerConnection serverConnection;
 
    @FXML
    public void initialize() {
        new ServerConnection();
		// Iniciar el servidor aquí
        serverConnection = ServerConnection.getInstance();
       serverConnection.startServer(); // Inicia el servidor al iniciar el controlador
    System.out.println("Servidor Iniciado Desde Controlador de Inicio de Sesión.");
    }

    @FXML
    public void loginRegister(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIRegisterUsers.fxml"));
            Parent root = loader.load();
            UIRegisterUsersController controller = loader.getController();
            controller.setServerConnection(serverConnection); 
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            Stage temp = (Stage) this.btnLogin.getScene().getWindow();
            temp.close();

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al abrir la ventana de registro de Usuarios.");
        }
    }

    @FXML
    public void loginAdmin(ActionEvent event) {
        String userID = tfUserID.getText();
        String password = tfPassword.getText();

        if (userID.isEmpty() || password.isEmpty()) {
            showAlert("Por favor, complete todos los campos.");
            return;
        }

        LinkedList<User> users = UserData.getUsers(); // Obtener la lista de usuarios
        boolean loginSuccessful = false;

        // Iterar sobre los usuarios para verificar las credenciales
        for (User user : users) {
            if (user.getId() == Integer.parseInt(userID) && user.getTipe().equals("Personal")) {
                // Verificar la contraseña usando el método verifyPassword
                if (PasswordHasher.verifyPassword(password, user.getPassword(), user.getSalt())) {
                    loginSuccessful = true; // Autenticación exitosa
                    break;
                }
            }
        }

        if (loginSuccessful) {
            showAlert("Inicio de sesión exitoso.");
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIProfile.fxml"));
                Parent root = loader.load();
                UIProfileController controller = loader.getController();
                controller.loadUserProfile(userID); // Cargar el perfil del usuario
                controller.initialize(userID);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

                Stage temp = (Stage) this.btnLogin.getScene().getWindow();
                temp.close(); // Cerrar la ventana de login

            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al abrir la ventana de perfil.");
            }
        } else {
            showAlert("Usuario o contraseña incorrectos o no tiene acceso.");
        }
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void stopServer() {
        if (serverConnection != null) {
            serverConnection.stopServer(); // Detener el servidor cuando se cierre la aplicación
        }
    }

	 public void setServerConnection(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }
    
}