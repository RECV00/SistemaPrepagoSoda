package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection {

    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;

    public ServerConnection(String host, int port) throws IOException {
        socket = new Socket(host, port);
        output = new PrintWriter(socket.getOutputStream(), true);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public String sendRegister(String userID, String password) {
        sendMessage("REGISTER," + userID + "," + password);
        return receiveMessage();
    }

    public String sendLogin(String userID, String password) {
        sendMessage("LOGIN," + userID + "," + password);
        return receiveMessage();
    }

    private void sendMessage(String message) {
        output.println(message);
    }

    private String receiveMessage() {
        try {
            return input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR: No se pudo recibir respuesta del servidor";
        }
    }

    public void close() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


