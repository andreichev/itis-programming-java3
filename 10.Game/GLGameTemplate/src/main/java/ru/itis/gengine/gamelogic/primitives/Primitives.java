package ru.itis.gengine.gamelogic.primitives;

import ru.itis.gengine.renderer.Vertex;

public class Primitives {
    public static MeshData createCube(float SIZE) {
        Vertex[] vertices = {
                // Front
                new Vertex(-SIZE / 2.0f, -SIZE / 2.0f, SIZE / 2.0f, 0.0f, 1.0f, 1.f), // 0
                new Vertex(SIZE / 2.0f, -SIZE / 2.0f, SIZE / 2.0f, 1.0f, 1.0f, 1.f),  // 1
                new Vertex(SIZE / 2.0f, SIZE / 2.0f, SIZE / 2.0f, 1.0f, 0.0f, 1.f),   // 2
                new Vertex(-SIZE / 2.0f, SIZE / 2.0f, SIZE / 2.0f, 0.0f, 0.0f, 1.f),  // 3
                // Back
                new Vertex(-SIZE / 2.0f, -SIZE / 2.0f, -SIZE / 2.0f, 0.0f, 1.0f, 0.75f), // 4
                new Vertex(-SIZE / 2.0f, SIZE / 2.0f, -SIZE / 2.0f, 1.0f, 1.0f, 0.75f),  // 5
                new Vertex(SIZE / 2.0f, SIZE / 2.0f, -SIZE / 2.0f, 1.0f, 0.0f, 0.75f),   // 6
                new Vertex(SIZE / 2.0f, -SIZE / 2.0f, -SIZE / 2.0f, 0.0f, 0.0f, 0.75f),  // 7
                // Top
                new Vertex(-SIZE / 2.0f, SIZE / 2.0f, -SIZE / 2.0f, 0.0f, 1.0f, 0.95f), // 8
                new Vertex(-SIZE / 2.0f, SIZE / 2.0f, SIZE / 2.0f, 1.0f, 1.0f, 0.95f),  // 11
                new Vertex(SIZE / 2.0f, SIZE / 2.0f, SIZE / 2.0f, 1.0f, 0.0f, 0.95f),   // 10
                new Vertex(SIZE / 2.0f, SIZE / 2.0f, -SIZE / 2.0f, 0.0f, 0.0f, 0.95f),  // 9
                // Bottom
                new Vertex(-SIZE / 2.0f, -SIZE / 2.0f, -SIZE / 2.0f, 0.0f, 1.0f, 0.85f), // 12
                new Vertex(SIZE / 2.0f, -SIZE / 2.0f, -SIZE / 2.0f, 1.0f, 1.0f, 0.85f),  // 13
                new Vertex(SIZE / 2.0f, -SIZE / 2.0f, SIZE / 2.0f, 1.0f, 0.0f, 0.85f),   // 14
                new Vertex(-SIZE / 2.0f, -SIZE / 2.0f, SIZE / 2.0f, 0.0f, 0.0f, 0.85f),  // 15
                // Left
                new Vertex(-SIZE / 2.0f, -SIZE / 2.0f, -SIZE / 2.0f, 0.0f, 1.0f, 0.9f), // 16
                new Vertex(-SIZE / 2.0f, -SIZE / 2.0f, SIZE / 2.0f, 1.0f, 1.0f, 0.9f),  // 17
                new Vertex(-SIZE / 2.0f, SIZE / 2.0f, SIZE / 2.0f, 1.0f, 0.0f, 0.9f),   // 18
                new Vertex(-SIZE / 2.0f, SIZE / 2.0f, -SIZE / 2.0f, 0.0f, 0.0f, 0.9f),  // 19
                // Right
                new Vertex(SIZE / 2.0f, -SIZE / 2.0f, -SIZE / 2.0f, 0.0f, 1.0f, 0.8f), // 20
                new Vertex(SIZE / 2.0f, SIZE / 2.0f, -SIZE / 2.0f, 1.0f, 1.0f, 0.8f),  // 23
                new Vertex(SIZE / 2.0f, SIZE / 2.0f, SIZE / 2.0f, 1.0f, 0.0f, 0.8f),   // 22
                new Vertex(SIZE / 2.0f, -SIZE / 2.0f, SIZE / 2.0f, 0.0f, 0.0f, 0.8f)   // 21
        };

        int[] indices = {
                0, 1, 2, 2, 3, 0,       // Front
                4, 5, 6, 6, 7, 4,       // Back
                8, 9, 10, 10, 11, 8,    // Top
                12, 13, 14, 14, 15, 12, // Bottom
                16, 17, 18, 18, 19, 16, // Left
                20, 21, 22, 22, 23, 20  // Right
        };

        return new MeshData(vertices, indices);
    }
}
