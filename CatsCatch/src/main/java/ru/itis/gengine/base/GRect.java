package ru.itis.gengine.base;

public class GRect {
    public GPoint origin;
    public GSize size;

    public GRect(GPoint origin, GSize size) {
        this.origin = origin;
        this.size = size;
    }

    public GRect(float x, float y, float width, float height) {
        this.origin = new GPoint(x, y);
        this.size = new GSize(width, height);
    }

    public static GRect zero() {
        return new GRect(0, 0, 0, 0);
    }

    @Override
    public String toString() {
        return "GRect, origin=" + origin + ", size=" + size;
    }
}
