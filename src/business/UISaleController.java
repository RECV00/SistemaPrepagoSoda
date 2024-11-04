package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.util.Callback;

import domain.Order;
import data.OrderData;
import data.UserData;

import java.io.IOException;
import java.util.LinkedList;

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
    private TableView<Order> cartTableView;
    @FXML
    private TableColumn<Order, String> descriptionColumn;
    @FXML
    private TableColumn<Order, Integer> quantityColumn;
    @FXML
    private TableColumn<Order, Double> totalColumn;
    @FXML
    private TableColumn<Order, String> stateColumn;
    @FXML
    private TableColumn<Order, String> studentIdColumn; // Columna de ID Estudiante
    @FXML
    private TextField discountField;
    @FXML
    private Label totalLabel;
    @FXML
    private Button bBack;
    
    private ObservableList<Order> cartItems;

    // Inicialización y lógica
    @FXML
    public void initialize() {
        initializeComboBoxes();
        initializeCart();
        loadOrdersFromDatabase();
    }

    private void initializeComboBoxes() {
        categoryComboBox.getItems().addAll("Bebidas", "Comidas Rápidas", "Postres");
        priceComboBox.getItems().addAll("Menor a $50", "$50 - $100", "Mayor a $100");
    }

    private void initializeCart() {
        cartItems = FXCollections.observableArrayList();
        cartTableView.setItems(cartItems);

        // Configurar columnas
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNameProduct()));
        quantityColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAmount()));
        totalColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTotal()));

        // Configurar columna de estado
        stateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getIsState())));
        stateColumn.setCellFactory(getStateCellFactory());

        // Configurar columna de ID Estudiante
        studentIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdStudent()));
    }

    private void loadOrdersFromDatabase() {
        LinkedList<Order> orders = OrderData.getOrders(); // Obtiene las órdenes de la base de datos
        cartItems.addAll(orders);
    }

    // Actualizar el historial de órdenes
    public void updateOrderHistory(LinkedList<Order> orders) {
        cartItems.clear();
        cartItems.addAll(orders);
        System.out.println("Historial de órdenes actualizado.");
    }

    @FXML
    private void calculateTotal() {
        double total = cartItems.stream().mapToDouble(Order::getTotal).sum();
        totalLabel.setText("RD$" + total);
    }

    public void userLoggedIn(String userId) {
        userNameLabel.setText("Usuario: " + userId);
        loadUserProfile(userId);
    }

    public void loadUserProfile(String userId) {
        String photoPath = UserData.getPhotoLinkByCedula(Integer.parseInt(userId));
        if (photoPath != null && !photoPath.isEmpty()) {
            Image userImage = new Image("file:" + photoPath);
            userProfileImage.setImage(userImage);
        } else {
            System.out.println("No se encontró la foto del usuario.");
            userProfileImage.setImage(null);
        }
    }

    // Configuración de la columna de estado con un ComboBox en cada celda para cambiar el estado
    private Callback<TableColumn<Order, String>, TableCell<Order, String>> getStateCellFactory() {
        return column -> new TableCell<>() {
            private final ComboBox<String> comboBox = new ComboBox<>();

            {
                comboBox.getItems().addAll("Pendiente", "Preparado", "Entregado");
                comboBox.setOnAction(event -> {
                    Order order = getTableView().getItems().get(getIndex());
                    char newState = comboBox.getValue().charAt(0);
                    updateOrderState(order, newState);
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    comboBox.setValue(getStateString(item.charAt(0)));
                    setGraphic(comboBox);
                }
            }
        };
    }

    private void updateOrderState(Order order, char newState) {
        OrderData.updateOrderState(order.getNameProduct(), order.getNameProduct(), newState);
        order.setIsState(newState); // Actualiza el estado localmente
        cartTableView.refresh(); // Refresca la tabla
    }

    private String getStateString(char state) {
        return switch (state) {
            case 'P' -> "Pendiente";
            case 'R' -> "Preparado";
            case 'E' -> "Entregado";
            default -> "Desconocido";
        };
    }

    @FXML
    public void returnProfile() {
        closeWindows();
    }

    public void closeWindows() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIProfile.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage temp = (Stage) bBack.getScene().getWindow();
            temp.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}