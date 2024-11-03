package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;


import domain.Dishe;
import domain.Order;
import domain.User;

public class ServerConnection {


    private ServerSocket serverSocket;
   
    public ServerConnection() {
        // Constructor vacío, o puedes pasar configuraciones si lo necesitas
    }
    public void startServer() {
        try {
            serverSocket = new ServerSocket(12354); // Puerto del servidor
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
                out.println("SUCCESS"+","+userID); // Respuesta de éxito   
            }else {
                
                out.println("ERROR,Credenciales inválidas");
            }
        }

        private void sendDishList(String day, String time) {
            System.out.print(" DIA:" + day + " Horario:" + time);
            LinkedList<Dishe> dishes = LogicUIServiceRequestController.getDishesForDayAndTime(day, time);
            System.out.println(dishes);
            StringBuilder response = new StringBuilder("DISH_LIST");
            for (Dishe dish : dishes) {
                response.append(",").append(dish.getServiceName()).append(",").append(dish.getServicePrice());
                System.out.print("Platillos " + dish.toString());
            }
            System.out.println("Enviando al cliente: " + response.toString());

            // Enviar la respuesta al cliente
            out.println(response.toString());
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
               
            } catch (Exception e) {
                e.printStackTrace();
                out.println("ERROR, Error al procesar la compra");
            }
        }

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