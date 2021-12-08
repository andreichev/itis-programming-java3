package ru.itits.fxexample.engine;

import javafx.scene.image.ImageView;

public abstract class Entity extends ImageView {
    public int id;

    public Entity(int id) {
        this.id = id;
    }

    public void update(float deltaTime) {}
}
