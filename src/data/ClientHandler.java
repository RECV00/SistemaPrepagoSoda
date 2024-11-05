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

public class ClientHandler implements Runnable {
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
        String command = parts[0];

        switch (command) {
            case "LOGIN":
                userId = parts[1]; // Guardar el ID del usuario al iniciar sesión
                System.out.print(userId);
                String password = parts[2];
                System.out.print(password);
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
        String stateDescription = getStateDescription(newState);
        
        StringBuilder response = new StringBuilder("ORDER_STATUS_UPDATE");
        response.append(",").append(dishName).append(",").append(stateDescription);
        out.println(response.toString()); // Enviar la respuesta al cliente
    }

    private String getStateDescription(char state) {
        switch (state) {
            case 'P':
                return "Pendiente";
            case 'N':
                return "En preparación";
            case 'E':
                return "Entregado";
            case 'L':
                return "Listo";
            default:
                return "Estado desconocido"; // Manejo de valores inesperados
        }
    }
    private void validateUser(String userId, String password) {
        LinkedList<User> users = UserData.getUsers(); // Obtener la lista de usuarios

        // Verificar las credenciales utilizando streams
        boolean loginSuccessful = users.stream()
            .filter(user -> user.getId() == Integer.parseInt(userId) && user.getTipe().equals("Estudiante")) // Filtrar usuarios por ID y tipo
            .anyMatch(user -> PasswordHasher.verifyPassword(password, user.getPassword(), user.getSalt())); // Verificar la contraseña

        if (loginSuccessful) {
            out.println("SUCCESS"+","+userId); // Enviar éxito si las credenciales son válidas
        } else {
        	out.println("FAILURE"+","+userId);
            sendError("Credenciales inválidas"); // Enviar error si las credenciales son inválidas
        }
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