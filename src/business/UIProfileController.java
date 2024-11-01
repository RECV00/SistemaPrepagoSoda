package business;

import data.ServerConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class UIProfileController {

    @FXML
    private Label welcomeLabel; // Etiqueta para mostrar un mensaje de bienvenida
    @FXML
    private ImageView userIcon; // Icono del usuario
    @FXML
    private Button btnPuntoDeVenta; // Botón para acceder al punto de venta
    @FXML
    private Button btnInventario; // Botón para acceder al inventario
    @FXML
    private Button btnRegisterStudent; // Botón para gestionar estudiantes
    @FXML
    private Button btnUsuarios; // Botón para gestionar usuarios
    @FXML
    private Button btnConfiguracion; // Botón para la configuración

    private ServerConnection servidor;

    @FXML
    public void initialize() {
        servidor = new ServerConnection(); // Inicializa el servidor
        new Thread(() -> servidor.startServer()).start(); // Inicia el servidor en un nuevo hilo

        // Inicializar cualquier lógica necesaria al cargar la vista
        // welcomeLabel.setText("Bienvenido al sistema de prepago soda");

        // Puedes agregar listeners para los botones si es necesario
        btnPuntoDeVenta.setOnAction(event -> openPointOfSale());
        btnInventario.setOnAction(event -> openInventory());
        btnRegisterStudent.setOnAction(event -> openRegisterStudent());
        btnUsuarios.setOnAction(event -> openUsers());
        btnConfiguracion.setOnAction(event -> openConfiguration());
    }

    private void openPointOfSale() {
        // Lógica para abrir la ventana de Punto de Venta
        System.out.println("Abriendo Punto de Venta...");
        // Aquí puedes cargar una nueva vista o hacer algo más
    }

    private void openInventory() {
        // Lógica para abrir la ventana de Inventario
        System.out.println("Abriendo Inventario...");
        // Aquí puedes cargar una nueva vista o hacer algo más
    }

    private void openRegisterStudent() {
        // Lógica para abrir la ventana de Registro de Estudiantes
        System.out.println("Abriendo Registro de Estudiantes...");
        // Aquí puedes cargar una nueva vista o hacer algo más
    }

    private void openUsers() {
        // Lógica para abrir la ventana de Usuarios
        System.out.println("Abriendo Usuarios...");
        // Aquí puedes cargar una nueva vista o hacer algo más
    }

    private void openConfiguration() {
        // Lógica para abrir la ventana de Configuración
        System.out.println("Abriendo Configuración...");
        // Aquí puedes cargar una nueva vista o hacer algo más
    }
}