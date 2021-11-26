package ru.itits.fxexample.engine.server;

import ru.itits.fxexample.engine.Event;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Server {
    private final List<ServerClient> clients;
    private final List<ServerDelegate> delegates;
    private final List<ServerEvent> events;

    public Server() throws IOException {
        clients = new ArrayList<>();
        delegates = new ArrayList<>();
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

    public void addEvent(Event event) {
        events.add(new ServerEvent(event));
    }

    public Event getForClient(int id) {
        Optional<ServerEvent> optionalEvent = events.
                stream()
                .filter(item -> item.sentToClientsIds.contains(id) == false)
                .findAny();
        if(optionalEvent.isPresent() == false) { return null; }
        ServerEvent serverEvent = optionalEvent.get();
        serverEvent.sentToClientsIds.add(id);
        return serverEvent.event;
    }

    public void addDelegate(ServerDelegate delegate) {
        delegates.add(delegate);
    }

    public void removeDelegate(ServerDelegate delegate) {
        delegates.add(delegate);
    }

    public void clear() {
        delegates.clear();
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

        ServerClient client = new ServerClient(clients.size(), socket, this);
        clients.add(client);

        for(ServerDelegate delegate: delegates) {
            delegate.playerConnected(client.getId());
        }
    }
}
