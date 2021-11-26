package ru.itits.fxexample.engine.server;

import ru.itits.fxexample.engine.Event;

import java.util.HashSet;
import java.util.Set;

public class ServerEvent {
    public Event event;
    public Set<Integer> sentToClientsIds;

    public ServerEvent(Event event) {
        this.sentToClientsIds = new HashSet<>();
        this.event = event;
    }
}
