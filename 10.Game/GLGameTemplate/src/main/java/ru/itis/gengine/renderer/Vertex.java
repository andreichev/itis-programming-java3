package ru.itis.gengine.renderer;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Vertex {
    Vector3f pos;
    Vector2f texCoords;
    float light;

    public Vertex(Vector3f pos, Vector2f uv, float light) {
        this.pos = pos;
        this.texCoords = uv;
        this.light = light;
    }

    public Vertex(float x, float y, float z, float u, float v, float light) {
        this.pos = new Vector3f(x, y, z);
        this.texCoords = new Vector2f(u, v);
        this.light = light;
    }
}
