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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;

public class UIViewAdminController {

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
    private Button btnBack;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    

    private ObservableList<User> userList;
    
    private ServerConnection serverConnection;
	 
	 public void setServerConnection(ServerConnection serverConnection) {
			this.serverConnection = serverConnection;
			
		}

    @FXML
    public void initialize() {
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
                if (empty || gender == null) {
                    setText(null);
                } else {
                    setText(gender ? "Femenino" : "Masculino");
                }
            }
        });

        // Personalizar columna de estado activo
        isActiveColumn.setCellValueFactory(new PropertyValueFactory<>("isActive"));
        isActiveColumn.setCellFactory(column -> new TableCell<User, Boolean>() {
      
            protected void updateItem(Boolean isActive, boolean empty) {
                super.updateItem(isActive, empty);
                if (empty || isActive == null) {
                    setText(null);
                } else {
                    setText(isActive ? "Activo" : "Inactivo");
                }
            }
        });

        moneyAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("moneyAvailable"));
        dateEntryColumn.setCellValueFactory(new PropertyValueFactory<>("dateEntry"));
        photoColumn.setCellValueFactory(new PropertyValueFactory<>("photoRoute"));
        tipeColumn.setCellValueFactory(new PropertyValueFactory<>("tipe"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        loadUsers();
    }


    private void loadUsers() {
        userList = FXCollections.observableArrayList();
        LinkedList<User> users = UserData.getUsers();
        for (User user : users) {
            if ("Personal".equals(user.getTipe())) {
                userList.add(user);
            }
        }
        userTable.setItems(userList);
    }

    @FXML
    private void handleDelete() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            UserData.deleteUser(selectedUser.getId_tbuser());
            userList.remove(selectedUser);
        }
    }

    @FXML
    private void handleUpdate() {
    	User selectedUser = userTable.getSelectionModel().getSelectedItem();

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIRegisterUsers.fxml"));
			Parent root = loader.load();
			UIRegisterUsersController controller = loader.getController();
			controller.loadUserData(selectedUser); 
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();	
			stage.setOnCloseRequest(e -> controller.closeWindows());
			Stage temp = (Stage) this.btnUpdate.getScene().getWindow();
			temp.close();
			
		}catch(IOException e){
			
		}
    }
    
    @FXML
    public void returnMain(ActionEvent event) {
        // Lógica para regresar al inicio
    	closeWindows();
    }
//--------------------------------------------------------------------------------------------------- 
    @FXML
	public void closeWindows() {
		
		try {
			 FXMLLoader loader = new FXMLLoader (getClass().getResource("/presentation/UIProfile.fxml"));
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

