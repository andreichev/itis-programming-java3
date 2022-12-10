package ru.itis.game.base;

public class GSize {
    public float width;
    public float height;

    public GSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return "(" + width + ", " + height + ')';
    }
}
