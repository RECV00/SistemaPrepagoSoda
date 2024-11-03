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

    @FXML
    public void initialize() {

	}
	
	@FXML
	public void loginRegister(ActionEvent event) {
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIRegisterUsers.fxml"));
            Parent root = loader.load();
            UIRegisterUsersController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

//            stage.setOnCloseRequest(e -> controller.closeWindows());
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

	    // Verificar si el usuario existe en la base de datos
	    LinkedList<User> users = UserData.getUsers();
	    boolean loginSuccessful = false;

	    for (User user : users) {
	        // Asegúrate de que el ID se está comparando correctamente
	        // Usamos equals() para comparar objetos de tipo Integer
	        if (user.getId() == Integer.parseInt(userID)
	                && user.getPassword().equals(password)
	                && user.getTipe().equals("Personal")) { // Verificar que el tipo sea "personal"
	            loginSuccessful = true;
	            break;
	        }
	    }

	    if (loginSuccessful) {
	        showAlert("Inicio de sesión exitoso.");
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIProfile.fxml"));
	            Parent root = loader.load();
	            UIProfileController controller = loader.getController();
	            //controller.loadUserProfile(userID);
	            Scene scene = new Scene(root);
	            Stage stage = new Stage();
	            stage.setScene(scene);
	            stage.show();

	            // Cerrar la ventana de inicio de sesión
	            Stage temp = (Stage) this.btnLogin.getScene().getWindow();
	            temp.close();

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
	    alert.setHeaderText(null); // No mostrar encabezado
	        alert.setContentText(message);
	        alert.showAndWait();
	    }
}
