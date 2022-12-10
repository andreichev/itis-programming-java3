package ru.itis.game.window;

import ru.itis.game.base.GSize;
import ru.itis.game.events.Events;

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
