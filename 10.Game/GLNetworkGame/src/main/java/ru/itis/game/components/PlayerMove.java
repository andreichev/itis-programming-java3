package ru.itis.game.components;

import ru.itis.gengine.application.Application;
import ru.itis.gengine.base.Direction;
import ru.itis.gengine.events.Events;
import ru.itis.gengine.events.Key;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.gamelogic.Physics;
import ru.itis.gengine.gamelogic.components.Transform;

public class PlayerMove extends Component {
    private Events events;
    private Transform transform;
    private Physics physics;
    private final float speed = 3.f;
    private float verticalVelocity = 0.f;
    private float jumpForce = 10.f;
    private float gravity = 0.4f;
    private Direction direction;

    public PlayerMove(int id) {
        super(id, true);
    }

    @Override
    public void initialize() {
        events = getEntity().getEvents();
        physics = getEntity().getPhysics();
        transform = getEntity().getTransform();
        direction = Direction.Right;
    }

    @Override
    public void update(float deltaTime) {
        if(events.isKeyPressed(Key.A)) {
            if(direction != Direction.Left) {
                transform.setRotation(0.f, (float) (Math.PI * 1.f), 0.f);
                direction = Direction.Left;
            }
            transform.translate(-speed * deltaTime, 0.f, 0.f);
        }
        if(events.isKeyPressed(Key.D)) {
            if(direction != Direction.Right) {
                transform.setRotation(0.f, 0.f, 0.f);
                direction = Direction.Right;
            }
            transform.translate(speed * deltaTime, 0.f, 0.f);
        }
        boolean isGrounded = !physics.moveAcceptable(getEntity(), verticalVelocity, Direction.Down);
        if(events.isKeyJustPressed(Key.SPACE) && isGrounded) {
            verticalVelocity += jumpForce * deltaTime;
        }
        if (verticalVelocity < 0 && isGrounded) {
            verticalVelocity = 0;
        } else {
            verticalVelocity -= gravity * deltaTime;
        }
        if(Math.abs(verticalVelocity) > 0.01f) {
            transform.translate(Direction.Up, verticalVelocity);
        }
    }
}
