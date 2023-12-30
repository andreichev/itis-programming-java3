package ru.itis.gengine.gamelogic.components;

import ru.itis.gengine.base.GSize;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.gamelogic.Entity;

public class BoxCollider extends Component {
    private final GSize size;
    private boolean isTrigger;

    public BoxCollider(float width, float height) {
        super(-2, false);
        size = new GSize(width, height);
        isTrigger = false;
    }

    public BoxCollider(GSize size) {
        super(-2, false);
        this.size = size;
        isTrigger = false;
    }

    public GSize getSize() {
        return size;
    }

    public float getMinY() {
        return getEntity().getTransform().getPosition().y - size.height / 2;
    }

    public float getMinX() {
        return getEntity().getTransform().getPosition().x - size.width / 2;
    }

    public float getMaxY() {
        return getEntity().getTransform().getPosition().y + size.height / 2;
    }

    public float getMaxX() {
        return getEntity().getTransform().getPosition().x + size.width / 2;
    }

    public boolean isTrigger() {
        return isTrigger;
    }

    public void setIsTrigger(boolean trigger) {
        isTrigger = trigger;
    }

    @Override
    public void initialize() {
        Entity entity = getEntity();
        entity.getPhysics().register(this);
    }

    @Override
    public void terminate() {
        Entity entity = getEntity();
        entity.getPhysics().unregister(this);
    }
}
