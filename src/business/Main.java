package business;

import data.ServerConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {

    private static ServerConnection serverConnection; // Instancia única del servidor

    @Override
    public void start(Stage primaryStage) {
        try {
            // Iniciar el servidor solo una vez al inicio de la aplicación
            serverConnection = new ServerConnection();
            serverConnection.startServer();

            // Cargar la vista de inicio de sesión
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UILogin.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Inicio de Sesión");
            primaryStage.show();            
            // Configuración para detener el servidor al cerrar la ventana
            primaryStage.setOnCloseRequest(event -> stopServer());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        stopServer(); // Asegurarse de detener el servidor al salir de la aplicación
    }

    private void stopServer() {
        if (serverConnection != null) {
            serverConnection.stopServer();
        }
    }

    public static void main(String[] args) {
        launch(args); // Iniciar la aplicación JavaFX
    }
}