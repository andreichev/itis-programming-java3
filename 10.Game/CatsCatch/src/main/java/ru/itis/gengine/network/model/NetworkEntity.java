package ru.itis.gengine.network.model;

import java.util.List;

public class NetworkEntity implements NetworkPacketData {
    private final int id;
    private final List<NetworkComponent> components;

    public NetworkEntity(int id, List<NetworkComponent> components) {
        this.id = id;
        this.components = components;
    }

    public int getId() {
        return id;
    }

    public synchronized void add(NetworkComponent component) {
        components.add(component);
    }

    public synchronized void remove(NetworkComponent component) {
        components.remove(component);
    }

    public List<NetworkComponent> getComponents() {
        return components;
    }

    public synchronized void applyToComponent(NetworkPacket packet) {
        NetworkComponent incomingComponent;
        if (packet.data instanceof NetworkComponent) {
            incomingComponent = (NetworkComponent) packet.data;
        } else {
            throw new RuntimeException("COMPONENT_STATE пакет должен содержать NetworkComponent в data");
        }
        for (NetworkComponent component : components) {
            if (component.getId() == incomingComponent.getId()) {
                component.setState(incomingComponent.getState());
                break;
            }
        }
    }

    public synchronized void apply(NetworkEntity entity) {
        for(NetworkComponent incomingComponent: entity.components) {
            for (NetworkComponent existingComponent : components) {
                if (existingComponent.getId() == incomingComponent.getId()) {
                    existingComponent.setState(incomingComponent.getState());
                    break;
                }
            }
        }
    }
}
