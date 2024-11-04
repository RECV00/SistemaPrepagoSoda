package data;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection {

	private ServerSocket serverSocket;

    public ServerConnection() {
        // Constructor vacío, o puedes pasar configuraciones si lo necesitas
    }
    public void startServer() {
        try {
            serverSocket = new ServerSocket(12360); // Puerto del servidor
            System.out.println("Servidor iniciado, esperando conexiones...");
            while (true) {
                Socket clientSocket = serverSocket.accept(); // Esperar a que un cliente se conecte
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());
                new Thread(new ClientHandler(clientSocket)).start(); // Manejar la conexión en un nuevo hilo
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}