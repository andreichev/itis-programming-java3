package ru.itits.fxexample.engine;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import ru.itits.fxexample.engine.network.NetworkEvent;
import ru.itits.fxexample.game.network.NetworkEventType;
import ru.itits.fxexample.input.Input;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class World {
    private final List<Entity> entities;
    private final List<NetworkEvent> eventsQueue;
    private Level currentLevel;
    private final Input input;
    private Pane pane;
    private boolean firstPlayerConnection = true;

    public World(Input input) {
        this.entities = new ArrayList<>();
        this.eventsQueue = new ArrayList<>();
        this.input = input;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public void update(float deltaTime) {
        for(Entity entity: entities) {
            entity.update(deltaTime);
        }
    }

    // Событие с сервера (обновить состояние объекта)
    public void processEventFromServer(NetworkEvent event) {
        if(event.type == NetworkEventType.PLAYER_CONNECTED.value) {
            if(currentLevel != null) {
                currentLevel.playerConnected(event.objectId, event.data[0], event.data[1], firstPlayerConnection);
                firstPlayerConnection = false;
            }
            return;
        }

        Optional<Entity> optionalEntity = entities.stream()
                .filter(item -> item.id == event.objectId)
                .findAny();
        if(optionalEntity.isPresent() == false) { return; }
        Entity entity = optionalEntity.get();

        if(entity instanceof Replicable) {
            ((Replicable) entity).updateState(event);
        }
    }

    public void addEntity(Entity entity) {
        Platform.runLater(() -> {
            pane.getChildren().add(entity);
            entities.add(entity);
        });
    }

    // Событие, произошедшее со стороны клиента
    public void addEventToQueue(NetworkEvent event) {
        eventsQueue.add(event);
    }

    public List<NetworkEvent> pollEvents() {
        List<NetworkEvent> list = new ArrayList<>(eventsQueue);
        eventsQueue.clear();
        return list;
    }

    public void removeEntity(Entity entity) {
        Platform.runLater(() -> {
            pane.getChildren().remove(entity);
            entities.remove(entity);
        });
    }

    public void setLevel(Level level) {
        currentLevel = level;
    }

    public void clear() {
        Platform.runLater(() -> {
            for(Entity entity: entities) {
                pane.getChildren().remove(entity);
                entities.remove(entity);

            }
        });
    }
}
