package ru.itis.gengine.network.client;

import ru.itis.gengine.network.server.NetworkEvent;

public interface Replicable {
    void processEvent(NetworkEvent event);
}
