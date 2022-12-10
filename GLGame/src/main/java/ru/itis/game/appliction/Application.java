package ru.itis.game.appliction;

import ru.itis.game.base.GSize;
import ru.itis.game.events.Events;
import ru.itis.game.events.Key;
import ru.itis.game.events.impl.EventsGlfwImpl;
import ru.itis.game.renderer.Renderer;
import ru.itis.game.window.Window;
import ru.itis.game.window.impl.WindowGlfwImpl;

public final class Application {
    public static final Application shared = new Application();
    public int fps;

    // Время со старта программы
    private double time;
    // Таймер до 1 секудны для подсчета FPS
    private float oneSecondTimeCount;
    private int thisSecondFramesCount;
    // Ограничение по FPS
    private int maximumFps;
    // Время после отрисовки предыдущего кадра
    private float deltaTime;

    private Window window;
    private Events events;
    private Renderer renderer;

    private Application() {}

    public void run() {
        initialize();
        loop();
        terminate();
    }

    private void initialize() {
        events = new EventsGlfwImpl();
        window = new WindowGlfwImpl();
        events.setWindow(window);
        window.setEvents(events);
        // Порядок важен
        window.initialize("ITIS", new GSize(620, 480), false);
        events.initialize();
        renderer = new Renderer();
        renderer.initialize();
        time = window.getTime();
        maximumFps = 1000;
    }

    private void terminate() {
        window.terminate();
    }

    private void loop() {
        while (window.isShouldClose() == false) {
            double lastTime = time;
            time = window.getTime();
            deltaTime += (float) time - (float) lastTime;
            if(deltaTime - (1.f / maximumFps) < 0) {
                continue;
            }
            oneSecondTimeCount += deltaTime;

            thisSecondFramesCount++;
            if (oneSecondTimeCount > 1f) {
                fps = thisSecondFramesCount;
                System.out.println("FPS: " + fps);
                thisSecondFramesCount = 0;
                oneSecondTimeCount -= 1.f;
            }

            if(events.isKeyPressed(Key.ESCAPE)) {
                window.setShouldClose(true);
            }
            if(events.isKeyJustPressed(Key.TAB)) {
                events.toggleCursorLock();
            }

            renderer.update();

            deltaTime = 0.f;
            window.swapBuffers();
            events.pollEvents();
        }
    }
}
