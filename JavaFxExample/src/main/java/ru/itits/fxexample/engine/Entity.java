package ru.itits.fxexample.engine;

import javafx.scene.image.ImageView;

public abstract class Entity extends ImageView {
    public int id;
    public boolean isReplicable;

    public Entity(int id, boolean isReplicable) {
        this.id = id;
        this.isReplicable = isReplicable;
    }

    public void update(float deltaTime) {}
}
