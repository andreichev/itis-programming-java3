package ru.itis.gengine.events;

import ru.itis.gengine.window.Window;

public interface Events {
    void initialize();
    void setWindow(Window window);
    void pollEvents();

    boolean isKeyPressed(Key key);
    boolean isKeyJustPressed(Key key);
    boolean isMouseButtonClicked(MouseButton button);
    boolean isMouseButtonJustClicked(MouseButton button);
    boolean isCursorLocked();
    void toggleCursorLock();
    void addWindowSizeListener(WindowSizeListener listener);
    void removeWindowSizeDelegate(WindowSizeListener listener);
    void addFrameBufferSizeListener(FrameBufferSizeListener listener);
    void removeFrameBufferSizeListener(FrameBufferSizeListener listener);
    void resetCursorPos();
    float getDeltaX();
    float getDeltaY();
}
