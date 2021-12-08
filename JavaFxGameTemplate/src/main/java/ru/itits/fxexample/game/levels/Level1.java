package ru.itits.fxexample.game.levels;

import ru.itits.fxexample.engine.Level;
import ru.itits.fxexample.engine.World;
import ru.itits.fxexample.game.objects.Rect;

public class Level1 extends Level {
    private World world;

    @Override
    public void initialize(World world) {
        this.world = world;
    }

    @Override
    public void playerConnected(int id, double x, double y, boolean currentPlayer) {
        Rect kama = new Rect(id, x, y, currentPlayer);
        world.addEntity(kama);
    }
}
