module SistemaPrepagoSoda {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	
	opens business to javafx.graphics, javafx.fxml;
}
