package ru.itis.game.components;

import ru.itis.game.network.PlayerPosition;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.gamelogic.components.Transform;
import ru.itis.gengine.network.model.NetworkComponentState;

public class SecondPlayerMove extends Component {
    private Transform transform;

    public SecondPlayerMove(int id) {
        super(id, true);
    }

    @Override
    public void initialize() {
        transform = getEntity().getTransform();
    }

    @Override
    public void setState(NetworkComponentState state) {
        super.setState(state);
        PlayerPosition position = (PlayerPosition) state;
        transform.setPosition(position.getX(), position.getY(), 0.f);
    }
}
