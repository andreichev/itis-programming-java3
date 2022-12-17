package ru.itis.gengine.base;

import org.joml.Vector4f;

public enum Direction {
    Forward,
    Backward,
    Left,
    Right,
    Up,
    Down;

    public static final Vector4f unitUp = new Vector4f(0.f, 1.f, 0.f, 1.f);
    public static final Vector4f unitForward = new Vector4f(0.f, 0.f, -1.f, 1.f);
    public static final Vector4f unitLeft = new Vector4f(-1.f, 0.f, 0.f, 1.f);
    public static final Vector4f unitRight = new Vector4f(1.f, 0.f, 0.f, 1.f);
}
