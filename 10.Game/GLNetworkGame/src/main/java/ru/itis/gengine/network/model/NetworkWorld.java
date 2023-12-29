package ru.itis.gengine.network.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NetworkWorld implements NetworkPacketData {
    private List<NetworkEntity> entities;

    public NetworkWorld() {
        entities = new ArrayList<>();
    }

    synchronized public void add(NetworkEntity entity) {
        entities.add(entity);
    }

    synchronized public void remove(NetworkEntity entity) {
        entities.remove(entity);
    }

    synchronized public void applyToComponent(NetworkPacket packet) {
        for (int i = 0; i < entities.size(); i++) {
            if(entities.get(i).getId() == packet.objectId) {
                entities.get(i).applyToComponent(packet);
                break;
            }
        }
    }

    synchronized public void applyToEntity(NetworkPacket packet) {
        if (!(packet.data instanceof NetworkEntity)) {
            throw new RuntimeException("ENTITY_STATE пакет должен содержать NetworkEntity в data");
        }
        NetworkEntity state = (NetworkEntity) packet.data;
        Optional<NetworkEntity> optional = entities.stream()
                .filter(item -> item.getId() == packet.objectId)
                .findAny();
        if(optional.isEmpty()) {
            entities.add(state);
            return;
        }
        NetworkEntity entity = optional.get();
        entity.apply(state);
    }

    synchronized public List<NetworkEntity> getEntities() {
        return entities;
    }

    synchronized public void removeEntity(int id) {
        entities.removeIf(item -> item.getId() == id);
    }

    synchronized public void apply(NetworkPacket event) {
        if (!(event.data instanceof NetworkWorld)) {
            throw new RuntimeException("WORLD_SNAPSHOT должен содержать NetworkWorld в data");
        }
        entities = ((NetworkWorld) event.data).entities;
    }

    synchronized public boolean isEmpty() {
        return entities.isEmpty();
    }
}
