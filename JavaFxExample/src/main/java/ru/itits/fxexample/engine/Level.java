package ru.itits.fxexample.engine;

import ru.itits.fxexample.engine.server.ServerDelegate;

public class Level implements ServerDelegate {
    public void initialize(World world) {}

    public void terminate() {}

    @Override
    public void playerConnected(int id) {}
}
