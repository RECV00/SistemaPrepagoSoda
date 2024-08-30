package presentation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;

public class UIStartController {
	@FXML
	private Button bCheckBalance;
	@FXML
	private Button bRequestBalance;
	@FXML
	private Label lTitule;

	// Event Listener on Button[#bCheckBalance].onAction
	@FXML
	public void UICheckBalance(ActionEvent event) {
//		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UI.fxml"));
//			Parent root = loader.load();
//			UIController controller = loader.getController();
//			controller.initialize();
//			Scene scene = new Scene(root);
//			Stage stage = new Stage();
//			stage.setScene(scene);
//			stage.show();
//			
//			stage.setOnCloseRequest(e -> controller.closeWindows());
//			Stage temp = (Stage) this.bRequestBalance.getScene().getWindow();
//			temp.close();
//			
//		}catch(IOException e){
//			
//		}
	}
	// Event Listener on Button[#bRequestBalance].onAction
	@FXML
	public void UIRequestBalance(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIServiceRequest.fxml"));
			Parent root = loader.load();
			UIServiceRequestController controller = loader.getController();
			controller.initialize();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			
			stage.setOnCloseRequest(e -> controller.closeWindows());
			Stage temp = (Stage) this.bRequestBalance.getScene().getWindow();
			temp.close();
			
		}catch(IOException e){
			
		}
	}
}
