package ru.itis.gengine.gamelogic;

import ru.itis.gengine.events.Events;
import ru.itis.gengine.gamelogic.components.Transform;
import ru.itis.gengine.network.model.NetworkComponent;
import ru.itis.gengine.network.model.NetworkEntity;
import ru.itis.gengine.network.model.NetworkEventType;
import ru.itis.gengine.network.model.NetworkPacket;
import ru.itis.gengine.renderer.Renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Entity {
    private final int id;
    private final boolean isNetworkObject;
    private final String name;
    private final List<Component> components;
    private final Transform transform;
    private final Events events;
    private final Renderer renderer;
    private final Physics physics;
    private final World world;

    // MARK: - Init

    Entity(int id, boolean isNetworkObject, String name, World world, Events events, Renderer renderer, Physics physics) {
        this.id = id;
        this.isNetworkObject = isNetworkObject;
        this.name = name;
        this.world = world;
        this.events = events;
        this.renderer = renderer;
        this.physics = physics;
        components = new ArrayList<>();
        transform = new Transform();
        addComponent(transform);
    }

    // MARK: - Package private methods

    void initialize() {
        for (Component component : components) {
            if (component.isActive) {
                component.initialize();
            }
        }
    }

    void update(float deltaTime) {
        // Обновить все компонеты у этой сущности
        for (Component component : components) {
            if (component.isActive) {
                component.update(deltaTime);
            }
        }
    }

    public void terminate() {
        for (Component component : components) {
            if (component.isActive) {
                component.terminate();
            }
        }
    }

    public void applyEntity(NetworkEntity entity) {
        components
                .forEach(existingComponent -> {
            Optional<NetworkComponent> optional = entity.getComponents().stream()
                    .filter(component -> component.getId() == existingComponent.id)
                    .findAny();
            if(optional.isPresent()) {
                NetworkComponent incomeComponent = optional.get();
                existingComponent.setState(incomeComponent.getState());
            }
        });
    }

    void updateComponentState(NetworkPacket event) {
        NetworkComponent incomingComponent;
        if (event.data instanceof NetworkComponent) {
            incomingComponent = (NetworkComponent) event.data;
        } else {
            throw new RuntimeException("COMPONENT_STATE пакет должен содержать NetworkComponent в data");
        }
        Optional<Component> optional = components.stream()
                .filter(item -> item.id == incomingComponent.getId())
                .findAny();
        if(optional.isEmpty()) { return; }
        Component existingComponent = optional.get();
        existingComponent.setState(incomingComponent.getState());
    }

    // MARK: - Public methods

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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public World getWorld() {
        return world;
    }

    public Events getEvents() {
        return events;
    }

    public Physics getPhysics() {
        return physics;
    }

    public boolean isNetworkObject() {
        return isNetworkObject;
    }

    public NetworkEntity createSnapshot() {
        List<NetworkComponent> networkComponents = components.stream()
                .filter(item -> item.isNetwork)
                .map(item -> new NetworkComponent(item.id, item.getState()))
                .toList();
        return new NetworkEntity(id, networkComponents);
    }

    public void sendCurrentState() {
        if (!isNetworkObject) {
            throw new RuntimeException("Объект не является сетевым");
        }
        NetworkEntity state = createSnapshot();
        NetworkPacket packet = new NetworkPacket(NetworkEventType.ENTITY_STATE, getId(), state);
        getWorld().addEventToQueue(packet);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Entity) {
            Entity entity = (Entity) o;
            return id == entity.id;
        }
        if (o instanceof NetworkEntity) {
            NetworkEntity entity = (NetworkEntity) o;
            return id == entity.getId();
        }
        return false;
    }
}
