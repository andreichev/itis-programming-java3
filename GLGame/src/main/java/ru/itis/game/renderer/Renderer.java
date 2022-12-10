package ru.itis.game.renderer;

import org.lwjgl.opengl.GL;
import ru.itis.game.utils.Utils;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Renderer {

    int ibo;
    int vbo;
    int programId;

    public void initialize() {
        GL.createCapabilities();

        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        float[] vb = new float[] {
                0, 0, 1, 1, 0, 1
        };
        glBufferData(GL_ARRAY_BUFFER, vb, GL_STATIC_DRAW);
        // glBindBuffer(GL_ARRAY_BUFFER, 0);

        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 8, 0);

        ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        int[] indices = new int[] {
            0, 1, 2
        };
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        String vertexCode = Utils.readFromFile("resources/shaders/vertex_shader.glsl");
        String fragmentCode = Utils.readFromFile("resources/shaders/fragment_shader.glsl");

        int vertexShaderId = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShaderId, vertexCode);
        glCompileShader(vertexShaderId);
        checkCompileErrors(vertexShaderId, false);

        int fragmentShaderId = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShaderId, fragmentCode);
        glCompileShader(fragmentShaderId);
        checkCompileErrors(fragmentShaderId, false);

        programId = glCreateProgram();
        glAttachShader(programId, vertexShaderId);
        glAttachShader(programId, fragmentShaderId);
        glLinkProgram(programId);

        checkCompileErrors(programId, true);

        glDeleteShader(vertexShaderId);
        glDeleteShader(fragmentShaderId);

        glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
    }

    public void update() {
        glClear(GL_COLOR_BUFFER_BIT);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glUseProgram(programId);
        glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_INT, 0);
        RendererErrorsHandler.checkForErrorsAndPrint();
    }

    private void checkCompileErrors(int shaderHandle, boolean isProgram) {
        if (isProgram) {
            int status = glGetProgrami(shaderHandle, GL_LINK_STATUS);
            if (status != GL_TRUE) {
                throw new RuntimeException(glGetProgramInfoLog(shaderHandle));
            }
        } else {
            int status = glGetShaderi(shaderHandle, GL_COMPILE_STATUS);
            if (status != GL_TRUE) {
                throw new RuntimeException(glGetShaderInfoLog(shaderHandle));
            }
        }
    }
}
