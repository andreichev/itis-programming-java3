package ru.itits.fxexample.engine.network;

import ru.itits.fxexample.game.network.NetworkEventType;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        final Random random = new Random();
        int newClientId = clients.size();
        double[] buffer = new double[10];

        ServerClient newClient = new ServerClient(newClientId, socket, this);

        buffer[0] = random.nextInt(200);
        buffer[1] = random.nextInt(200);
        newClient.addEvent(new NetworkEvent(NetworkEventType.PLAYER_CONNECTED.value, newClientId, buffer));
        for(ServerClient serverClient : clients) {
            serverClient.addEvent(new NetworkEvent(NetworkEventType.PLAYER_CONNECTED.value, newClientId, buffer));
        }

        buffer[0] = 0;
        buffer[1] = 0;
        for (ServerClient serverClient : clients) {
            NetworkEvent playerConnectedEvent = new NetworkEvent(NetworkEventType.PLAYER_CONNECTED.value, serverClient.getId(), buffer);
            newClient.addEvent(playerConnectedEvent);
        }
        clients.add(newClient);
    }
}
