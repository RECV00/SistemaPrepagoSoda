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
    private String userId;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

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
        String command = parts[0];

        switch (command) {
            case "LOGIN":
                userId = parts[1];
                String password = parts[2];
                validateUser(userId, password);
                break;
            case "LOAD_DISHES":
                String day = parts[1];
                String time = parts[2];
                sendDishList(day, time);
                break;
            case "PURCHASE":
                userId = parts[1];
                processPurchase(parts, userId);
                break;
            default:
                System.out.println("Comando no reconocido: " + command);
                out.println("ERROR, Comando no reconocido: " + command);
                break;
        }
    }

    private void validateUser(String userID, String password) {
        LinkedList<User> users = UserData.getUsers();
        boolean loginSuccessful = users.stream().anyMatch(user -> 
            String.valueOf(user.getId()).equals(userID) && user.getPassword().equals(password)
        );

        if (loginSuccessful) {
            out.println("SUCCESS," + userID);
        } else {
            out.println("ERROR, Credenciales inválidas");
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

    private void processPurchase(String[] parts, String userId) {
        if (userId == null || userId.isEmpty()) {
            out.println("ERROR, Usuario no autenticado");
            return;
        }

        LinkedList<Order> orders = new LinkedList<>();

        for (int i = 2; i < parts.length; i += 3) {
            if (i + 2 < parts.length) {
                String dishName = parts[i];
                int amount;
                double total;

                try {
                    amount = Integer.parseInt(parts[i + 1]);
                    total = Double.parseDouble(parts[i + 2]);
                    if (amount <= 0 || total <= 0) {
                        out.println("ERROR, Cantidad o total debe ser positivo");
                        return;
                    }
                } catch (NumberFormatException e) {
                    out.println("ERROR, Formato de cantidad o total inválido");
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
            } else {
                out.println("ERROR, Información de compra incompleta");
                return;
            }
        }
    }

    public void notifyOrderStatusToClient(String dishName, char newState) {
        StringBuilder response = new StringBuilder("ORDER_STATUS_UPDATE");
        response.append(",").append(dishName).append(",").append(newState);
        out.println(response.toString());
    }

    public char getOrderState(String stateInput) {
        switch (stateInput.toLowerCase()) {
            case "pendiente": return 'P';
            case "listo": return 'L';
            case "preparacion": return 'I';
            case "entregado": return 'E';
            default:
                System.out.println("Estado no reconocido: " + stateInput);
                return 'U';
        }
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