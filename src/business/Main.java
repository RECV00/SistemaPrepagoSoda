package business;
	
import data.ServerConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	private ServerConnection serverConnection;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			 // Iniciar el servidor aquí
            serverConnection = new ServerConnection();
            new Thread(() -> serverConnection.startServer()).start();
            // Cargar la vista de inicio de sesión
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UILogin.fxml"));
            Parent root = loader.load();
            UILoginController loginController = loader.getController();
            loginController.setServerConnection(serverConnection); // Pasar la conexión al controlador de inicio de sesión

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Inicio de Sesión");
            primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}