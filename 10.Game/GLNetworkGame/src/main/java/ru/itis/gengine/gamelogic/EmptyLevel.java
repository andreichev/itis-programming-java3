package ru.itis.gengine.gamelogic;

import ru.itis.gengine.network.model.NetworkEntity;

public class EmptyLevel extends LevelBase {
    @Override
    public void startServer(World world) {}

    @Override
    public void startClient(World world) {}

    @Override
    public void createEntityNetworkEvent(NetworkEntity entity) {}

    @Override
    public void terminate() {}
}
