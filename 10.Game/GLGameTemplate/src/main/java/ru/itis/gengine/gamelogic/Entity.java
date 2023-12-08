package ru.itis.gengine.gamelogic;

import ru.itis.gengine.events.Events;
import ru.itis.gengine.gamelogic.components.Transform;
import ru.itis.gengine.renderer.Renderer;
import ru.itis.gengine.window.Window;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    private final List<Component> components;
    private final List<Entity> childEntities;
    private Entity parentEntity;
    private final Transform transform;
    private final Events events;
    private final Renderer renderer;
    private final World world;

    // MARK: - Init

    Entity(Events events, Renderer renderer, World world) {
        this.events = events;
        this.renderer = renderer;
        this.world = world;
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
        for (int i = 0; i < childEntities.size(); i++) {
            Entity entity = childEntities.get(i);
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

    public Entity getParent() {
        return parentEntity;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public Events getEvents() {
        return events;
    }

    public World getWorld() { return world; }
}
