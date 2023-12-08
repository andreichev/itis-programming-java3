package ru.itis.gengine.gamelogic;

public abstract class LevelBase {
    public abstract void start(World world);
    public abstract void playerConnected(int id, double x, double y, boolean currentPlayer);
    public abstract void terminate();
}
