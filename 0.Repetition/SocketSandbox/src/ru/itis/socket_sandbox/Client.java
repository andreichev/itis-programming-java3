package ru.itis.socket_sandbox;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket socket;

    public Client() {
        try {
            socket = new Socket("127.0.0.1", 5678);
            System.out.println("CLIENT: CONNECTED TO SERVER");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        readNumber();
    }

    private void readNumber() {
        DataInputStream dataInputStream;
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            System.out.println("CLIENT: READ BEGIN");
            System.out.println(dataInputStream.readInt());
            System.out.println("CLIENT: READ END");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
