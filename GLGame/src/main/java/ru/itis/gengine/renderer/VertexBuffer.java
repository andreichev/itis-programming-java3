package ru.itis.gengine.renderer;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15.*;

// Буфер для хранения вершин в видеокарте для отрисовки.
// Размер буфера при создании и обновлении должен сохраняться.
public class VertexBuffer {
    private final int id;
    private final VertexBufferLayout layout;
    private final boolean isDynamic;

    public VertexBuffer(Vertex[] vertices, boolean isDynamic) {
        this.isDynamic = isDynamic;
        id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, id);
        FloatBuffer buffer = MemoryUtil.memAllocFloat(vertices.length * 6);
        for (Vertex vertex : vertices) {
            buffer
                    .put(vertex.pos.x).put(vertex.pos.y).put(vertex.pos.z)
                    .put(vertex.texCoords.x).put(vertex.texCoords.y)
                    .put(vertex.light);
        }
        buffer.flip();
        glBufferData(GL_ARRAY_BUFFER, buffer, isDynamic ? GL_DYNAMIC_DRAW : GL_STATIC_DRAW);
        layout = new VertexBufferLayout();
        layout.pushFloat(3);
        layout.pushFloat(2);
        layout.pushFloat(1);
        layout.initializeForRenderer();
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public VertexBuffer(float[] data, boolean isDynamic, VertexBufferLayout layout) {
        this.isDynamic = isDynamic;
        this.layout = layout;
        id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, id);
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        for (float item : data) {
            buffer.put(item);
        }
        buffer.flip();
        glBufferData(GL_ARRAY_BUFFER, buffer, isDynamic ? GL_DYNAMIC_DRAW : GL_STATIC_DRAW);
        layout.initializeForRenderer();
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void update(float[] data) {
        if (isDynamic == false) {
            throw new RuntimeException("Невозможно обновить статичный буфер");
        }
        glBindBuffer(GL_ARRAY_BUFFER, id);
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        for (float item : data) {
            buffer.put(item);
        }
        buffer.flip();
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void update(Vertex[] vertices) {
        if (isDynamic == false) {
            throw new RuntimeException("Невозможно обновить статичный буфер");
        }
        glBindBuffer(GL_ARRAY_BUFFER, id);
        FloatBuffer buffer = MemoryUtil.memAllocFloat(vertices.length * 6);
        for (Vertex vertex : vertices) {
            buffer
                    .put(vertex.pos.x).put(vertex.pos.y).put(vertex.pos.z)
                    .put(vertex.texCoords.x).put(vertex.texCoords.y)
                    .put(vertex.light);
        }
        buffer.flip();
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void bind() {
        layout.bind();
    }

    public void unbind() {
        layout.unbind();
    }

    public void delete() {
        layout.delete();
        glDeleteBuffers(id);
    }
}
