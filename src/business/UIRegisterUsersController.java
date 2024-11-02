package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

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
    private TextField tfName;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfPhone;
    @FXML
    private ComboBox<String> cbActive;
    @FXML
    private DatePicker dbDateEntry;
    @FXML
    private ComboBox<String> cbGender;
    @FXML
    private TextField tfMoneyAvailable;
    @FXML
    private ImageView photoPreview;
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnSelectPhoto;

    private File selectedPhotoFile;

    // Initialize method to set up ComboBoxes
    @FXML
    public void initialize() {
        cbType.getItems().addAll("Personal", "Estudiante");
        cbType.getSelectionModel().selectFirst();
        cbActive.getItems().addAll("Sí", "No");
        cbActive.getSelectionModel().selectFirst();
        cbGender.getItems().addAll("Masculino", "Femenino");
        cbGender.getSelectionModel().selectFirst();

        // Listener para tipo seleccionado
        cbType.valueProperty().addListener((obs, oldVal, newVal) -> {
            if ("Personal".equals(newVal)) {
                tfMoneyAvailable.setDisable(true);
                tfMoneyAvailable.clear(); // Limpia el campo si se selecciona Personal
            } else {
                tfMoneyAvailable.setDisable(false);
            }
        });
    }

    @FXML
    private void handleRegister() {
        // Obtener datos de entrada
        int userID = Integer.parseInt(tfUserID.getText());
        String password = tfPassword.getText();
        String userType = cbType.getValue();
        String name = tfName.getText();
        String email = tfEmail.getText();
        int phone = Integer.parseInt(tfPhone.getText());
        boolean isActive = "Sí".equals(cbActive.getValue());
        LocalDate dateEntry = dbDateEntry.getValue();
        boolean gender = "Masculino".equals(cbGender.getValue());
        double moneyAvailable = tfMoneyAvailable.isDisabled() ? 0.0 : Double.parseDouble(tfMoneyAvailable.getText()); // Establecer a 0.0 si está deshabilitado

        // Guardar la imagen con el ID del usuario en la carpeta "media"
        String photoFileName = "media/" + userID + ".png";
        File destFile = new File(photoFileName);
        
        try {
            if (selectedPhotoFile != null) {
                Files.copy(selectedPhotoFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Manejar error al copiar la imagen
        }

        // Crear el objeto User
        User newUser = new User(userID, password, userType, photoFileName, name, email, phone, isActive, dateEntry, gender, moneyAvailable);
        
        // Guardar el usuario en la base de datos
        UserData.saveUser(newUser);

        // Limpiar campos después del registro
        clearFields();
    }

    @FXML
    private void handleSelectPhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Foto de Perfil");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Imagenes", ".png", ".jpg", "*.jpeg")
        );
        selectedPhotoFile = fileChooser.showOpenDialog(new Stage());
        
        if (selectedPhotoFile != null) {
            Image image = new Image(selectedPhotoFile.toURI().toString());
            photoPreview.setImage(image);
        }
    }

    @FXML
    private void returnMain() {
    	closeWindows();
    }

    private void clearFields() {
        tfUserID.clear();
        tfPassword.clear();
        tfName.clear();
        tfEmail.clear();
        tfPhone.clear();
        cbType.setValue(null);
        cbActive.setValue(null);
        dbDateEntry.setValue(null);
        cbGender.setValue(null);
        tfMoneyAvailable.clear();
        photoPreview.setImage(null); // Limpiar la imagen
    }
    
    @FXML
    public void closeWindows() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UILogin.fxml"));
            Parent root = loader.load();
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
}
