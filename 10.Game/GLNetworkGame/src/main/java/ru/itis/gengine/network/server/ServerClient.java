package ru.itis.gengine.network.server;

import ru.itis.gengine.application.Application;
import ru.itis.gengine.network.model.NetworkPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

// Клиент для работы сервера
public class ServerClient {
    private final int id;
    private final Socket socket;
    private final int maximumFps;
    // список событий для отправки этому клиенту
    private final List<NetworkPacket> events;

    private double lastTime;

    public ServerClient(int id, Socket socket, Server server) {
        maximumFps = 60;
        this.id = id;
        this.socket = socket;
        events = new ArrayList<>();

        lastTime = System.currentTimeMillis() / 1e3;
        Thread thread = new Thread(() -> {
            while (Application.shared.isRunning()) {
                double time = System.currentTimeMillis() / 1e3;
                double deltaTime = time - lastTime;
                if (deltaTime < 1.0 / maximumFps) {
                    continue;
                }
                lastTime = time;

                try {
                    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                    NetworkPacket event;
                    while ((event = NetworkPacket.readEvent(dataInputStream)) != null) {
                        server.addEventExceptClient(event, this);
                    }

                    writeAllEvents(dataOutputStream);
                } catch (Exception e) {
                    if (Application.shared.isRunning()) {
                        System.out.println("CLIENT ERROR: " + e.getMessage());
                        server.removeClient(this);
                        return;
                    }
                }
            }
        });
        thread.start();
    }

    private synchronized void writeAllEvents(OutputStream outputStream) throws Exception {
        for(NetworkPacket networkPacket : events) {
            NetworkPacket.writeEvent(networkPacket, outputStream);
        }
        NetworkPacket.writeEvent(NetworkPacket.END, outputStream);
        events.clear();
    }

    public synchronized void addEvent(NetworkPacket event) {
        events.add(event);
    }

    public void terminate() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerClient that = (ServerClient) o;
        return id == that.id;
    }
}
