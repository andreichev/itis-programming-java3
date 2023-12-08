package ru.itis.gengine.gamelogic.primitives;

import ru.itis.gengine.renderer.Vertex;

public class MeshData {
    private final Vertex[] vertices;
    private final int[] indices;

    public MeshData(Vertex[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }
}
