package ru.itits.fxexample.game.levels;

import ru.itits.fxexample.engine.Level;
import ru.itits.fxexample.engine.World;
import ru.itits.fxexample.game.objects.Block;
import ru.itits.fxexample.game.objects.KamaPlayer;

public class Level1 extends Level {
    private World world;

    @Override
    public void initialize(World world) {
        this.world = world;
        world.addEntity(new Block(100, 50, 200, 100, 100));
        world.addEntity(new Block(101, 240, 260, 100, 100));
        world.addEntity(new Block(101, 400, 350, 100, 100));
    }

    @Override
    public void playerConnected(int id, double x, double y, boolean currentPlayer) {
        KamaPlayer kama = new KamaPlayer(id, x, y, currentPlayer);
        world.addEntity(kama);
    }
}
