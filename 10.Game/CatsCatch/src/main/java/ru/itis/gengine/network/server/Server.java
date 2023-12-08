package ru.itis.gengine.network.server;

import ru.itis.game.network.NetworkEventType;
import ru.itis.gengine.application.Application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private final List<ServerClient> clients;
    private final ServerSocket serverSocket;
    private final Thread thread;

    public Server() throws IOException {
        clients = new ArrayList<>();

        serverSocket = new ServerSocket(16431);
        System.out.println("SERVER STARTED!");
        thread = new Thread(() -> {
            while (Application.shared.isRunning()) {
                try {
                    Socket socket = serverSocket.accept();
                    clientConnected(socket);
                } catch (IOException e) {
                    if (Application.shared.isRunning()) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    public void addEvent(NetworkEvent event) {
        for(ServerClient client: clients) {
            client.addEvent(event);
        }
    }

    public void clear() {
        for(ServerClient client: clients) {
            client.terminate();
        }
        clients.clear();
    }

    private void clientConnected(Socket socket) {
        System.out.println("Client Connected!");
        int newClientId = clients.size();
        ServerClient newClient = new ServerClient(newClientId, socket, this);
        double[] data = new double[10];
        clients.add(newClient);
        NetworkEvent clientConnectedEvent = new NetworkEvent(NetworkEventType.PLAYER_CONNECTED.value, clients.size(), data);
        addEvent(clientConnectedEvent);
    }

    public void terminate() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
