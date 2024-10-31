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
    private TableView<?> cartTableView;
    @FXML
    private TableColumn<?, ?> descriptionColumn;
    @FXML
    private TableColumn<?, ?> quantityColumn;
    @FXML
    private TableColumn<?, ?> priceColumn;
    @FXML
    private TableColumn<?, ?> totalColumn;
    @FXML
    private TextField discountField;
    @FXML
    private Label totalLabel;

    // Métodos de inicialización y lógica
    @FXML
    public void initialize() {
        // Este método se llama automáticamente después de que se carga el archivo FXML
        // Puedes usarlo para inicializar los valores de los ComboBox, cargar productos, etc.
        initializeComboBoxes();
        loadProducts();
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

    private void addToCart(String productName) {
        // Lógica para agregar el producto al carrito de compras
        // Aquí podrías manejar la cantidad, calcular el total, etc.
        System.out.println("Producto agregado al carrito: " + productName);
    }

    // Otros métodos para manejar eventos y actualizar la interfaz
    @FXML
    private void applyDiscount() {
        // Lógica para aplicar el descuento basado en el valor del campo discountField
        String discountText = discountField.getText();
        System.out.println("Aplicando descuento: " + discountText);
    }

    @FXML
    private void calculateTotal() {
        // Lógica para calcular el total del carrito de compras
        totalLabel.setText("RD$1,500"); // Actualiza el total en el label
    }
}