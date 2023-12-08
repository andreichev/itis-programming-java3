package ru.itis.game.components;

import org.joml.Vector4f;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.gamelogic.components.Transform;

public class Laser extends Component {
    private Vector4f target = new Vector4f();

    public void setTarget(Vector4f target) {
        this.target = target;
    }

    @Override
    public void initialize() {
        target = new Vector4f(1.f, 1.f, 0.f, 0.f);
    }

    @Override
    public void terminate() {}

    @Override
    public void update(float deltaTime) {
        Transform transform = getEntity().getTransform();
        transform.rotate(0.f, 2.0f * deltaTime, 0.f);

        Vector4f position = transform.getPosition();
        Vector4f diff = new Vector4f(target.x - position.x, target.y - position.y, 0.f, 0.f);
        if(diff.length() < 0.1) { return; }
        Vector4f newPosition = position.add(diff.mul(8.0f * deltaTime));
        transform.setPosition(newPosition.x, newPosition.y, newPosition.z);
    }
}
