package ru.itis.game.network;

import ru.itis.gengine.network.model.NetworkComponentState;

public class PlayerPosition implements NetworkComponentState {
    private float x;
    private float y;

    public PlayerPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
