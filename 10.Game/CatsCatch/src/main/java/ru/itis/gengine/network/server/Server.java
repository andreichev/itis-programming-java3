package ru.itis.gengine.network.server;

import ru.itis.game.network.NetworkEventType;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private final List<ServerClient> clients;

    public Server() throws IOException {
        clients = new ArrayList<>();

        ServerSocket serverSocket = new ServerSocket(16431);
        System.out.println("SERVER STARTED!");
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    clientConnected(socket);
                } catch (IOException e) {
                    e.printStackTrace();
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
}
