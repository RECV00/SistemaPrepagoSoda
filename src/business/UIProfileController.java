package business;

import java.io.IOException;

import data.ServerConnection;
import data.UserData;
import domain.User;
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
    private Button btnPuntoDeVenta; // Botón para acceder al punto de venta
    @FXML
    private Button btnInventario; // Botón para acceder al inventario
    @FXML
    private Button btnRegisterStudent; // Botón para gestionar estudiantes
    @FXML
    private Button btnUsuarios; // Botón para gestionar usuarios
    @FXML
    private Button btnConfiguracion; // Botón para la configuración

    private ServerConnection servidor;

    @FXML
    public void initialize() {
        servidor = new ServerConnection(); // Inicializa el servidor
        new Thread(() -> servidor.startServer()).start(); // Inicia el servidor en un nuevo hilo

        // Inicializar cualquier lógica necesaria al cargar la vista
        // welcomeLabel.setText("Bienvenido al sistema de prepago soda");

        // Puedes agregar listeners para los botones si es necesario
        btnPuntoDeVenta.setOnAction(event -> openPointOfSale());
        btnInventario.setOnAction(event -> openInventory());
        btnRegisterStudent.setOnAction(event -> openRegisterStudent());
        btnUsuarios.setOnAction(event -> openUsers());
        btnConfiguracion.setOnAction(event -> openConfiguration());
    
        // Cargar el perfil del usuario al iniciar
        loadUserProfile("userID");
    }
    public void loadUserProfile(String userId) {
        User currentUser = UserData.getUserById(userId); // Obtener el usuario desde la fuente de datos

        if (currentUser != null) {
         
            // Cargar la dirección de la foto del usuario
            String photoPath = currentUser.getPhotoRoute(); // Obtener la ruta de la foto
            if (photoPath != null && !photoPath.isEmpty()) {
                // Cargar la imagen en el ImageView
                Image userImage = new Image("file:" + photoPath); // Asegúrate de que la ruta sea correcta
                userIcon.setImage(userImage); // Establecer la imagen en el ImageView
            } else {
                System.out.println("No se encontró la foto del usuario.");
                userIcon.setImage(null); // Limpiar el ImageView si no hay imagen
            }
        } else {
            System.out.println("Error: Usuario no encontrado.");
            // Maneja el error, tal vez mostrando un mensaje en la UI
        }
    }
    private void openPointOfSale() {
        // Lógica para abrir la ventana de Punto de Venta
        System.out.println("Abriendo Punto de Venta...");
        // Aquí puedes cargar una nueva vista o hacer algo más
    }

    private void openInventory() {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIInventory.fxml"));
			Parent root = loader.load();
			UIInventoryController controller = loader.getController();
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

    private void openRegisterStudent() {
    		try {
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIStart.fxml"));
    			Parent root = loader.load();
    			UIStartController controller = loader.getController();
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
        // Lógica para abrir la ventana de Usuarios
        System.out.println("Abriendo Usuarios...");
        // Aquí puedes cargar una nueva vista o hacer algo más
    }

    private void openConfiguration() {
        // Lógica para abrir la ventana de Configuración
        System.out.println("Abriendo Configuración...");
        // Aquí puedes cargar una nueva vista o hacer algo más
    }
}