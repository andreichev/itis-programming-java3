package ru.itis.gengine.network.model;

public class NetworkComponent implements NetworkPacketData {
    private final int id;
    private NetworkComponentState state;

    public NetworkComponent(int id, NetworkComponentState state) {
        this.id = id;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setState(NetworkComponentState state) {
        this.state = state;
    }

    public NetworkComponentState getState() {
        return state;
    }
}
