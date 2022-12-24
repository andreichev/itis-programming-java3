package ru.itis.gengine.network.client;

import ru.itis.gengine.gamelogic.World;
import ru.itis.gengine.network.server.NetworkEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.List;

public class Client {
    private final World world;
    private final Socket socket;

    public Client(World world, Socket socket) {
        this.world = world;
        this.socket = socket;
    }

    public void initialize() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    List<NetworkEvent> events = world.pollEvents();
                    for(NetworkEvent event: events) {
                        NetworkEvent.writeEvent(event, dataOutputStream);
                    }
                    dataOutputStream.writeInt(NetworkEvent.END);
                    NetworkEvent event;
                    while ((event = NetworkEvent.readEvent(dataInputStream)) != null) {
                        world.processEventFromServer(event);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
