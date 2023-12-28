package ru.itis.gengine.gamelogic;

import ru.itis.gengine.network.model.NetworkEntity;

public abstract class LevelBase {
    public abstract void startServer(World world);
    public abstract void startClient(World world);
    public abstract void createEntityNetworkEvent(NetworkEntity entity);
    public abstract void terminate();
}
