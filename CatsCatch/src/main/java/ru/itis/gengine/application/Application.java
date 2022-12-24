package ru.itis.gengine.application;

import ru.itis.gengine.base.GSize;
import ru.itis.gengine.events.Events;
import ru.itis.gengine.events.FrameBufferSizeListener;
import ru.itis.gengine.events.Key;
import ru.itis.gengine.events.impl.EventsGlfwImpl;
import ru.itis.gengine.gamelogic.LevelBase;
import ru.itis.gengine.gamelogic.Physics;
import ru.itis.gengine.gamelogic.World;
import ru.itis.gengine.network.client.Client;
import ru.itis.gengine.network.server.Server;
import ru.itis.gengine.renderer.Renderer;
import ru.itis.gengine.window.Window;
import ru.itis.gengine.window.impl.WindowGlfwImpl;

import java.io.IOException;
import java.net.Socket;

public class Application implements FrameBufferSizeListener {
    public static final Application shared = new Application();
    public int fps;

    private Window window;
    private Events events;
    private Renderer renderer;
    private Physics physics;
    private Server server;
    private Client client;
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

    public LevelBase getCurrentLevel() {
        return currentLevel;
    }

    public World getWorld() {
        return world;
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
        physics = new Physics();
        events = new EventsGlfwImpl();
        window = new WindowGlfwImpl();
        events.setWindow(window);
        window.setEvents(events);
        // Порядок важен
        window.initialize(settings.getWindowTitle(), settings.getWindowSize(), settings.isFullScreen());
        events.initialize();
        renderer.initialize();
        world = new World(window, events, renderer, physics);
        time = window.getTime();
        events.addFrameBufferSizeListener(this);
        if (settings.isServer()) {
            try {
                server = new Server();
            } catch (IOException e) {
                System.out.printf("SERVER FAILED TO START %s\n", e.getLocalizedMessage());
            }
        }
        try {
            Socket socket = new Socket("127.0.0.1", 16431);
            client = new Client(world, socket);
        } catch (IOException e) {
            System.out.printf("CLIENT FAILED TO CONNECT %s\n", e.getLocalizedMessage());
        }

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
            if(time == 300) {
                window.setShouldClose(true);
            }
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
