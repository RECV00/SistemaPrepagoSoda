package business;

import java.io.IOException;
import java.util.LinkedList;

import data.ClientHandler;
import data.OrderData;
import data.ServerConnection;
import data.UserData;
import domain.Order;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class UISaleController {

    @FXML
    private ImageView userProfileImage;
    @FXML
    private Label userNameLabel;

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
    private TableColumn<Order, String> studentIdColumn;
    @FXML
    private TableColumn<Order, String> chanceStateColumn;
    @FXML
    private Label totalLabel;
    @FXML
    private Button bBack;

    private ObservableList<Order> cartItems;
    private ServerConnection serverConnection;
    private ClientHandler clientHandler;

    @FXML
    public void initialize() {
        serverConnection = ServerConnection.getInstance(); // Obtener instancia única de ServerConnection
        initializeCart();
        loadOrdersFromDatabase();
    }

    private void initializeCart() {
        cartItems = FXCollections.observableArrayList();
        cartTableView.setItems(cartItems);

        // Configuración de columnas
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("nameProduct"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("isState"));
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("idStudent"));

        // Configurar columna para cambio de estado
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
        LinkedList<Order> orders = OrderData.getOrders();
        cartItems.addAll(orders);
    }

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
        order.setIsState(newState);
        
        if (clientHandler != null) {
            clientHandler.notifyOrderStatusToClient(order.getNameProduct(), newState);
        }
        
        cartTableView.refresh();
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
            default -> 'P';
        };
    }

    @FXML
    public void returnProfile() {
        closeWindows();
    }


    public void closeWindows() {
        // Detener el servidor si es necesario, pero asegúrate de que no haya clientes conectados
        if (serverConnection != null) {
            // Opcionalmente, podrías verificar si no hay conexiones activas antes de detener el servidor
            serverConnection.stopServer(); // Detener el servidor antes de cerrar la ventana
        }

        // Cerrar la ventana actual
        Stage currentStage = (Stage) bBack.getScene().getWindow();
        currentStage.close();

        // Abrir UIProfile
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIProfile.fxml"));
            Parent root = loader.load();
            UIProfileController profileController = loader.getController();
            profileController.setServerConnection(serverConnection); // Pasar la conexión del servidor si es necesario

            Stage profileStage = new Stage();
            profileStage.setScene(new Scene(root));
            profileStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void setServerConnection(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }
}