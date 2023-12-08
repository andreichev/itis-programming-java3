package ru.itits.fxexample.engine;

import javafx.scene.image.ImageView;

public abstract class Entity extends ImageView {
    public int id;
    public boolean isCollision;

    public Entity(int id) {
        this.id = id;
        this.isCollision = false;
    }

    public Entity(int id, boolean isCollision) {
        this.id = id;
        this.isCollision = isCollision;
    }

    public void update(float deltaTime) {}
}
