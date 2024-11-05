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

import data.ServerConnection;
import data.UserData;
import domain.User;
import data.PasswordHasher;

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
    private boolean isEditing = false; // Indicador de modo de edición
    private User currentUser; // Usuario actual en modo de edición
    private int tbuser;
    
    private ServerConnection serverConnection;

    public void setServerConnection(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }
    
    // Initialize method to set up ComboBoxes
    @FXML
    public void initialize() {
        cbType.getItems().addAll("Estudiante","Personal");
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
        boolean isActive = "Sí".equals(cbActive.getSelectionModel().getSelectedItem());
        LocalDate dateEntry = dbDateEntry.getValue();
        boolean gender = "Femenino".equals(cbGender.getSelectionModel().getSelectedItem());
        double moneyAvailable = tfMoneyAvailable.isDisabled() ? 0.0 : Double.parseDouble(tfMoneyAvailable.getText());

        // Generar un salt aleatorio
        String salt = PasswordHasher.generateSalt();

        // Cifrar la contraseña con el salt
        String encryptedPassword = PasswordHasher.hashPasswordWithSalt(password, salt);

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

        // Crear el objeto User con la contraseña cifrada
        User newUser;
        if (isEditing) {
            newUser = new User(tbuser, userID, encryptedPassword,salt, userType, photoFileName, name, email, phone, isActive, dateEntry, gender, moneyAvailable);
            UserData.updateUser(newUser); // Actualizar usuario
        } else {
            newUser = new User(userID, encryptedPassword,salt, userType, photoFileName, name, email, phone, isActive, dateEntry, gender, moneyAvailable);
            UserData.saveUser(newUser); // Registrar nuevo usuario
        }

        // Limpiar campos después de la operación
        clearFields();
    }



    public void loadUserData(User user) {
        currentUser = user;
        tfUserID.setText(String.valueOf(user.getId()));
        tfPassword.setText(user.getPassword());
        cbType.setValue(user.getTipe());
        tfName.setText(user.getName());
        tfEmail.setText(user.getEmail());
        tfPhone.setText(String.valueOf(user.getPhone()));
        cbActive.setValue(user.getIsActive() ? "Sí" : "No");
        dbDateEntry.setValue(user.getDateEntry());
        cbGender.setValue(String.valueOf(user.getGender()? "Femenino" : "Maculino" ));
        tfMoneyAvailable.setText(String.valueOf(user.getMoneyAvailable()));

        tfUserID.setDisable(true);
        tfMoneyAvailable.setDisable(true);
        tfPassword.setDisable(true);
        // Cargar imagen
        File photoFile = new File(user.getPhotoRoute());
        if (photoFile.exists()) {
            Image image = new Image(photoFile.toURI().toString());
            photoPreview.setImage(image);
        }

        isEditing = true; // Establecer que estamos en modo edición
        tbuser = user.getId_tbuser();
        System.out.println("ID al cargar: " + user.getId_tbuser()); // Al cargar
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
        isEditing = false; // Restablecer a modo de registro
    }
    
    @FXML
    public void closeWindows() {
    	 if (isEditing && "Estudiante".equals(cbType.getValue())) {
    		  try {
    	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIViewStudent.fxml"));
    	            Parent root = loader.load();
    	            UIViewStudentController controller = loader.getController();
    				controller.setServerConnection(serverConnection);
    	            Scene scene = new Scene(root);
    	            Stage stage = new Stage();
    	            stage.setScene(scene);
    	            stage.show();			        
    	            Stage temp = (Stage) btnBack.getScene().getWindow();
    	            temp.close();
    	        } catch (IOException e) {
    	            e.printStackTrace();
    	        }
    		  } else if (isEditing && "Personal".equals(cbType.getValue())) {
	        		  try {
	      	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIViewAdmin.fxml"));
	      	            Parent root = loader.load();
	      	            UIViewAdminController controller = loader.getController();
	      				controller.setServerConnection(serverConnection);
	      	            Scene scene = new Scene(root);
	      	            Stage stage = new Stage();
	      	            stage.setScene(scene);
	      	            stage.show();			        
	      	            Stage temp = (Stage) btnBack.getScene().getWindow();
	      	            temp.close();
	      	        } catch (IOException e) {
	      	            e.printStackTrace();
	      	        } 
    		  }else {
	    			  try {
	                  FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UILogin.fxml"));
	                  Parent root = loader.load();
	                  UILoginController controller = loader.getController();
	      			controller.setServerConnection(serverConnection);
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

	
}
