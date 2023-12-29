package ru.itis.gengine.renderer;

import org.joml.*;
import org.lwjgl.system.MemoryStack;
import ru.itis.gengine.utils.Utils;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private int programId;
    private final Map<String, Integer> uniformLocationCache = new HashMap<>();

    public Shader(String vertexPath, String fragmentPath) {
        CommandBuffer.shared.addCommand(() -> {
            String vertexCode = Utils.readFromFile(vertexPath);
            String fragmentCode = Utils.readFromFile(fragmentPath);

            int vertexHandle, fragmentHandle;

            vertexHandle = glCreateShader(GL_VERTEX_SHADER);
            glShaderSource(vertexHandle, vertexCode);
            glCompileShader(vertexHandle);
            checkCompileErrors(vertexHandle, false);

            fragmentHandle = glCreateShader(GL_FRAGMENT_SHADER);
            glShaderSource(fragmentHandle, fragmentCode);
            glCompileShader(fragmentHandle);
            checkCompileErrors(fragmentHandle, false);

            programId = glCreateProgram();
            glAttachShader(programId, vertexHandle);
            glAttachShader(programId, fragmentHandle);

            glLinkProgram(programId);
            checkCompileErrors(programId, true);

            glDeleteShader(vertexHandle);
            glDeleteShader(fragmentHandle);
        });
    }

    public void delete() {
        CommandBuffer.shared.addCommand(() -> {
            glDeleteProgram(programId);
        });
    }

    public void use() {
        CommandBuffer.shared.addCommand(() -> {
            glUseProgram(programId);
        });
    }

    public void setUniform(String name, int value) {
        CommandBuffer.shared.addCommand(() -> {
            glUniform1i(getUniformLocation(name), value);
        });
    }

    public void setUniform(String name, float value) {
        CommandBuffer.shared.addCommand(() -> {
            glUniform1f(getUniformLocation(name), value);
        });
    }

    public void setUniform(String name, Vector2f value) {
        CommandBuffer.shared.addCommand(() -> {
            try (MemoryStack stack = MemoryStack.stackPush()) {
                FloatBuffer buffer = stack.mallocFloat(2);
                value.get(buffer);
                glUniform2fv(getUniformLocation(name), buffer);
            }
        });
    }

    public void setUniform(String name, Vector3f value) {
        CommandBuffer.shared.addCommand(() -> {
            try (MemoryStack stack = MemoryStack.stackPush()) {
                FloatBuffer buffer = stack.mallocFloat(3);
                value.get(buffer);
                glUniform3fv(getUniformLocation(name), buffer);
            }
        });
    }

    public void setUniform(String name, Vector4f value) {
        CommandBuffer.shared.addCommand(() -> {
            try (MemoryStack stack = MemoryStack.stackPush()) {
                FloatBuffer buffer = stack.mallocFloat(4);
                value.get(buffer);
                glUniform4fv(getUniformLocation(name), buffer);
            }
        });
    }

    public void setUniform(String name, Matrix2f value) {
        CommandBuffer.shared.addCommand(() -> {
            try (MemoryStack stack = MemoryStack.stackPush()) {
                FloatBuffer buffer = stack.mallocFloat(2 * 2);
                value.get(buffer);
                glUniformMatrix2fv(getUniformLocation(name), false, buffer);
            }
        });
    }

    public void setUniform(String name, Matrix3f value) {
        CommandBuffer.shared.addCommand(() -> {
            try (MemoryStack stack = MemoryStack.stackPush()) {
                FloatBuffer buffer = stack.mallocFloat(3 * 3);
                value.get(buffer);
                glUniformMatrix3fv(getUniformLocation(name), false, buffer);
            }
        });
    }

    public void setUniform(String name, Matrix4f value) {
        CommandBuffer.shared.addCommand(() -> {
            try (MemoryStack stack = MemoryStack.stackPush()) {
                FloatBuffer buffer = stack.mallocFloat(4 * 4);
                value.get(buffer);
                glUniformMatrix4fv(getUniformLocation(name), false, buffer);
            }
        });
    }

    private int getUniformLocation(String name) {
        if(uniformLocationCache.containsKey(name)) {
            return uniformLocationCache.get(name);
        }
        int location = glGetUniformLocation(programId, name);
        if(location == -1) {
            throw new RuntimeException("Uniform " + name + " not found");
        }
        uniformLocationCache.put(name, location);
        return location;
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
