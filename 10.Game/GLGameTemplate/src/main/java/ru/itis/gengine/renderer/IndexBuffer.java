package ru.itis.gengine.renderer;

import static org.lwjgl.opengl.GL15.*;

// Буфер для хранения вершин в видеокарте для отрисовки.
// Размер буфера при создании и обновлении должен сохраняться.
public class IndexBuffer {
    private int id;
    private int size;
    private final boolean isDynamic;

    public IndexBuffer(int[] indices, boolean isDynamic) {
        this.isDynamic = isDynamic;
        this.size = indices.length;
        CommandBuffer.shared.addCommand(() -> {
            this.id = glGenBuffers();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, isDynamic ? GL_DYNAMIC_DRAW : GL_STATIC_DRAW);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        });
    }

    public void update(int[] indices) {
        if (isDynamic == false) {
            throw new RuntimeException("Невозможно обновить статичный буфер");
        }
        CommandBuffer.shared.addCommand(() -> {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_DYNAMIC_DRAW);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        });
    }

    public void bind() {
        CommandBuffer.shared.addCommand(() -> {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
        });
    }

    public void unbind() {
        CommandBuffer.shared.addCommand(() -> {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        });
    }

    public int getSize() {
        return size;
    }

    public void delete() {
        CommandBuffer.shared.addCommand(() -> {
            glDeleteBuffers(id);
        });
    }
}
