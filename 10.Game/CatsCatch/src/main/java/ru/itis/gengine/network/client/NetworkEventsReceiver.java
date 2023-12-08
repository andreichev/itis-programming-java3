package ru.itis.gengine.network.client;

import ru.itis.gengine.network.server.NetworkEvent;

public interface NetworkEventsReceiver {
    void processEvent(NetworkEvent event);
}
