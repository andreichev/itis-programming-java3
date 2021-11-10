package ru.itis.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {
    private Socket client;

    private PrintWriter toServer; // на EchoServerSocket fromClient
    private BufferedReader fromServer; // на EchoServerSocket toClient

    public SocketClient(String host, int port) {
        try {
            client = new Socket(host, port);
            toServer = new PrintWriter(client.getOutputStream(), true);
            fromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void sendTankPosition(double x, double y) {
        String message = "Положение объекта по ОХ:" + x + ", по ОУ:" + y;
        toServer.println(message);
    }

    public BufferedReader getFromServer() {
        return fromServer;
    }
}

