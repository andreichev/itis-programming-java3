package ru.itis.gengine.gamelogic;

public class EmptyLevel extends LevelBase {
    @Override
    public void start(World world) {}

    @Override
    public void playerConnected(int id, double x, double y, boolean currentPlayer) {}

    @Override
    public void terminate() {}
}
