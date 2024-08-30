module SistemaPrepagoSoda {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.datatype.jsr310;
	requires java.desktop;
	
	opens business to javafx.graphics, javafx.fxml;
}
