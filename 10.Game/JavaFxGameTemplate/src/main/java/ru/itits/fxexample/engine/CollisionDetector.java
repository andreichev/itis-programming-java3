package ru.itits.fxexample.engine;

import javafx.geometry.Bounds;
import ru.itits.fxexample.application.JavaFxApplication;

import java.util.List;

public class CollisionDetector {

    public static boolean moveAcceptable(Entity entity, Direction direction, float amount) {
        final Bounds worldBounds = JavaFxApplication.getInstance().getWorld().getPane().getLayoutBounds();
        final Bounds objectBounds = entity.getBoundsInParent();

        if (direction == Direction.UP && objectBounds.getMinY() < worldBounds.getMinY()) {
            return false;
        } else if (direction == Direction.LEFT && objectBounds.getMinX() < worldBounds.getMinX()) {
            return false;
        } else if (direction == Direction.DOWN && objectBounds.getMaxY() > worldBounds.getMaxY()) {
            return false;
        } else if (direction == Direction.RIGHT && objectBounds.getMaxX() > worldBounds.getMaxX()) {
            return false;
        }

        List<Entity> collisionList = JavaFxApplication.getInstance().getWorld().getAllCollision();
        for (Entity collision: collisionList) {
            if (collision.equals(entity)) { continue; }
            Bounds collisionBounds = collision.getBoundsInParent();
            if (direction == Direction.UP
                    && (objectBounds.getMinY() + amount < collisionBounds.getMaxY()
                    && objectBounds.getMaxY() > collisionBounds.getMinY()
                    )
                    && (objectBounds.getMaxX() > collisionBounds.getMinX()
                    && objectBounds.getMinX() < collisionBounds.getMaxX()
                    )
            ) {
                return false;
            }
            if (direction == Direction.DOWN
                    && (objectBounds.getMinY() < collisionBounds.getMaxY()
                    && objectBounds.getMaxY() + amount > collisionBounds.getMinY()
                    )
                    && (objectBounds.getMaxX() > collisionBounds.getMinX()
                    && objectBounds.getMinX() < collisionBounds.getMaxX()
                    )
            ) {
                return false;
            }
            if (direction == Direction.LEFT
                    && (objectBounds.getMaxX() > collisionBounds.getMinX()
                    && objectBounds.getMinX() - amount < collisionBounds.getMaxX()
                    )
                    && (objectBounds.getMinY() < collisionBounds.getMaxY()
                    && objectBounds.getMaxY() > collisionBounds.getMinY()
                    )
            ) {
                return false;
            }
            if (direction == Direction.RIGHT
                    && (objectBounds.getMaxX() + amount > collisionBounds.getMinX()
                    && objectBounds.getMinX() < collisionBounds.getMaxX()
            )
                    && (objectBounds.getMinY() < collisionBounds.getMaxY()
                    && objectBounds.getMaxY() > collisionBounds.getMinY()
            )
            ) {
                return false;
            }
        }

        return true;
    }
}
