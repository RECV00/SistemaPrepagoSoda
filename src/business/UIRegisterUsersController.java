package business;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import data.UserData;
import domain.User;

public class UIRegisterUsersController {

    @FXML
    private TextField tfUserID;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private ComboBox<String> cbType;

    @FXML
    private ImageView photoPreview;

    @FXML
    private Button btnSelectPhoto;

    private File selectedPhotoFile;

    @FXML
    public void initialize() {
        // Cargar opciones para el ComboBox
        cbType.getItems().addAll("Personal", "Estudiante");
    }

    @FXML
    private void handleSelectPhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Foto de Perfil");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Imagenes", "*.png", "*.jpg", "*.jpeg")
        );
        selectedPhotoFile = fileChooser.showOpenDialog(new Stage());
        
        if (selectedPhotoFile != null) {
            Image image = new Image(selectedPhotoFile.toURI().toString());
            photoPreview.setImage(image);
        }
    }

    @FXML
    private void handleRegister() {
        // Validar campos de entrada
        String userID = tfUserID.getText();
        String password = tfPassword.getText();
        String userType = cbType.getValue();

        if (userID.isEmpty() || password.isEmpty() || userType == null || selectedPhotoFile == null) {
            showAlert("Error", "Todos los campos son obligatorios.");
            return;
        }

        try {
            // Guardar la imagen con el ID del usuario en la carpeta "media"
            String photoFileName = "media/" + userID + ".png";
            File destFile = new File(photoFileName);
            Files.copy(selectedPhotoFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Crear el usuario y guardarlo en la base de datos
            User user = new User(Integer.parseInt(userID), password, userType, photoFileName);
            UserData.saveUser(user);

            showAlert("Éxito", "Usuario registrado correctamente.");
            clearFields();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Ocurrió un error al guardar la imagen.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Error al registrar el usuario.");
        }
    }

    private void showAlert(String title, String message) {
        // Mostrar una alerta en un diálogo (implementación personalizada o uso de Alert de JavaFX)
        System.out.println(title + ": " + message);
    }

    private void clearFields() {
        tfUserID.clear();
        tfPassword.clear();
        cbType.setValue(null);
        photoPreview.setImage(null);
        selectedPhotoFile = null;
    }
}