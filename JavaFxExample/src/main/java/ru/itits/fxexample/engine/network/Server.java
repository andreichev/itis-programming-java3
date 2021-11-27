package ru.itits.fxexample.engine.network;

import ru.itits.fxexample.game.network.NetworkEventType;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Server {
    private final List<ServerClient> clients;
    private final List<ServerEvent> events;

    public Server() throws IOException {
        clients = new ArrayList<>();
        events = new ArrayList<>();

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
        events.add(new ServerEvent(event));
    }

    // TODO: - возвращать список сразу
    public NetworkEvent getEventForClient(int id) {
        Optional<ServerEvent> optionalEvent = events.
                stream()
                .filter(item -> item.sentToClientIds.contains(id) == false)
                .findAny();
        if(optionalEvent.isPresent() == false) { return null; }
        ServerEvent serverEvent = optionalEvent.get();
        serverEvent.sentToClientIds.add(id);
        if(serverEvent.sentToClientIds.size() == clients.size()) {
            events.remove(serverEvent);
        }
        return serverEvent.event;
    }

    public void clear() {
        for(ServerClient client: clients) {
            client.terminate();
        }
        clients.clear();
    }

    public List<ServerClient> getClients() {
        return clients;
    }

    private void clientConnected(Socket socket) {
        System.out.println("Client Connected!");
        final Random random = new Random();
        int newClientId = clients.size();
        double[] buffer = new double[10];

        buffer[0] = random.nextInt(200);
        buffer[1] = random.nextInt(200);
        ServerEvent serverEvent = new ServerEvent(
                new NetworkEvent(NetworkEventType.PLAYER_CONNECTED.value, newClientId, buffer)
        );
        events.add(serverEvent);

        buffer[0] = 0;
        buffer[1] = 0;
        for (ServerClient serverClient : clients) {
            ServerEvent playerConnectedEvent = new ServerEvent(
                    new NetworkEvent(NetworkEventType.PLAYER_CONNECTED.value, serverClient.getId(), buffer)
            );
            for(ServerClient otherClient : clients) {
                playerConnectedEvent.sentToClientIds.add(otherClient.getId());
            }
            events.add(playerConnectedEvent);
        }

        ServerClient client = new ServerClient(newClientId, socket, this);
        clients.add(client);
    }
}
