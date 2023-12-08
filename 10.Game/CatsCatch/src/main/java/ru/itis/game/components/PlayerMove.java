package ru.itis.game.components;

import ru.itis.gengine.base.Direction;
import ru.itis.gengine.events.Events;
import ru.itis.gengine.events.Key;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.gamelogic.Physics;
import ru.itis.gengine.gamelogic.components.Transform;

public class PlayerMove extends Component {
    public float moveSpeed = 8.0f;
    private Transform transform;
    private Events events;

    @Override
    public void initialize() {
        transform = getEntity().getTransform();
        events = getEntity().getEvents();
    }

    @Override
    public void update(float deltaTime) {
        Physics physics = getEntity().getPhysics();

        Direction direction = null;
        float speed = moveSpeed * deltaTime;

        if (events.isKeyPressed(Key.W) ) {
            direction = Direction.Up;
        }
        if (events.isKeyPressed(Key.S)) {
            direction = Direction.Down;
        }
        if (events.isKeyPressed(Key.A)) {
            direction = Direction.Left;
        }
        if (events.isKeyPressed(Key.D)) {
            direction = Direction.Right;
        }

        if (direction != null && physics.moveAcceptable(getEntity(), speed, direction)) {
            transform.translate(direction, speed);
        }
    }
}
