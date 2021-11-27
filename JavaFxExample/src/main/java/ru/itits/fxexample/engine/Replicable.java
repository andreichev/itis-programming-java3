package ru.itits.fxexample.engine;

import ru.itits.fxexample.engine.network.NetworkEvent;

public interface Replicable {
    void updateState(NetworkEvent event);
}
