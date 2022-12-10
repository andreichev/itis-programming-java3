package ru.itis.gengine.window;

import ru.itis.gengine.base.GSize;
import ru.itis.gengine.events.Events;

public interface Window {
    void setEvents(Events events);
    void initialize(String title, GSize size, boolean isFullscreen);
    void terminate();

    double getTime();
    boolean isShouldClose();
    void setShouldClose(boolean flag);
    boolean isFullScreen();
    void setFullScreen(boolean isFullScreen);
    GSize getWindowSize();
    void swapBuffers();
    long getWindowHandle();
}
