package ru.itis.utills;

import ru.itis.base.Direction;
import ru.itis.base.Game;
import ru.itis.base.GameObject;
import ru.itis.gameobjects.Tank;
import javafx.geometry.Bounds;

import java.util.List;

public class SceneEndChecker {

    public static boolean moveAcceptable(Tank tank, Direction direction) {
        final Bounds worldBounds = Game.instance.getGamingPane().getLayoutBounds();
        final Bounds tankBounds = tank.getBoundsInParent();
        double indent = 5;

        if (direction == Direction.UP && tankBounds.getMinY() < worldBounds.getMinY()) {
            return false;
        } else if (direction == Direction.LEFT && tankBounds.getMinX() < worldBounds.getMinX()) {
            return false;
        } else if (direction == Direction.DOWN && tankBounds.getMaxY() > worldBounds.getMaxY()) {
            return false;
        } else if (direction == Direction.RIGHT && tankBounds.getMaxX() > worldBounds.getMaxX()) {
            return false;
        }

        List<GameObject> collisionList = Game.instance.getAllCollision();
        for (GameObject collision: collisionList) {
            if (collision.equals(tank)) { continue; }
            Bounds collisionBounds = collision.getBoundsInParent();
            if (direction == Direction.UP
                    && (tankBounds.getMinY() - indent < collisionBounds.getMaxY()
                    && tankBounds.getMaxY() > collisionBounds.getMinY()
                    )
                    && (tankBounds.getMaxX() > collisionBounds.getMinX()
                    && tankBounds.getMinX() < collisionBounds.getMaxX()
                    )
            ) {
                return false;
            }
            if (direction == Direction.DOWN
                    && (tankBounds.getMinY() < collisionBounds.getMaxY()
                    && tankBounds.getMaxY() + indent > collisionBounds.getMinY()
                    )
                    && (tankBounds.getMaxX() > collisionBounds.getMinX()
                    && tankBounds.getMinX() < collisionBounds.getMaxX()
                    )
            ) {
                return false;
            }
            if (direction == Direction.LEFT
                    && (tankBounds.getMaxX() > collisionBounds.getMinX()
                    && tankBounds.getMinX() - indent < collisionBounds.getMaxX()
                    )
                    && (tankBounds.getMinY() < collisionBounds.getMaxY()
                    && tankBounds.getMaxY() > collisionBounds.getMinY()
                    )
            ) {
                return false;
            }
            if (direction == Direction.RIGHT
                    && (tankBounds.getMaxX() + indent > collisionBounds.getMinX()
                    && tankBounds.getMinX() < collisionBounds.getMaxX()
            )
                    && (tankBounds.getMinY() < collisionBounds.getMaxY()
                    && tankBounds.getMaxY() > collisionBounds.getMinY()
            )
            ) {
                return false;
            }
        }

        return true;
    }
}
