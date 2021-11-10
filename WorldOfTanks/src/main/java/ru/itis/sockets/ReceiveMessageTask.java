package ru.itis.sockets;

import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;

// слушатель сообщений от сервера
public class ReceiveMessageTask extends Task<Void> {
    // читаем сообщения с сервера
    private BufferedReader fromServer; // на EchoServerSocket toClient

    public ReceiveMessageTask(BufferedReader fromServer) {
        this.fromServer = fromServer;
    }

    @Override
    protected Void call() throws Exception {
        while (true) {
            try {
                String messageFromServer = fromServer.readLine();
                if (messageFromServer != null) {
                    System.out.println(messageFromServer);

                }
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}

