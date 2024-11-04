package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import domain.Dishe;
import domain.Order;
import domain.User;

public class ClientHandler extends Thread {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String userId; // ID del usuario conectado

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
             
            this.out = out; // Guardar la referencia de PrintWriter
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
                userId = parts[1]; // Extraer el ID del usuario desde el índice 1
                processPurchase(parts, userId);
                break;
            default:
                System.out.println("Comando no reconocido: " + command);
                out.println("ERROR, Comando no reconocido: " + command);
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
        } else {
            out.println("ERROR, Credenciales inválidas");
        }
    }

    private void sendDishList(String day, String time) {
        System.out.print("DIA: " + day + " Horario: " + time);
        LinkedList<Dishe> dishes = LogicUIServiceRequestController.getDishesForDayAndTime(day, time);
        StringBuilder response = new StringBuilder("DISH_LIST");
        for (Dishe dish : dishes) {
            response.append(",").append(dish.getServiceName()).append(",").append(dish.getServicePrice());
            System.out.print("Platillos " + dish.toString());
        }
        System.out.println("Enviando al cliente: " + response.toString());

        // Enviar la respuesta al cliente
        out.println(response.toString());
    }

    private void processPurchase(String[] parts, String userId) {
        if (userId == null || userId.isEmpty()) {
            out.println("ERROR, Usuario no autenticado");
            return; // Salir si el usuario no ha iniciado sesión o no se recibió un ID
        }

        LinkedList<Order> orders = new LinkedList<>();

        // Procesar cada orden
        for (int i = 2; i < parts.length; i += 3) { // Iniciar en 2 para saltar el userId y command
            if (i + 2 < parts.length) {
                String dishName = parts[i]; // Nombre del platillo
                int amount;
                double total;

                try {
                    amount = Integer.parseInt(parts[i + 1]); // Cantidad
                    total = Double.parseDouble(parts[i + 2]); // Total
                    if (amount <= 0 || total <= 0) {
                        out.println("ERROR, Cantidad o total debe ser positivo");
                        return;
                    }
                } catch (NumberFormatException e) {
                    out.println("ERROR, Formato de cantidad o total inválido");
                    return; // Salir si hay un error en el formato
                }

                // Verificar si el usuario tiene fondos suficientes
                if (!UserData.hasSufficientFunds(Integer.parseInt(userId), total)) {
                    out.println("INSUFFICIENT_FUNDS, Fondos insuficientes para completar la compra.");
                    return; // Salir si el usuario no tiene fondos suficientes
                }

                // Restar el monto del pedido del saldo del usuario
                UserData.updateStudentFunds(Integer.parseInt(userId), -total); // Restar el total del pedido

                char isState = getOrderState("pendiente"); // Estado inicial como "pendiente"

                // Crear y guardar la orden
                Order order = new Order(dishName, amount, total, isState, userId);
                orders.add(order);
                OrderData.saveOrder(order); // Guardar la orden
           
                // Notificar al cliente del estado inicial
                notifyOrderStatusToClient(dishName, isState);
            } else {
                out.println("ERROR, Información de compra incompleta");
                return; // Salir si la información de compra no es completa
            }
        }     
    }
    
    public void notifyOrderStatusToClient(String dishName, char newState) {
        StringBuilder response = new StringBuilder("ORDER_STATUS_UPDATE");
        response.append(",").append(dishName).append(",").append(newState);
        out.println(response.toString()); // Enviar la respuesta al cliente
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