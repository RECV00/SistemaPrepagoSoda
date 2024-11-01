package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import domain.Dishe;
import domain.Order;
import domain.Student;
import domain.User;

public class ServerConnection {

	private ServerSocket serverSocket;

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
                    String userID = parts[1];
                    String password = parts[2];
                    validateUser(userID, password);
                    break;
                case "REGISTER":
                    int newId = Integer.parseInt(parts[1]);
                    String newPassword = parts[2];
                    char newTipe = parts[3].charAt(0);
                    String newPhotoRoute = parts[4];
                    saveUser(newId, newPassword, newTipe, newPhotoRoute);
                    break;
                case "GET_STUDENT_LIST":
                    sendStudentList();
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
                out.println("SUCCESS," + userID); // Respuesta de éxito
            } else {
                out.println("ERROR,Credenciales inválidas");
            }
        }

        private void saveUser(int id, String password, char tipe, String photoRoute) {
            // Crear el usuario con los datos recibidos del cliente
            User newUser = new User(id, password, tipe, photoRoute);
            
            try {
                UserData.saveUser(newUser); // Guardar usuario en la base de datos mediante el procedimiento almacenado
                out.println("SUCCESS,Usuario registrado exitosamente");
            } catch (Exception e) {
                e.printStackTrace();
                out.println("ERROR,Error al registrar el usuario");
            }
        }
        
        private void sendStudentList() {
            // Suponiendo que tenemos un método en UserData para obtener los nombres de los estudiantes
            List<Student> students = StudentData.getStudentList();
            StringBuilder response = new StringBuilder("STUDENT_LIST");

            for (Student student : students) {
                response.append(",").append(student);
            }

            out.println(response.toString()); // Enviar la lista de estudiantes al cliente
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
            String studentId = parts[1];
            LinkedList<Order> orders = new LinkedList<>();

            for (int i = 2; i < parts.length; i += 3) {
                String nameProduct = parts[i];
                int amount = Integer.parseInt(parts[i + 1]);
                double total = Double.parseDouble(parts[i + 2]);
                
                char isState = getOrderState("pendiente"); // Aquí decides el estado

                // Crear la orden y agregarla a la lista
                Order order = new Order(nameProduct, amount, total, isState, studentId);
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


