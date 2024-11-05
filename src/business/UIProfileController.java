package business;

import data.ServerConnection;
import data.UserData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

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
    private Button btnInventario;
    @FXML
    private Button btnRegisterStudent;
    @FXML
    private Button btnUsuarios;
    @FXML
    private Button btnConfiguracion;

    private ServerConnection serverConnection;

    // Inicialización y obtención de la conexión del servidor
    public void initialize(String userId) {
        serverConnection = ServerConnection.getInstance();
        
        System.out.println("User ID: " + userId);
        loadUserProfile(userId);

        btnPedidos.setOnAction(event -> openPointOfSale(userId, "/presentation/UISale.fxml"));
        btnInventario.setOnAction(event -> openInventory("/presentation/UIViewDishes.fxml"));
        btnRegisterStudent.setOnAction(event -> openViewStudent());
        btnUsuarios.setOnAction(event -> openUsers());
        btnConfiguracion.setOnAction(event -> openConfiguration());
        btnBinnacle.setOnAction(event -> openBinnacle());
    }

    public void loadUserProfile(String userId) {
        String photoPath = "media/402480420.png"; // Esta línea puede ser ajustada según sea necesario
        System.out.println("LINK:" + UserData.getPhotoLinkByCedula(Integer.parseInt(userId)));
        if (photoPath != null && !photoPath.isEmpty()) {
            Image userImage = new Image("file:" + photoPath);
            userIcon.setImage(userImage);
        } else {
            System.out.println("No se encontró la foto del usuario.");
            userIcon.setImage(null);
        }
    }

    private void openPointOfSale(String userId, String fxmlPath) {
        openWindow(fxmlPath, loader -> {
        	UISaleController controller = loader.getController();
            controller.setServerConnection(serverConnection);
            
        });
    }

    private void openInventory(String fxmlPath) {
        openWindow(fxmlPath, loader -> {
            UIViewDishesController controller = loader.getController();
            controller.setServerConnection(serverConnection);
        });
    }

    private void openViewStudent() {
        openWindow("/presentation/UIViewStudent.fxml", loader -> {
            UIViewStudentController controller = loader.getController();
            controller.setServerConnection(serverConnection);
        });
    }

    private void openUsers() {
        openWindow("/presentation/UIViewAdmin.fxml", loader -> {
            UIViewAdminController controller = loader.getController();
            controller.setServerConnection(serverConnection);
        });
    }

    private void openBinnacle() {
        openWindow("/presentation/UIBinnacle.fxml", loader -> {
            UIBinnacleController controller = loader.getController();
            controller.setServerConnection(serverConnection);
        });
    }

    private void openConfiguration() {
        System.out.println("Abriendo Configuración...");
        // Implementa la lógica de configuración aquí
    }

    // Método auxiliar para cargar ventanas
    private void openWindow(String fxmlPath, FXMLLoaderConsumer loaderConsumer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            loaderConsumer.accept(loader); // Ejecuta la lógica del controlador

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            // Oculte la ventana actual en lugar de cerrarla
            Stage currentStage = (Stage) btnPedidos.getScene().getWindow();
            currentStage.hide();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    interface FXMLLoaderConsumer {
        void accept(FXMLLoader loader) throws IOException;
    }
    
    public void setServerConnection(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }
}