package ru.itits.fxexample.engine.server;

import ru.itits.fxexample.engine.Event;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

// Клиент для работы сервера
public class ServerClient {
    private final int id;
    private final Socket socket;
    private final Thread thread;
    private final int maximumFps;
    private final Server server;

    private double lastTime;

    public ServerClient(int id, Socket socket, Server server) {
        maximumFps = 60;
        this.id = id;
        this.socket = socket;
        this.server = server;

        lastTime = System.currentTimeMillis() / 1e3;
        thread = new Thread(() -> {
            while (true) {
                double time = System.currentTimeMillis() / 1e3;
                double deltaTime = time - lastTime;
                if(deltaTime < 1.0 / maximumFps) {
                    continue;
                }
                lastTime = time;

                try {
                    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                    Event event;
                    while ((event = Event.readEvent(dataInputStream)) != null) {
                        server.addEvent(event);
                    }

                    while ((event = server.getForClient(id)) != null) {
                        Event.writeMessage(event, dataOutputStream);
                    }
                    dataOutputStream.writeInt(Event.END);
                } catch (Exception ignored) {}
            }
        });
        thread.start();
    }

    public void terminate() {
        thread.interrupt();
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
