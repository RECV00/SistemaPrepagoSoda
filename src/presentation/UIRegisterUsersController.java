package presentation;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;

import javafx.scene.image.ImageView;

import javafx.scene.control.PasswordField;

import javafx.scene.control.DatePicker;

public class UIRegisterUsersController {
	@FXML
	private TextField tfUserID;
	@FXML
	private PasswordField tfPassword;
	@FXML
	private ComboBox cbType;
	@FXML
	private TextField tfName;
	@FXML
	private TextField tfEmail;
	@FXML
	private TextField tfPhone;
	@FXML
	private ComboBox cbActive;
	@FXML
	private ComboBox cbGender;
	@FXML
	private TextField tfMoneyAvailable;
	@FXML
	private ImageView photoPreview;
	@FXML
	private Button btnSelectPhoto;
	@FXML
	private Button btnRegister;
	@FXML
	private DatePicker dbDateEntry;
	@FXML
	private Button btnBack;

	// Event Listener on Button[#btnSelectPhoto].onAction
	@FXML
	public void handleSelectPhoto(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#btnRegister].onAction
	@FXML
	public void handleRegister(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#btnBack].onAction
	@FXML
	public void returnMain(ActionEvent event) {
		// TODO Autogenerated
	}
}
