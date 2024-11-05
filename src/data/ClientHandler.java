package data;

import domain.Dishe;
import domain.Order;
import domain.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;

public class ClientHandler extends Thread {
    private static final String LOGIN_COMMAND = "LOGIN";
    private static final String LOAD_DISHES_COMMAND = "LOAD_DISHES";
    private static final String PURCHASE_COMMAND = "PURCHASE";

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String userId;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
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
            System.err.println("Error de comunicación con el cliente:");
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    private void processRequest(String request) {
        String[] parts = request.split(",");
        if (parts.length == 0) {
            sendError("Petición vacía");
            return;
        }

        String command = parts[0];
        switch (command) {
            case LOGIN_COMMAND:
                handleLogin(parts);
                break;

            case LOAD_DISHES_COMMAND:
                handleLoadDishes(parts);
                break;

            case PURCHASE_COMMAND:
                handlePurchase(parts);
                break;

            default:
                sendError("Comando no reconocido: " + command);
                break;
        }
    }

    private void handleLogin(String[] parts) {
        if (parts.length == 4) {
            userId = parts[1];
            String password = parts[2];
            validateUser(userId, password);
        } else {
            sendError("Argumentos de LOGIN insuficientes");
        }
    }

    private void handleLoadDishes(String[] parts) {
        if (parts.length == 3) {
            String day = parts[1];
            String time = parts[2];
            sendDishList(day, time);
        } else {
            sendError("Argumentos de LOAD_DISHES insuficientes");
        }
    }

    private void handlePurchase(String[] parts) {
        if (parts.length < 3) {
            sendError("Argumentos de PURCHASE insuficientes");
            return;
        }

        userId = parts[1];
        if (userId == null || userId.isEmpty()) {
            sendError("Usuario no autenticado");
            return;
        }

        processPurchase(parts);
    }

    private void validateUser(String userId, String password) {
        LinkedList<User> users = UserData.getUsers();
        boolean loginSuccessful = users.stream().anyMatch(user -> 
            String.valueOf(user.getId()).equals(userId) && user.getPassword().equals(password)
        );

        if (loginSuccessful) {
            out.println("SUCCESS," + userId);
        } else {
            sendError("Credenciales inválidas");
        }
    }

    private void sendDishList(String day, String time) {
        LinkedList<Dishe> dishes = LogicUIServiceRequestController.getDishesForDayAndTime(day, time);
        StringBuilder response = new StringBuilder("DISH_LIST");

        for (Dishe dish : dishes) {
            response.append(",").append(dish.getServiceName()).append(",").append(dish.getServicePrice());
        }

        System.out.println("Enviando al cliente: " + response);
        out.println(response.toString());
    }

    private void processPurchase(String[] parts) {
        LinkedList<Order> orders = new LinkedList<>();

        for (int i = 2; i < parts.length; i += 3) {
            if (i + 2 >= parts.length) {
                sendError("Información de compra incompleta");
                return;
            }

            String dishName = parts[i];
            int amount;
            double total;

            try {
                amount = Integer.parseInt(parts[i + 1]);
                total = Double.parseDouble(parts[i + 2]);

                if (amount <= 0 || total <= 0) {
                    sendError("Cantidad o total debe ser positivo");
                    return;
                }
            } catch (NumberFormatException e) {
                sendError("Formato de cantidad o total inválido");
                return;
            }

            if (!UserData.hasSufficientFunds(Integer.parseInt(userId), total)) {
                out.println("INSUFFICIENT_FUNDS, Fondos insuficientes para completar la compra.");
                return;
            }

            UserData.updateStudentFunds(Integer.parseInt(userId), -total);
            char isState = getOrderState("pendiente");
            Order order = new Order(dishName, amount, total, isState, userId);
            orders.add(order);
            OrderData.saveOrder(order);
            notifyOrderStatusToClient(dishName, isState);
        }
    }

    public void notifyOrderStatusToClient(String dishName, char newState) {
        String response = String.format("ORDER_STATUS_UPDATE", dishName, newState);
        out.println(response);
    }

    private char getOrderState(String stateInput) {
        switch (stateInput.toLowerCase()) {
            case "pendiente": return 'P';
            case "listo": return 'L';
            case "preparacion": return 'I';
            case "entregado": return 'E';
            default:
                System.out.println("Estado no reconocido: " + stateInput);
                return 'U'; // Estado desconocido
        }
    }

    private void sendError(String message) {
        out.println("ERROR, " + message);
    }

    private void closeResources() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            System.err.println("Error al cerrar los recursos del cliente:");
            e.printStackTrace();
        }
    }
}