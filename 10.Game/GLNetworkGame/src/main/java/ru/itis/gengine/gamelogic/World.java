package ru.itis.gengine.gamelogic;

import ru.itis.gengine.application.Application;
import ru.itis.gengine.events.Events;
import ru.itis.gengine.network.model.NetworkEntity;
import ru.itis.gengine.network.model.NetworkPacket;
import ru.itis.gengine.network.model.NetworkWorld;
import ru.itis.gengine.renderer.Renderer;

import java.util.*;

public class World {
    private final List<Entity> entities;
    private final Events events;
    private final Renderer renderer;
    private final Physics physics;
    // TODO: - Переместить в Client
    private final List<NetworkPacket> networkPackets;

    // MARK: - Init

    public World(Events events, Renderer renderer, Physics physics) {
        this.events = events;
        this.renderer = renderer;
        this.physics = physics;
        this.networkPackets = new ArrayList<>();
        entities = new ArrayList<>();
    }

    // MARK: - Public methods

    public void initialize() {
        for(int i = 0; i < entities.size(); i++) {
            entities.get(i).initialize();
        }
    }

    public void terminate() {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).terminate();
        }
    }

    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).update(deltaTime);
        }
    }

    public Optional<Entity> findEntityByName(String name) {
        return entities.stream()
                .filter(item -> item.getName().equals(name))
                .findAny();
    }

    public Optional<Entity> findEntityById(int id) {
        return entities.stream()
                .filter(item -> item.getId() == id)
                .findAny();
    }

    public Entity instantiateEntity(int id, boolean isNetwork, String name) {
        if(findEntityById(id).isPresent()) {
            throw new RuntimeException("Entity with id " + id + " already exists");
        }
        Entity entity = new Entity(id, isNetwork, name, this, events, renderer, physics);
        entities.add(entity);
        return entity;
    }

    public void destroy(Entity entity) {
        entity.terminate();
        entities.removeIf(item -> item.equals(entity));
    }

    public void destroy(int id) {
        for(int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if(entity.getId() == id) {
                entity.terminate();
                entities.removeIf(item -> item.equals(entity));
                return;
            }
        }
    }

    public Events getEvents() {
        return events;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    // Событие с сервера
    public void updateComponentState(NetworkPacket event) {
        for (Entity entity : entities) {
            if (entity.getId() == event.objectId) {
                entity.updateComponentState(event);
                break;
            }
        }
    }

    // Событие с сервера
    public void updateEntityState(NetworkPacket event) {
        if(!(event.data instanceof NetworkEntity)) {
            throw new RuntimeException("Неверный формат пакета");
        }
        for (Entity entity : entities) {
            if (entity.getId() == event.objectId) {
                entity.applyEntity((NetworkEntity) event.data);
                return;
            }
        }
        // Если объект не найден - его требуется создать
        Application.shared.getCurrentLevel().createEntityNetworkEvent((NetworkEntity) event.data);
    }

    // Событие, произошедшее со стороны клиента
    public synchronized void addEventToQueue(NetworkPacket event) {
        for(NetworkPacket queueEvent: networkPackets) {
            if(queueEvent.type == event.type && queueEvent.objectId == event.objectId) {
                queueEvent.data = event.data;
                return;
            }
        }
        networkPackets.add(event);
    }

    public synchronized List<NetworkPacket> pollEvents() {
        List<NetworkPacket> list = new ArrayList<>(networkPackets);
        networkPackets.clear();
        return list;
    }

    public NetworkWorld createSnapshot() {
        NetworkWorld world = new NetworkWorld();
        for(int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if(!entity.isNetworkObject()) {
                continue;
            }
            world.add(entity.createSnapshot());
        }
        return world;
    }

    public void applySnapshot(NetworkWorld world) {
        Set<Entity> entities = new HashSet<>(this.entities);
        Set<NetworkEntity> networkEntities = new HashSet<>(world.getEntities());

        // Добавить объекты, которые есть на сервере и нет на клиенте
        Set<NetworkEntity> entitiesToAdd = new HashSet<>(networkEntities);
        entitiesToAdd.removeAll(entities);
        entitiesToAdd.forEach(item -> Application.shared.getCurrentLevel().createEntityNetworkEvent(item));

        // Удалить объекты, которые есть на клиенте и их нет на сервере
        // Set<Entity> entitiesToDelete = new HashSet<>(entities);
        // entitiesToDelete.removeAll(networkEntities);
        // entitiesToDelete.forEach(this::destroy);

        // Обновить остальные
        Set<Entity> entitiesToUpdate = new HashSet<>(entities);
        Set<NetworkEntity> networkEntitiesToApply = new HashSet<>(networkEntities);
        networkEntities.retainAll(entitiesToUpdate);
        entitiesToUpdate.forEach(item -> {
            Optional<NetworkEntity> optional = networkEntitiesToApply.stream()
                    .filter(entity -> entity.getId() == item.getId())
                    .findAny();
            if(optional.isPresent()) {
                NetworkEntity entity = optional.get();
                item.applyEntity(entity);
            }
        });
    }
}
