package data;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection {

    private static ServerConnection instance;
    private ServerSocket serverSocket;
    private volatile boolean isRunning; // Uso de volatile para mayor seguridad en hilos

    public ServerConnection() {
        // Constructor privado para implementar el patrón Singleton
    }

    public static synchronized ServerConnection getInstance() {
        if (instance == null) {
            instance = new ServerConnection();
        }
        return instance;
    }

    public void startServer() {
        if (isRunning) {
            System.out.println("El servidor ya está en ejecución.");
            return; // Evita iniciar el servidor si ya está corriendo
        }
        isRunning = true;

        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(12397); // Puerto del servidor
                System.out.println("Servidor iniciado, esperando conexiones...");

                while (isRunning) {
                    acceptClientConnection(); // Método separado para aceptar conexiones
                }
            } catch (IOException e) {
                System.err.println("Error al iniciar el servidor:");
                e.printStackTrace();
            } finally {
                closeServerSocket();
            }
        }).start();
    }

    private void acceptClientConnection() {
        try {
            // Esperar a que un cliente se conecte
            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
            new Thread(new ClientHandler(clientSocket)).start(); // Manejar la conexión en un nuevo hilo
        } catch (IOException e) {
            if (isRunning) {
                System.err.println("Error al aceptar la conexión del cliente:");
                e.printStackTrace();
            }
        }
    }

    public void stopServer() {
        isRunning = false; // Detener el bucle de conexión
        closeServerSocket(); // Cerrar el socket
        System.out.println("Servidor detenido.");
    }

    private void closeServerSocket() {
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close(); // Cerrar el ServerSocket
                System.out.println("ServerSocket cerrado.");
            } catch (IOException e) {
                System.err.println("Error al cerrar el ServerSocket:");
                e.printStackTrace();
            }
        }
    }
}