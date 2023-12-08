package ru.itis.gengine.renderer;

import org.lwjgl.opengl.GL;
import ru.itis.gengine.base.GSize;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_ALIASED_LINE_WIDTH_RANGE;

public class Renderer {
    float[] lineWidthRange = new float[2];

    public void initialize() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glEnable(GL_BLEND);
        glGetFloatv(GL_ALIASED_LINE_WIDTH_RANGE, lineWidthRange);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public void setClearColor(float r, float g, float b, float a) {
        glClearColor(r, g, b, a);
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
    }

    public void checkForErrors() {
        RendererErrorsHandler.checkForErrorsAndPrint();
    }

    public void draw(int elementsCount) {
        glDrawArrays(GL_TRIANGLES, 0, elementsCount);
    }

    public void drawIndexed(int indicesCount) {
        glDrawElements(GL_TRIANGLES, indicesCount, GL_UNSIGNED_INT, 0);
    }

    public void setViewportSize(GSize size) {
        glViewport(0, 0, (int) size.width, (int) size.height);
    }

    public void setLineWidth(float width) {
        if(width > lineWidthRange[1] || width < lineWidthRange[0]) {
            return;
        }
        glLineWidth(width);
    }

    public void drawLines(int pointsCount) {
        glDrawArrays(GL_LINES, 0, pointsCount);
    }

    public void terminate() {}
}
