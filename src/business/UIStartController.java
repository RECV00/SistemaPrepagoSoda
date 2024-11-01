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
	private Button btnShowBinnacle;
	@FXML
	private Label lTitule;
	@FXML
	private Button btnBack;

	// Event Listener on Button[#bCheckBalance].onAction
	@FXML
	public void UICheckBalance(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UICheckBalance.fxml"));
			Parent root = loader.load();
			UICheckBalanceController controller = loader.getController();
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
	public void showBinnacle(ActionEvent event) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIBinnacle.fxml"));
			Parent root = loader.load();
			UIBinnacleController controller = loader.getController();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();	
			stage.setOnCloseRequest(e -> controller.closeWindows());
			Stage temp = (Stage) this.btnShowBinnacle.getScene().getWindow();
			temp.close();
			
		}catch(IOException e){
			
		}
	}
	
	// Event Listener on Button[#bBackStudent].onAction
		@FXML
		public void returnProfile(ActionEvent event) {
			closeWindows();
		}
		
		@FXML
		public void closeWindows() {
			
			try {
				 FXMLLoader loader = new FXMLLoader (getClass().getResource("/presentation/UIProfile.fxml"));
		        Parent root = loader.load();
				Scene scene = new Scene(root);
		        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
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
