package data;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection {

    private ServerSocket serverSocket;
    private volatile boolean isRunning; 
    public static int PORT = 12395; 

    @SuppressWarnings("static-access")
	public ServerConnection(int port) {
        this.PORT = port; // Permitir la configuración del puerto
    }

    public void startServer() {
        if (isRunning) {
            System.out.println("El servidor ya está en ejecución.");
            return; 
        }
        isRunning = true;

        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(PORT ); // Usar el puerto configurado
                System.out.println("Servidor iniciado, esperando conexiones...");

                while (isRunning) {
                    acceptClientConnection(); 
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
            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
            new Thread(new ClientHandler(clientSocket)).start(); 
        } catch (IOException e) {
            if (isRunning) {
                System.err.println("Error al aceptar la conexión del cliente:");
                e.printStackTrace();
            }
        }
    }

    public void stopServer() {
        isRunning = false; 
        closeServerSocket(); 
        System.out.println("Servidor detenido.");
    }

    private void closeServerSocket() {
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close(); 
                System.out.println("ServerSocket cerrado.");
            } catch (IOException e) {
                System.err.println("Error al cerrar el ServerSocket:");
                e.printStackTrace();
            }
        }
    }

	public static int getPORT() {
		return PORT;
	}

	public static void setPORT(int pORT) {
		PORT = pORT;
	}
    
    
    
}
