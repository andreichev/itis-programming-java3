package ru.itis.gengine.gamelogic;

import ru.itis.game.network.NetworkEventType;
import ru.itis.gengine.application.Application;
import ru.itis.gengine.events.Events;
import ru.itis.gengine.gamelogic.ui.UINode;
import ru.itis.gengine.network.server.NetworkEvent;
import ru.itis.gengine.renderer.Renderer;
import ru.itis.gengine.window.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class World {
    private final Entity root;
    private final UINode uiRoot;
    private final Window window;
    private final Events events;
    private final Renderer renderer;
    private final Physics physics;
    private final List<NetworkEvent> networkEvents;

    // MARK: - Init

    public World(Window window, Events events, Renderer renderer, Physics physics) {
        this.window = window;
        this.events = events;
        this.renderer = renderer;
        this.physics = physics;
        this.networkEvents = new ArrayList<>();
        uiRoot = new UINode();
        uiRoot.configure(renderer, window, events);
        root = new Entity("root", window, events, renderer, physics);
    }

    // MARK: - Public methods

    public void initialize() {
        root.initialize();
    }

    public void terminate() {
        uiRoot.terminate();
        root.terminate();
    }

    public void update(float deltaTime) {
        root.update(deltaTime);
        uiRoot.render();
    }

    public Optional<Entity> findEntityByName(String name) {
        return root.getChildEntities().stream()
                .filter(item -> item.getName().equals(name))
                .findAny();
    }

    public void addRootUIElement(UINode node) {
        uiRoot.addSubnode(node);
    }

    public void removeRootUIElement(UINode node) {
        uiRoot.removeSubnode(node);
    }

    public Entity instantiateEntity(String name) {
        Entity entity = new Entity(name, window, events, renderer, physics);
        root.addChildEntity(entity);
        return entity;
    }

    public void destroy(Entity entity) {
        entity.terminate();
        root.removeEntity(entity);
    }

    public Window getWindow() {
        return window;
    }

    public Events getEvents() {
        return events;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    private boolean firstPlayerConnection = true;
    // Событие с сервера (обновить состояние объекта)
    public void processEventFromServer(NetworkEvent event) {
        if(event.type == NetworkEventType.PLAYER_CONNECTED.value) {
            LevelBase level = Application.shared.getCurrentLevel();
            if(level != null) {
                level.playerConnected(event.objectId, event.data[0], event.data[1], firstPlayerConnection);
                firstPlayerConnection = false;
            }
            return;
        }
        root.processEventFromServer(event);
    }

    // Событие, произошедшее со стороны клиента
    public synchronized void addEventToQueue(NetworkEvent event) {
        for(NetworkEvent queueEvent: networkEvents) {
            if(queueEvent.type == event.type && queueEvent.objectId == event.objectId) {
                queueEvent.data = event.data;
                return;
            }
        }
        networkEvents.add(event);
    }

    public synchronized List<NetworkEvent> pollEvents() {
        List<NetworkEvent> list = new ArrayList<>(networkEvents);
        networkEvents.clear();
        return list;
    }
}
