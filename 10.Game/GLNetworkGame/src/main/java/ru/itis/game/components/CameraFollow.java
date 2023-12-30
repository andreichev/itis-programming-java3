package ru.itis.game.components;

import org.joml.Vector4f;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.gamelogic.components.Transform;

public class CameraFollow extends Component {
    private Transform target;
    private Transform transform;
    private float minDistance = 0.1f;
    private float speed = 2.9f;

    public CameraFollow(int id, Transform target) {
        super(id, false);
        this.target = target;
    }

    @Override
    public void initialize() {
        transform = getEntity().getTransform();
    }

    @Override
    public void update(float deltaTime) {
        moveTowards(deltaTime);
    }

    private void moveTowards(float deltaTime) {
        if(target == null) { return; }
        float dx = target.getPosition().x - transform.getPosition().x;
        float dy = target.getPosition().y - transform.getPosition().y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        if (distance > minDistance) {
            float vx = (dx / distance) * speed * deltaTime;
            float vy = (dy / distance) * speed * deltaTime;
            transform.setPosition(transform.getPosition().x + vx, transform.getPosition().y + vy, 10.f);
        }
    }
}
