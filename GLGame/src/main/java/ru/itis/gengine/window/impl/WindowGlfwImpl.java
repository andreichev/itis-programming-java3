package ru.itis.gengine.window.impl;

import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import ru.itis.gengine.base.GSize;
import ru.itis.gengine.events.Events;
import ru.itis.gengine.utils.OsCheck;
import ru.itis.gengine.utils.OsType;
import ru.itis.gengine.window.Window;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class WindowGlfwImpl implements Window {
    private long windowHandle;
    private GLFWErrorCallback errorCallback;
    private boolean isFullScreen;
    // Backup before going fullScreen
    private GSize windowSizeBackup;
    private Events events;

    @Override
    public void initialize(String title, GSize size, boolean isFullScreen) {
        this.isFullScreen = isFullScreen;
        this.windowSizeBackup = size;
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        errorCallback = GLFWErrorCallback.createPrint(System.out);
        glfwSetErrorCallback(errorCallback);

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (glfwInit() == false) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default

        if(OsCheck.getOperatingSystemType() == OsType.MacOS) {
            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        }

        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        windowHandle = glfwCreateWindow((int) size.width, (int) size.height, title, NULL, NULL);
        if (windowHandle == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Make the OpenGL context current
        glfwMakeContextCurrent(windowHandle);
        // Enable v-sync
        glfwSwapInterval(1);


        glfwShowWindow(windowHandle);
        glfwFocusWindow(windowHandle);

        if(isFullScreen) {
            setFullScreen(true);
        }
    }

    @Override
    public void setEvents(Events events) {
        this.events = events;
    }

    @Override
    public void terminate() {
        glfwFreeCallbacks(windowHandle);
        glfwDestroyWindow(windowHandle);
        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    @Override
    public double getTime() {
        // return System.nanoTime() / 1.0E9;
        return glfwGetTime();
    }

    @Override
    public boolean isShouldClose() {
        return glfwWindowShouldClose(windowHandle);
    }

    @Override
    public void setShouldClose(boolean flag) {
        glfwSetWindowShouldClose(windowHandle, flag);
    }

    @Override
    public void swapBuffers() {
        glfwSwapBuffers(windowHandle);
    }

    @Override
    public GSize getWindowSize() {
        IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
        IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
        glfwGetWindowSize(windowHandle, widthBuffer, heightBuffer);
        int width = widthBuffer.get(0);
        int height = heightBuffer.get(0);
        return new GSize((float) width, (float) height);
    }

    @Override
    public boolean isFullScreen() {
        return isFullScreen;
    }

    @Override
    public void setFullScreen(boolean fullScreen) {
        isFullScreen = fullScreen;
        // Get primary monitor handle
        long monitorHandle = glfwGetPrimaryMonitor();
        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(monitorHandle);
        events.resetCursorPos();
        if(isFullScreen) {
            // Backup window size
            windowSizeBackup = getWindowSize();
            glfwSetWindowMonitor(windowHandle, monitorHandle, 0, 0, vidmode.width(), vidmode.height(), 0);
        } else {
            glfwSetWindowMonitor(windowHandle, NULL, 0, 0, (int) windowSizeBackup.width, (int) windowSizeBackup.height, 0);
            // Center the window
            glfwSetWindowPos(
                    windowHandle,
                    (vidmode.width() - (int) windowSizeBackup.width) / 2,
                    (vidmode.height() - (int) windowSizeBackup.height) / 2
            );
        }
    }

    @Override
    public long getWindowHandle() {
        return windowHandle;
    }
}
