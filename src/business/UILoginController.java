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
            JOptionPane.showMessageDialog(null, "Error al abrir la ventana de registro de platillos.");
        }
	}
	
	@FXML
	public void loginAdmin(ActionEvent event) {
	    String userID = tfUserID.getText().trim();
	    String password = tfPassword.getText();
	
	    if (userID.isEmpty() || password.isEmpty()) {
	        showAlert("Por favor, complete todos los campos.");
	    return;
	}
	
	   
	// Verificar si el usuario existe en la base de datos
	LinkedList<User> users = UserData.getUsers();
	boolean loginSuccessful = false;
	
	for (User user : users) {
	    if (user.getId() == Integer.parseInt(userID) && user.getPassword().equals(password)) {
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
	            Scene scene = new Scene(root);
	            Stage stage = new Stage();
	            stage.setScene(scene);
	            stage.show();
	
//	            stage.setOnCloseRequest(e -> controller.closeWindows());
	            Stage temp = (Stage) this.btnLogin.getScene().getWindow();
	            temp.close();
	
	        } catch (IOException e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(null, "Error al abrir la ventana de registro de platillos.");
	        }
	    } else {
	    showAlert("Usuario o contraseña incorrectos.");
	    }
	}
	
	private void showAlert(String message) {
	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setHeaderText(null); // No mostrar encabezado
	        alert.setContentText(message);
	        alert.showAndWait();
	    }
}
