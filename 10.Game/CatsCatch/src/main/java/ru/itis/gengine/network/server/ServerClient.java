package ru.itis.gengine.network.server;

import ru.itis.gengine.application.Application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

// Клиент для работы сервера
public class ServerClient {
    private final int id;
    private final Socket socket;
    private final Thread thread;
    private final int maximumFps;
    // список событий для отправки клиенту
    private final List<NetworkEvent> events;

    private double lastTime;

    public ServerClient(int id, Socket socket, Server server) {
        maximumFps = 60;
        this.id = id;
        this.socket = socket;
        events = new ArrayList<>();

        lastTime = System.currentTimeMillis() / 1e3;
        thread = new Thread(() -> {
            while (Application.shared.isRunning()) {
                double time = System.currentTimeMillis() / 1e3;
                double deltaTime = time - lastTime;
                if(deltaTime < 1.0 / maximumFps) {
                    continue;
                }
                lastTime = time;

                try {
                    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                    NetworkEvent event;
                    while ((event = NetworkEvent.readEvent(dataInputStream)) != null) {
                        server.addEvent(event);
                    }

                    writeAllEvents(dataOutputStream);
                } catch (Exception e) {
                    if (Application.shared.isRunning()) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    private synchronized void writeAllEvents(DataOutputStream dataOutputStream) throws IOException {
        for(NetworkEvent networkEvent: events) {
            NetworkEvent.writeEvent(networkEvent, dataOutputStream);
        }
        dataOutputStream.writeInt(NetworkEvent.END);
        events.clear();
    }

    public synchronized void addEvent(NetworkEvent event) {
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
}
