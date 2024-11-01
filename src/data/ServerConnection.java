package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import business.UIProfileController;
import business.UISaleController;
import domain.Dishe;
import domain.Order;
import domain.User;

public class ServerConnection {


    private ServerSocket serverSocket;
    private UISaleController uiSaleController; // Referencia a UISaleController
    private UIProfileController uiProfileController; // Nueva referencia a UIProfileController

    // Constructor que recibe UISaleController y UIProfileController
    public ServerConnection(UISaleController uiSaleController, UIProfileController uiProfileController) {
        this.uiSaleController = uiSaleController;
        this.uiProfileController = uiProfileController; // Inicializar la nueva referencia
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(12348); // Puerto del servidor
            System.out.println("Servidor iniciado, esperando conexiones...");

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Esperar a que un cliente se conecte
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());
                new ClientHandler(clientSocket).start(); // Manejar la conexión en un nuevo hilo
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String userId; // ID del usuario conectado

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Mensaje recibido: " + inputLine);
                    processRequest(inputLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void processRequest(String request) {
            String[] parts = request.split(",");
            String command = parts[0];

            switch (command) {
                case "LOGIN":
                    userId = parts[1]; // Guardar el ID del usuario al iniciar sesión
                    String password = parts[2];
                    validateUser(userId, password);
                    break;
                case "LOAD_DISHES":
                    String day = parts[1];
                    String time = parts[2];
                    sendDishList(day, time);
                    break;
                case "PURCHASE":
                    processPurchase(parts);
                    break;
//                case "UPDATE_PROFILE":
//                    updateUserProfile(parts);
//                    break;
                default:
                    System.out.println("Comando no reconocido: " + command);
                    break;
            }
        }

        private void validateUser(String userID, String password) {
            // Verificar si el usuario existe en la base de datos
            LinkedList<User> users = UserData.getUsers(); // Asumiendo que tienes una clase UserData que maneja la lista de usuarios
            boolean loginSuccessful = false;

            for (User user : users) {
                if (user.getId() == Integer.parseInt(userID) && user.getPassword().equals(password)) {
                    loginSuccessful = true;
                    break;
                }
            }

            if (loginSuccessful) {
                out.println("SUCCESS," + userID); // Respuesta de éxito
                uiSaleController.userLoggedIn(userID); // Notificar a UISaleController sobre el inicio de sesión
                uiProfileController.loadUserProfile(userID); // Cargar el perfil del usuario
            } else {
                out.println("ERROR,Credenciales inválidas");
            }
        }

        private void sendDishList(String day, String time) {
            // Suponiendo que tenemos un método en alguna clase para obtener los platos según el día y horario
            LinkedList<Dishe> dishes = LogicUIServiceRequestController.getDishesForDayAndTime(day, time);
            StringBuilder response = new StringBuilder("DISH_LIST");

            for (Dishe dish : dishes) {
                response.append(",").append(dish.getServiceName()).append(",").append(dish.getServicePrice());
            }

            out.println(response.toString()); // Enviar la lista de platos al cliente
        }

        private void processPurchase(String[] parts) {
            if (userId == null) {
                out.println("ERROR,Usuario no autenticado");
                return; // Salir si el usuario no ha iniciado sesión
            }

            LinkedList<Order> orders = new LinkedList<>();

            for (int i = 1; i < parts.length; i += 3) { // Cambiado para empezar desde 1
                String nameProduct = parts[i];
                int amount = Integer.parseInt(parts[i + 1]);
                double total = Double.parseDouble(parts[i + 2]);

                char isState = getOrderState("pendiente"); // Aquí decides el estado

                // Crear la orden y agregarla a la lista
                Order order = new Order(nameProduct, amount, total, isState, userId); // Utiliza userId
                orders.add(order);
            }

            try {
                // Guardar todas las órdenes
                for (Order order : orders) {
                    OrderData.saveOrder(order);
                }
                out.println("SUCCESS, Compra realizada exitosamente");
                uiSaleController.updateOrderHistory(orders); // Notificar a UISaleController sobre la compra realizada
            } catch (Exception e) {
                e.printStackTrace();
                out.println("ERROR, Error al procesar la compra");
            }
        }

//        private void updateUserProfile(String[] parts) {
//            if (userId == null) {
//                out.println("ERROR,Usuario no autenticado");
//                return; // Salir si el usuario no ha iniciado sesión
//            }
//
//            String newProfileData = parts[1]; // Asumiendo que se pasa la nueva información del perfil
//            boolean updateSuccessful = uiProfileController.updateProfile(userId, newProfileData);
//            if (updateSuccessful) {
//                out.println("SUCCESS, Perfil actualizado exitosamente");
//            } else {
//                out.println("ERROR, Error al actualizar el perfil");
//            }
//        }

        public char getOrderState(String stateInput) {
            switch (stateInput.toLowerCase()) {
                case "pendiente":
                    return 'P';
                case "listo":
                    return 'L';
                case "preparacion":
                    return 'I';
                case "entregado":
                    return 'E';
                default:
                    System.out.println("Estado no reconocido: " + stateInput);
                    return 'U'; // Estado desconocido
            }
        }
    }
}