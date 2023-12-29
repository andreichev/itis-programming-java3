package ru.itis.gengine.gamelogic;

import ru.itis.gengine.base.Direction;
import ru.itis.gengine.base.GSize;
import ru.itis.gengine.gamelogic.components.BoxCollider;

import java.util.ArrayList;
import java.util.List;

public class Physics {
    private final List<BoxCollider> entities = new ArrayList<>();

    public void register(BoxCollider entity) {
        entities.add(entity);
    }

    public void unregister(BoxCollider entity) {
        entities.remove(entity);
    }

    public boolean moveAcceptable(Entity entity, float amount, Direction direction) {
        BoxCollider collider = entity.getComponentWithType(BoxCollider.class);
        return entities.stream()
                .filter(e -> (!collider.equals(e) && !e.isTrigger()))
                .map(e -> moveAcceptable(collider, e, amount, direction))
                .allMatch(b -> b == true);
    }

    public boolean intersects(Entity one, Entity two) {
        float oneX = one.getTransform().getPosition().x;
        float oneY = one.getTransform().getPosition().y;
        float twoX = two.getTransform().getPosition().x;
        float twoY = two.getTransform().getPosition().y;
        GSize oneSize = one.getComponentWithType(BoxCollider.class).getSize();
        GSize twoSize = two.getComponentWithType(BoxCollider.class).getSize();

        boolean collisionX = oneX + oneSize.width >= twoX &&
                twoX + twoSize.width >= oneX;
        // collision y-axis?
        boolean collisionY = oneY + oneSize.height >= twoY &&
                twoY + twoSize.height >= oneY;
        // collision only if on both axes
        return collisionX && collisionY;
    }

    public boolean moveAcceptable(
            BoxCollider object, BoxCollider collision,
            float amount, Direction direction
    ) {
        // низ объекта + сдвиг вниз меньше верха препятствия
        if (direction == Direction.Down
                && (object.getMinY() - amount < collision.getMaxY()
                && object.getMaxY() > collision.getMinY()
        )
                && (object.getMaxX() > collision.getMinX()
                && object.getMinX() < collision.getMaxX()
        )
        ) {
            return false;
        }
        // верх объекта + сдвиг вверх больше низа препятствия
        if (direction == Direction.Up
                && (object.getMinY() < collision.getMaxY()
                && object.getMaxY() + amount > collision.getMinY()
        )
                && (object.getMaxX() > collision.getMinX()
                && object.getMinX() < collision.getMaxX()
        )
        ) {
            return false;
        }
        // левая сторона объекта + сдвиг влево меньше правой стороны препятствия
        if (direction == Direction.Left
                && (object.getMaxX() > collision.getMinX()
                && object.getMinX() - amount < collision.getMaxX()
        )
                && (object.getMinY() < collision.getMaxY()
                && object.getMaxY() > collision.getMinY()
        )
        ) {
            return false;
        }
        // правая сторона объекта + сдвиг вправо больше левой стороны препятствия
        if (direction == Direction.Right
                && (object.getMaxX() + amount > collision.getMinX()
                && object.getMinX() < collision.getMaxX()
        )
                && (object.getMinY() < collision.getMaxY()
                && object.getMaxY() > collision.getMinY()
        )
        ) {
            return false;
        }

        return true;
    }
}
