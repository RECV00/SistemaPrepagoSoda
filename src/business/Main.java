package business;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) {
        try {
            // Cargar el archivo FXML de UILogin
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UILogin.fxml"));
            Parent root = loader.load();
            // Configurar la escena y el escenario
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Sistema de Gestión de Usuarios"); // Título de la ventana
            primaryStage.setResizable(false); // No permitir que la ventana sea redimensionada
            // Mostrar la ventana
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args); // Iniciar la aplicación JavaFX
    }

}