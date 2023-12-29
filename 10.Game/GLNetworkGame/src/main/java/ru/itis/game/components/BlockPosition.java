package ru.itis.game.components;

import org.joml.Vector4f;
import ru.itis.game.network.ObjectPosition;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.gamelogic.components.Transform;
import ru.itis.gengine.network.model.NetworkComponentState;

public class BlockPosition extends Component {
    Transform transform;

    public BlockPosition(int id) {
        super(id, true);
    }

    @Override
    public void initialize() {
        transform = getEntity().getTransform();
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
