module SistemaPrepagoSoda {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.datatype.jsr310;
	requires java.desktop;
	requires javafx.base;
	requires java.sql;
	
	exports domain;
	exports business;
	opens business to javafx.graphics, javafx.fxml;
}
