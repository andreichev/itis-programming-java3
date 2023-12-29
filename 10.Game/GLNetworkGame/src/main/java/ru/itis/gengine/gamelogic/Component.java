package ru.itis.gengine.gamelogic;

import ru.itis.gengine.network.model.NetworkComponent;
import ru.itis.gengine.network.model.NetworkComponentState;
import ru.itis.gengine.network.model.NetworkEventType;
import ru.itis.gengine.network.model.NetworkPacket;

public abstract class Component {
    public int id;
    public boolean isActive = true;
    public final boolean isNetwork;
    private Entity entity;

    public Component(int id, boolean isNetwork) {
        this.id = id;
        this.isNetwork = isNetwork;
    }

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

    public NetworkComponentState getState() {
        throw new RuntimeException("Вызван метод sendCurrentState(), при этом getState не определен.");
    }

    public void setState(NetworkComponentState state) {}

    protected void sendCurrentState() {
        if(!isNetwork) {
            throw new RuntimeException("Компонент не отмечен как сетевой.");
        }
        NetworkComponentState state = getState();
        NetworkComponent component = new NetworkComponent(id, state);
        NetworkPacket packet = new NetworkPacket(NetworkEventType.COMPONENT_STATE, getEntity().getId(), component);
        getEntity().getWorld().addEventToQueue(packet);
    }
}
