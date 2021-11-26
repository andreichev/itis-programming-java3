package ru.itits.fxexample.game.levels;

import ru.itits.fxexample.engine.Level;
import ru.itits.fxexample.engine.World;
import ru.itits.fxexample.game.objects.Rect;

import java.util.Random;

public class Level1 extends Level {
    private World world;
    private final Random random = new Random();

    @Override
    public void initialize(World world) {
        this.world = world;
    }

    // MARK: - ServerDelegate

    @Override
    public void playerConnected(int id) {
        Rect kama = new Rect(id, random.nextInt(200), random.nextInt(200));
        world.addEntity(kama);
    }
}
