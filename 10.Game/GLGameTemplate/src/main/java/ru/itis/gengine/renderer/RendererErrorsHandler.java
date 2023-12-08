package ru.itis.gengine.renderer;

import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.glGetError;

class RendererErrorsHandler {
    static void checkForErrorsAndPrint() {
        int error = glGetError();
        if (error != GL_NO_ERROR) {
            System.out.println("GL ERROR: " + error);
        }
    }
}
