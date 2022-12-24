package ru.itis.gengine.gamelogic;

import java.util.UUID;

public abstract class Component {
    public int id = UUID.randomUUID().hashCode();
    public boolean isActive = true;
    private Entity entity;

    void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        if(entity == null) {
            throw new RuntimeException("Инициализация компонента должна происходить в методе initialize(), а не в конструкторе");
        }
        return entity;
    }

    public void initialize() {}

    public void terminate() {}

    public void update(float deltaTime) {}
}
