package business;

import java.io.IOException;

import data.ServerConnection;
import data.UserData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class UIProfileController {

    @FXML
    private Label welcomeLabel; // Etiqueta para mostrar un mensaje de bienvenida
    @FXML
    private ImageView userIcon; // Icono del usuario
    @FXML
    private Button btnPedidos; 
    @FXML 
    private Button btnBinnacle;
    @FXML
    private Button btnInventario; // Botón para acceder al inventario
    @FXML
    private Button btnRegisterStudent; // Botón para gestionar estudiantes
    @FXML
    private Button btnUsuarios; // Botón para gestionar usuarios
    @FXML
    private Button btnConfiguracion; // Botón para la configuración

    
    private ServerConnection serverConnection;


    public UIProfileController() {
        // Solo se inicia el servidor la primera vez       
            this.serverConnection = new ServerConnection();
            new Thread(() -> serverConnection.startServer()).start(); // Iniciar el servidor en un nuevo hilo
        
    }
//    @FXML
    public void initialize(String userId) {
    	// Cargar el perfil del usuario al iniciar
//        loadUserProfile(userId);
    	 System.out.println("CEDULAr:"+ userId);
        // Inicializar cualquier lógica necesaria al cargar la vista
        // welcomeLabel.setText("Bienvenido al sistema de prepago soda");

        // Puedes agregar listeners para los botones si es necesario
    	btnPedidos.setOnAction(event -> openPointOfSale(userId,"/presentation/UISale.fxml"));
        btnInventario.setOnAction(event -> openInventory("/presentation/UIViewDishes.fxml"));
        btnRegisterStudent.setOnAction(event -> openViewStudent());
        btnUsuarios.setOnAction(event -> openUsers());
        btnConfiguracion.setOnAction(event -> openConfiguration());
        btnBinnacle.setOnAction(event -> openBinnacle());
        
        
    }
    public void loadUserProfile(String userId) {
        // Obtener la ruta de la foto del usuario usando el ID de usuario
        String photoPath = "media/402480420.png"; 
        System.out.println("LINK:"+UserData.getPhotoLinkByCedula(Integer.parseInt(userId)));
        if (photoPath != null && !photoPath.isEmpty()) {
            // Cargar la imagen en el ImageView
        	Image userImage = new Image("file:"+photoPath);
 // Asegúrate de que la ruta sea correcta
            userIcon.setImage(userImage); // Establecer la imagen en el ImageView
        } else {
            System.out.println("No se encontró la foto del usuario.");
            userIcon.setImage(null); // Limpiar el ImageView si no hay imagen
        }

    }
    private void openPointOfSale(String userId,String fxmlPath) {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UISale.fxml"));
			Parent root = loader.load();
			
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			if(fxmlPath.equals("presentation/UISale.fxml")) {
				UISaleController controller = loader.getController();
				controller.loadUserProfile(userId);
				controller.userLoggedIn(userId);
				controller.setServerConnection(serverConnection);
			}
			
			Stage temp = (Stage) this.btnPedidos.getScene().getWindow();
			temp.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
    }

    private void openInventory(String fxmlPath) {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIViewDishes.fxml"));
			Parent root = loader.load();
		
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();	
			
			if(fxmlPath.equals("presentation/UIViewDishes.fxml")) {
				UIViewDishesController controller = loader.getController();
				controller.setServerConnection(serverConnection);
			}			
			Stage temp = (Stage) this.btnInventario.getScene().getWindow();
			temp.close();
			
		}catch(IOException e){
			
		}
    }

    private void openViewStudent() {
    		try {
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIViewStudent.fxml"));
    			Parent root = loader.load();
    			UIViewStudentController controller = loader.getController();
    			controller.setServerConnection(serverConnection);
    			Scene scene = new Scene(root);
    			Stage stage = new Stage();
    			stage.setScene(scene);
    			stage.show();	
    			stage.setOnCloseRequest(e -> controller.closeWindows());
    			Stage temp = (Stage) this.btnRegisterStudent.getScene().getWindow();
    			temp.close();
    			
    		}catch(IOException e){
    			
    		}
    	
    }

    
    private void openUsers() {
       	try {
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIViewAdmin.fxml"));
    			Parent root = loader.load();
    			UIViewAdminController controller = loader.getController();
    			controller.setServerConnection(serverConnection);
    			Scene scene = new Scene(root);
    			Stage stage = new Stage();
    			stage.setScene(scene);
    			stage.show();	
    			stage.setOnCloseRequest(e -> controller.closeWindows());
    			Stage temp = (Stage) this.btnUsuarios.getScene().getWindow();
    			temp.close();
    			
    		}catch(IOException e){
    			
    		}
    }
    private void openBinnacle() {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIBinnacle.fxml"));
			Parent root = loader.load();
			UIBinnacleController controller = loader.getController();
			controller.setServerConnection(serverConnection);
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();	
			stage.setOnCloseRequest(e -> controller.closeWindows());
			Stage temp = (Stage) this.btnBinnacle.getScene().getWindow();
			temp.close();
			
		}catch(IOException e){
			
		}
    }
    private void openConfiguration() {
        // Lógica para abrir la ventana de Configuración
        System.out.println("Abriendo Configuración...");
        // Aquí puedes cargar una nueva vista o hacer algo más
    }
}