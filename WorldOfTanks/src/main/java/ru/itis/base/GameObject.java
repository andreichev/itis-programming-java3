package ru.itis.base;

import javafx.scene.image.ImageView;

public abstract class GameObject extends ImageView {
    private final String key;

    public boolean isCollision = true;

    public GameObject(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public abstract void update();
}
