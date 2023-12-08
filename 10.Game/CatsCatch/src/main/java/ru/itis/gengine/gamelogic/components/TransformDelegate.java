package ru.itis.gengine.gamelogic.components;

import org.joml.Vector3f;
import org.joml.Vector4f;

public interface TransformDelegate {
    void transformChanged(Vector4f position, Vector3f rotation);
}
