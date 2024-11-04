package data;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection {

	private ServerSocket serverSocket;
    private boolean isRunning;

    public ServerConnection() {
        // Constructor vacío, o puedes pasar configuraciones si lo necesitas
    }

    public void startServer() {
        isRunning = true;
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(12363); // Puerto del servidor
                System.out.println("Servidor iniciado, esperando conexiones...");
                
                while (isRunning) {
                    try {
                        // Esperar a que un cliente se conecte
                        Socket clientSocket = serverSocket.accept();
                        System.out.println("Cliente conectado: " + clientSocket.getInetAddress());
                        new Thread(new ClientHandler(clientSocket)).start(); // Manejar la conexión en un nuevo hilo
                    } catch (IOException e) {
                        if (isRunning) {
                            System.err.println("Error al aceptar la conexión del cliente.");
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Error al iniciar el servidor:");
                e.printStackTrace();
            } finally {
                closeServerSocket();
            }
        }).start();
    }

    public void stopServer() {
        isRunning = false; // Detener el bucle de conexión
        closeServerSocket();
        System.out.println("Servidor detenido.");
    }

    private void closeServerSocket() {
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar el ServerSocket.");
                e.printStackTrace();
            }
        }
    }


}