package ru.itis.gengine.network.server;

import ru.itis.gengine.application.Application;
import ru.itis.gengine.network.model.NetworkPacket;
import ru.itis.gengine.network.model.NetworkWorld;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private final List<ServerClient> clients;
    private final ServerSocket serverSocket;
    private final NetworkWorld world;

    public Server() throws IOException {
        clients = new ArrayList<>();
        world = new NetworkWorld();

        serverSocket = new ServerSocket(16431);
        System.out.println("SERVER STARTED!");
        Thread thread = new Thread(() -> {
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

    public void addEventExceptClient(NetworkPacket event, ServerClient client) {
        processEvent(event);
        clients.stream()
                .filter(item -> !item.equals(client))
                .forEach(item -> item.addEvent(event));
    }

    public void addEvent(NetworkPacket event) {
        processEvent(event);
        clients.forEach(item -> item.addEvent(event));
    }

    private void processEvent(NetworkPacket event) {
        switch (event.type) {
            case WORLD_SNAPSHOT:
                System.err.println("ERROR");
                world.apply(event);
                break;
            case INITIALIZE_WORLD_HOST:
                break;
            case INITIALIZE_WORLD_CLIENT:
                break;
            case COMPONENT_STATE:
                world.applyToComponent(event);
                break;
            case ENTITY_STATE:
                world.applyToEntity(event);
                break;
            case DESTROY_ENTITY:
                break;
            case END:
                break;
        }
    }

    private void clientConnected(Socket socket) {
        System.out.println("Client Connected!");
        int newClientId = clients.size();
        ServerClient newClient = new ServerClient(newClientId, socket, this);
        if(clients.isEmpty()) {
            newClient.addEvent(NetworkPacket.INITIALIZE_WORLD_HOST);
        } else {
            newClient.addEvent(NetworkPacket.INITIALIZE_WORLD_CLIENT);
            newClient.addEvent(NetworkPacket.worldSnapshot(world));
        }
        clients.add(newClient);
    }

    public void removeClient(ServerClient client) {
        client.terminate();
        clients.removeIf(item -> item.equals(client));
        world.removeEntity(client.getId());
        addEvent(NetworkPacket.destroyEntity(client.getId()));
    }

    public void terminate() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
