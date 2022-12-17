package ru.itis.gengine.application;

import ru.itis.gengine.base.GSize;
import ru.itis.gengine.events.Events;
import ru.itis.gengine.events.FrameBufferSizeListener;
import ru.itis.gengine.events.Key;
import ru.itis.gengine.events.impl.EventsGlfwImpl;
import ru.itis.gengine.gamelogic.LevelBase;
import ru.itis.gengine.gamelogic.World;
import ru.itis.gengine.renderer.Renderer;
import ru.itis.gengine.window.Window;
import ru.itis.gengine.window.impl.WindowGlfwImpl;

public class Application implements FrameBufferSizeListener {
    public static final Application shared = new Application();
    public int fps;

    private Window window;
    private Events events;
    private Renderer renderer;
    private World world;
    private LevelBase currentLevel;
    private double time;
    // Таймер до 1 секудны для подсчета FPS
    private float oneSecondTimeCount;
    private int thisSecondFramesCount;
    // Ограничение по FPS
    private int maximumFps;
    // Время после отрисовки предыдущего кадра
    private float deltaTime;

    public void run(ApplicationStartupSettings applicationStartupSettings) {
        initialize(applicationStartupSettings);
        loop();
        terminate();
    }

    public void loadLevel(LevelBase level) {
        if(currentLevel != null) {
            currentLevel.terminate();
        }
        currentLevel = level;
        currentLevel.start(world);
    }

    private Application() {}

    private void initialize(ApplicationStartupSettings settings) {
        maximumFps = 60;
        renderer = new Renderer();
        events = new EventsGlfwImpl();
        window = new WindowGlfwImpl();
        events.setWindow(window);
        window.setEvents(events);
        // Порядок важен
        window.initialize(settings.getWindowTitle(), settings.getWindowSize(), settings.isFullScreen());
        events.initialize();
        renderer.initialize();
        world = new World(window, events, renderer);
        time = window.getTime();
        events.addFrameBufferSizeListener(this);
        settings.getStartupLevel().start(world);
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
                // System.out.println("FPS: " + fps);
                thisSecondFramesCount = 0;
                oneSecondTimeCount -= 1.f;
            }

            renderer.clear();
            if(events.isKeyPressed(Key.ESCAPE)) {
                window.setShouldClose(true);
            }
            if(events.isKeyJustPressed(Key.TAB)) {
                events.toggleCursorLock();
            }

            world.update(deltaTime);
            deltaTime = 0.f;
            renderer.checkForErrors();
            window.swapBuffers();
            events.pollEvents();
        }
    }

    private void terminate() {
        if(currentLevel != null) {
            currentLevel.terminate();
        }
        world.terminate();
        window.terminate();
        renderer.terminate();
    }

    // MARK: - Frame buffer size listener

    @Override
    public void sizeChanged(GSize size) {
        renderer.setViewportSize(size);
    }
}
