package ru.itits.fxexample.engine;

import javafx.scene.layout.Pane;
import ru.itits.fxexample.events.Events;

import java.util.ArrayList;
import java.util.List;

public class World {
    private final List<Entity> entities;
    private final Events events;
    private Pane pane;

    public World(Events events) {
        this.entities = new ArrayList<>();
        this.events = events;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public void update(float deltaTime) {
        for(Entity entity: entities) {
            entity.update(deltaTime);
        }
    }

    public void addEntity(Entity entity) {
        pane.getChildren().add(entity);
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        pane.getChildren().remove(entity);
        entities.remove(entity);
    }
}
