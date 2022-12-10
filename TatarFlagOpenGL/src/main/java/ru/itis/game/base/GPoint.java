package ru.itis.game.base;

public class GPoint {
    public float x;
    public float y;

    public GPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ')';
    }
}
