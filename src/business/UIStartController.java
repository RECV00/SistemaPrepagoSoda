package business;

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
	private Button bRequestService;
	@FXML
	private Label lTitule;

	// Event Listener on Button[#bCheckBalance].onAction
	@FXML
	public void UICheckBalance(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UICheckBalance.fxml"));
			Parent root = loader.load();
			UICheckBalanceController controller = loader.getController();
			controller.initialize();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			
			stage.setOnCloseRequest(e -> controller.closeWindows());
			Stage temp = (Stage) this.bCheckBalance.getScene().getWindow();
			temp.close();
			
		}catch(IOException e){
			
		}
	}
	
	@FXML
	public void UIRequestService(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIServiceRequest.fxml"));
			Parent root = loader.load();
			UIServiceRequestController controller = loader.getController();
			//controller.initialize();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();	
			stage.setOnCloseRequest(e -> controller.closeWindows());
			Stage temp = (Stage) this.bRequestService.getScene().getWindow();
			temp.close();
			
		}catch(IOException e){
			
		}
	}
}
