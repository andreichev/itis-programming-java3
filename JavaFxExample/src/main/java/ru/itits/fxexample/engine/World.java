package ru.itits.fxexample.engine;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import ru.itits.fxexample.events.Events;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class World {
    private final List<Entity> entities;
    private final List<Event> events;
    private final Events inputEvents;
    private Pane pane;

    public World(Events inputEvents) {
        this.entities = new ArrayList<>();
        this.events = new ArrayList<>();
        this.inputEvents = inputEvents;
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
    public void updateEntity(Event event) {
        Optional<Entity> optionalEntity = entities.stream()
                .filter(item -> item.id == event.objectId)
                .findAny();
        if(optionalEntity.isPresent() == false) { return; }
        Entity entity = optionalEntity.get();

        if(entity instanceof Replicable) {
            ((Replicable)entity).updateState(event);
        }
    }

    public void addEntity(Entity entity) {
        Platform.runLater(() -> {
            pane.getChildren().add(entity);
            entities.add(entity);
        });
    }

    // Событие, произошедшее со стороны клиента
    public void addEvent(Event event) {
        events.add(event);
    }

    public List<Event> pollEvents() {
        List<Event> list = new ArrayList<>(events);
        events.clear();
        return list;
    }

    public void removeEntity(Entity entity) {
        Platform.runLater(() -> {
            pane.getChildren().remove(entity);
            entities.remove(entity);
        });
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
