package business;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import domain.Order;

import javafx.scene.image.Image;
import java.util.LinkedList;

import data.UserData;

public class UISaleController {

    // Barra Superior
    @FXML
    private ImageView userProfileImage;
    @FXML
    private Label userNameLabel;

    // Panel de Productos
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private ComboBox<String> priceComboBox;
    @FXML
    private TextField searchField;
    @FXML
    private FlowPane productsFlowPane;

    // Panel del Carrito de Compras
    @FXML
    private TableView<Order> cartTableView; // Cambiado a Order para reflejar la tabla del carrito
    @FXML
    private TableColumn<Order, String> descriptionColumn; // Cambiado para reflejar los tipos de datos
    @FXML
    private TableColumn<Order, Integer> quantityColumn;
    @FXML
    private TableColumn<Order, Double> priceColumn;
    @FXML
    private TableColumn<Order, Double> totalColumn;
    @FXML
    private TextField discountField;
    @FXML
    private Label totalLabel;

    private ObservableList<Order> cartItems; // Lista observable para los elementos del carrito

    // Métodos de inicialización y lógica
    @FXML
    public void initialize() {
        // Este método se llama automáticamente después de que se carga el archivo FXML
        // Puedes usarlo para inicializar los valores de los ComboBox, cargar productos, etc.
        initializeComboBoxes();
        loadProducts();
        initializeCart();
    }

    private void initializeComboBoxes() {
        // Agregar categorías y precios de ejemplo
        categoryComboBox.getItems().addAll("Bebidas", "Comidas Rápidas", "Postres");
        priceComboBox.getItems().addAll("Menor a $50", "$50 - $100", "Mayor a $100");
    }

    private void loadProducts() {
        // Crear y agregar botones de productos a productsFlowPane como ejemplo
        Button product1 = createProductButton("Sopita Dona Gallina");
        Button product2 = createProductButton("Galleta de Chocolate");
        Button product3 = createProductButton("Jugo de Naranja");

        productsFlowPane.getChildren().addAll(product1, product2, product3);
    }

    private Button createProductButton(String productName) {
        Button button = new Button(productName);
        button.setPrefSize(100, 100);
        button.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white;");
        button.setOnAction(event -> addToCart(productName));
        return button;
    }

    private void initializeCart() {
        cartItems = FXCollections.observableArrayList(); // Inicializa la lista observable
        cartTableView.setItems(cartItems); // Asocia la lista a la tabla
    }

    private void addToCart(String productName) {
        // Lógica para agregar el producto al carrito de compras
        // Aquí podrías manejar la cantidad, calcular el total, etc.
        System.out.println("Producto agregado al carrito: " + productName);

        // Simulando la adición de un pedido al carrito
        Order order = new Order(productName, 1, 100.0, 'P', "1"); // Reemplaza con datos reales
        cartItems.add(order); // Agrega el pedido a la lista observable
    }

  
    // Método para actualizar el historial de órdenes
    public void updateOrderHistory(LinkedList<Order> orders) {
        // Actualiza la tabla o el historial con las órdenes procesadas
        for (Order order : orders) {
            cartItems.add(order); // Agrega cada orden a la lista del carrito
        }
        System.out.println("Historial de órdenes actualizado.");
    }

    @FXML
    private void applyDiscount() {
        // Lógica para aplicar el descuento basado en el valor del campo discountField
        String discountText = discountField.getText();
        System.out.println("Aplicando descuento: " + discountText);
    }

    @FXML
    private void calculateTotal() {
        // Lógica para calcular el total del carrito de compras
        double total = cartItems.stream().mapToDouble(Order::getTotal).sum(); // Sumar los totales de las órdenes
        totalLabel.setText("RD$" + total); // Actualiza el total en el label
    }
    public void userLoggedIn(String userId) {
        userNameLabel.setText("Usuario: " + userId); // Actualiza la etiqueta con el ID del usuario
        System.out.println("Usuario ha iniciado sesión: " + userId);
        
        //loadUserProfile(userId); // Cargar la foto de perfil del usuario
    }
    public void loadUserProfile(String userId) {
        // Obtener la ruta de la foto del usuario usando el ID de usuario
        String photoPath = UserData.getPhotoLinkByCedula(Integer.parseInt(userId)); 

        if (photoPath != null && !photoPath.isEmpty()) {
            // Cargar la imagen en el ImageView
            Image userImage = new Image("file:" + photoPath); // Asegúrate de que la ruta sea correcta
            userProfileImage.setImage(userImage); // Establecer la imagen en el ImageView
        } else {
            System.out.println("No se encontró la foto del usuario.");
            userProfileImage.setImage(null); // Limpiar el ImageView si no hay imagen
        }
    }

	public Object closeWindows() {
		// TODO Auto-generated method stub
		return null;
	}
}