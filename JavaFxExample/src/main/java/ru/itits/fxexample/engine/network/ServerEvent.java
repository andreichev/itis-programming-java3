package ru.itits.fxexample.engine.network;

import java.util.HashSet;
import java.util.Set;

public class ServerEvent {
    public NetworkEvent event;
    public Set<Integer> sentToClientIds;

    public ServerEvent(NetworkEvent event) {
        this.sentToClientIds = new HashSet<>();
        this.event = event;
    }
}
