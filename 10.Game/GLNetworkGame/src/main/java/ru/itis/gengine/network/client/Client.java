package ru.itis.gengine.network.client;

import ru.itis.gengine.application.Application;
import ru.itis.gengine.gamelogic.World;
import ru.itis.gengine.network.model.NetworkPacket;
import ru.itis.gengine.network.model.NetworkWorld;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
            while (Application.shared.isRunning()) {
                try {
                    InputStream inputStream = socket.getInputStream();
                    OutputStream outputStream = socket.getOutputStream();
                    List<NetworkPacket> events = world.pollEvents();
                    for(NetworkPacket event: events) {
                        NetworkPacket.writeEvent(event, outputStream);
                    }
                    NetworkPacket.writeEvent(NetworkPacket.END, outputStream);
                    NetworkPacket event;
                    while ((event = NetworkPacket.readEvent(inputStream)) != null) {
                        switch (event.type) {
                            case COMPONENT_STATE:
                                world.updateComponentState(event);
                                break;
                            case ENTITY_STATE:
                                world.updateEntityState(event);
                                break;
                            case WORLD_SNAPSHOT:
                                NetworkWorld snapshot = (NetworkWorld) event.data;
                                world.applySnapshot(snapshot);
                                break;
                            case INITIALIZE_WORLD_HOST:
                                Application.shared.getCurrentLevel().startServer(world);
                                break;
                            case INITIALIZE_WORLD_CLIENT:
                                Application.shared.getCurrentLevel().startClient(world);
                                break;
                            case DESTROY_ENTITY:
                                world.destroy(event.objectId);
                                break;
                            case END:
                                break;
                        }
                    }
                } catch (Exception e) {
                    if (Application.shared.isRunning()) {
                        e.printStackTrace();
                        Application.shared.getWindow().setShouldClose(true);
                    }
                }
            }
        });
        thread.start();
    }

    public void terminate() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
