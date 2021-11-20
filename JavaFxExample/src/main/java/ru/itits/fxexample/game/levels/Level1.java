package ru.itits.fxexample.game.levels;

import ru.itits.fxexample.engine.World;
import ru.itits.fxexample.game.objects.Rect;

public class Level1 {
    public static void initialize(World world) {
        Rect kama1 = new Rect(10, 10);
        Rect kama2 = new Rect(200, 200);
        world.addEntity(kama1);
        world.addEntity(kama2);
    }
}
