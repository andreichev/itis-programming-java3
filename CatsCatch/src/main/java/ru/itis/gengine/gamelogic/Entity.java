package ru.itis.gengine.gamelogic;

import ru.itis.gengine.events.Events;
import ru.itis.gengine.gamelogic.components.Transform;
import ru.itis.gengine.network.client.Replicable;
import ru.itis.gengine.network.server.NetworkEvent;
import ru.itis.gengine.renderer.Renderer;
import ru.itis.gengine.window.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Entity {
    private String name;
    private final List<Component> components;
    private final List<Entity> childEntities;
    private Entity parentEntity;
    private final Transform transform;
    private final Window window;
    private final Events events;
    private final Renderer renderer;
    private final Physics physics;

    // MARK: - Init

    Entity(String name, Window window, Events events, Renderer renderer, Physics physics) {
        this.name = name;
        this.window = window;
        this.events = events;
        this.renderer = renderer;
        this.physics = physics;
        components = new ArrayList<>();
        childEntities = new ArrayList<>();
        transform = new Transform();
        addComponent(transform);
    }

    // MARK: - Public methods

    public void initialize() {
        for (Entity entity : childEntities) {
            entity.initialize();
        }
        for (Component component : components) {
            if (component.isActive) {
                component.initialize();
            }
        }
    }

    public void update(float deltaTime) {
        // Обновить дочерние сущности
        for (Entity entity : childEntities) {
            entity.update(deltaTime);
        }
        // Обновить все компонеты у этой сущности
        for (Component component : components) {
            if (component.isActive) {
                component.update(deltaTime);
            }
        }
    }

    public void terminate() {
        for (Entity entity : childEntities) {
            entity.terminate();
        }
        for (Component component : components) {
            if (component.isActive) {
                component.terminate();
            }
        }
    }

    public Transform getTransform() {
        return transform;
    }

    public void addComponent(Component component) {
        component.setEntity(this);
        components.add(component);
        component.initialize();
    }

    public void removeComponent(Component component) {
        components.remove(component);
    }

    public <T extends Component> T getComponentWithType(Class<T> type) {
        for (Component component : components) {
            if (type.isInstance(component)) {
                return (T) component;
            }
        }
        throw new RuntimeException("Component with type " + type.getName() + "  not found");
    }

    public void addChildEntity(Entity entity) {
        entity.parentEntity = entity;
        childEntities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entity.parentEntity = null;
        childEntities.remove(entity);
    }

    public List<Entity> getChildEntities() {
        return childEntities;
    }

    public String getName() {
        return name;
    }

    public Entity getParent() {
        return parentEntity;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public Window getWindow() {
        return window;
    }

    public Events getEvents() {
        return events;
    }

    public Physics getPhysics() {
        return physics;
    }

    // Событие с сервера (обновить состояние объекта)
    public void processEventFromServer(NetworkEvent event) {
        for (Entity entity : childEntities) {
            entity.processEventFromServer(event);
        }
        Optional<Component> optionalComponent = components.stream()
                .filter(item -> item.id == event.objectId)
                .findAny();
        if(optionalComponent.isPresent() == false) { return; }
        Component component = optionalComponent.get();

        if(component instanceof Replicable) {
            ((Replicable) component).processEvent(event);
        }
    }
}
