package ru.itis.game.renderer;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL;
import ru.itis.game.base.GSize;

import static org.lwjgl.opengl.GL15.*;

public class Renderer {

    VertexBuffer vertexBuffer;
    IndexBuffer indexBuffer;
    Shader program;

    public void initialize() {
        GL.createCapabilities();

        float[] vb = new float[] {
                -0.7f, -0.7f,
                0.7f, 0.7f,
                -0.7f, 0.7f,
                0.7f, -0.7f
        };
        glBufferData(GL_ARRAY_BUFFER, vb, GL_STATIC_DRAW);
        VertexBufferLayout layout = new VertexBufferLayout();
        layout.pushFloat(2);
        vertexBuffer = new VertexBuffer(vb, false, layout);

        int[] indices = new int[] {
            0, 1, 2,
            0, 1, 3
        };
        indexBuffer = new IndexBuffer(indices, false);

        program = new Shader("resources/shaders/vertex_shader.glsl", "resources/shaders/fragment_shader.glsl");

        glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
    }

    public void updateResolution(GSize size) {
        program.setUniform("iResolution", new Vector2f(size.width, size.height));
    }

    public void updateTime(double dt) {
        program.setUniform("iTime", (float) dt);
    }

    public void update() {
        glClear(GL_COLOR_BUFFER_BIT);
        program.use();
        indexBuffer.bind();
        vertexBuffer.bind();
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
        RendererErrorsHandler.checkForErrorsAndPrint();
    }
}
