package ru.itis.socket_sandbox;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private int counter = 0;

    public Server() {
        try {
            serverSocket = new ServerSocket(5678);
            System.out.println("SERVER: SERVER STARTED AT PORT 5678");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while (true) {
            serveClient();
        }
    }

    private void serveClient() {
        Socket socket;
        try {
            socket = serverSocket.accept();
            counter++;
            System.out.println("SERVER: CLIENT NUMBER " + counter + " CONNECTED");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        DataOutputStream dataOutputStream;
        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            System.out.println("SERVER: START SENDING DATA");
            dataOutputStream.writeInt(counter);
            System.out.println("SERVER: END SENDING DATA");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
