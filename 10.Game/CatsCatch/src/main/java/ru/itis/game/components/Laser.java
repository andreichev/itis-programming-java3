package ru.itis.game.components;

import org.joml.Vector4f;
import ru.itis.game.network.ObjectPosition;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.gamelogic.components.Transform;
import ru.itis.gengine.network.model.NetworkComponentState;

public class Laser extends Component {
    private Vector4f target = new Vector4f();
    private Transform transform;
    private final boolean isServer;

    public Laser(int id, boolean isServer) {
        super(id, true);
        this.isServer = isServer;
    }

    public void setTarget(Vector4f target) {
        this.target = target;
    }

    @Override
    public void initialize() {
        transform = getEntity().getTransform();
        target = new Vector4f(1.f, 1.f, 0.f, 0.f);
    }

    @Override
    public void update(float deltaTime) {
        transform = getEntity().getTransform();
        transform.rotate(0.f, 2.0f * deltaTime, 0.f);

        Vector4f position = transform.getPosition();
        Vector4f diff = new Vector4f(target.x - position.x, target.y - position.y, 0.f, 0.f);
        if(diff.length() < 0.1) { return; }
        Vector4f newPosition = position.add(diff.mul(8.0f * deltaTime));
        transform.setPosition(newPosition.x, newPosition.y, newPosition.z);
        if(isServer) {
            sendCurrentState();
        }
    }

    @Override
    public NetworkComponentState getState() {
        Vector4f coordinates = transform.getPosition();
        return new ObjectPosition(coordinates.x, coordinates.y);
    }

    @Override
    public void setState(NetworkComponentState state) {
        ObjectPosition position = (ObjectPosition) state;
        transform.setPosition(position.x(), position.y(), 0.f);
    }
}
