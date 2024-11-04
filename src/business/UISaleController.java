package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
import data.ClientHandler;
import data.OrderData;
import data.ServerConnection;
import data.UserData;

import java.io.IOException;
import java.util.LinkedList;

public class UISaleController {

    // Barra Superior
    @FXML
    private ImageView userProfileImage;
    @FXML
    private Label userNameLabel;

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
    private TableColumn<Order, String> chanceStateColumn;
    @FXML
    private Label totalLabel;
    @FXML
    private Button bBack;
    
    private ObservableList<Order> cartItems;
    
    private ServerConnection serverConnection;
    
    private  ClientHandler clientHandler;
    
    public void setServerConnection(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }
    // Inicialización y lógica
    @FXML
    public void initialize() {
        initializeCart();
        loadOrdersFromDatabase();
    }

    private void initializeCart() {
    	 cartItems = FXCollections.observableArrayList();
    	    cartTableView.setItems(cartItems);

    	    // Configurar columnas existentes
    	    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("nameProduct"));
    	    quantityColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    	    totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
    	    stateColumn.setCellValueFactory(new PropertyValueFactory<>("isState"));
    	    studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("idStudent"));

    	    // Configurar columna `chanceStateColumn` para cambiar el estado
    	    chanceStateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(getStateString(cellData.getValue().getIsState())));

    	    chanceStateColumn.setCellFactory(column -> new TableCell<>() {
    	        private final ComboBox<String> comboBox = new ComboBox<>();

    	        {
    	            comboBox.getItems().addAll("Pendiente", "En preparación", "Listo", "Entregado");
    	            comboBox.setOnAction(event -> {
    	                Order order = getTableView().getItems().get(getIndex());
    	                String selectedState = comboBox.getValue();
    	                char newState = getStateChar(selectedState);
    	                updateOrderState(order, newState);
    	            });
    	        }

    	        @Override
    	        protected void updateItem(String item, boolean empty) {
    	            super.updateItem(item, empty);
    	            if (empty || item == null) {
    	                setGraphic(null);
    	            } else {
    	                comboBox.setValue(item);
    	                setGraphic(comboBox);
    	            }
    	        }
    	    });
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

   
    private void updateOrderState(Order order, char newState) {
        OrderData.updateOrderState(order.getNameProduct(), order.getIdStudent(), newState);
        order.setIsState(newState); // Actualiza el estado localmente en el objeto `Order`
     // Llamar a ClientHandler para notificar al cliente del cambio de estado
        clientHandler.notifyOrderStatusToClient(order.getNameProduct(), newState);
        cartTableView.refresh(); // Refresca la tabla para mostrar el nuevo estado
    }

    private String getStateString(char state) {
        return switch (state) {
            case 'P' -> "Pendiente";
            case 'N' -> "En preparación";
            case 'L' -> "Listo";
            case 'E' -> "Entregado";
            default -> "Desconocido";
        };
    }

    private char getStateChar(String state) {
        return switch (state) {
            case "Pendiente" -> 'P';
            case "En preparación" -> 'N';
            case "Listo" -> 'L';
            case "Entregado" -> 'E';
            default -> 'P'; // Valor predeterminado en caso de error
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
         // Obtener el controlador de la nueva ventana
            //UIProfileController profileController = loader.getController();
            // Pasar la conexión del servidor al controlador de perfil
            //profileController.setServerConnection(serverConnection);
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