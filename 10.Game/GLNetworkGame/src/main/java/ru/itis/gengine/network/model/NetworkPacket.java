package ru.itis.gengine.network.model;

import java.io.*;

public class NetworkPacket implements Serializable {
    public NetworkEventType type;
    // Если собиые пришло с сервера - id объекта, к которому применить событие
    // Если собиые отправляется на сервер - id объекта источника события
    public int objectId;
    public NetworkPacketData data;

    public NetworkPacket(NetworkEventType type, int objectId, NetworkPacketData data) {
        this.type = type;
        this.objectId = objectId;
        this.data = data;
    }

    public static NetworkPacket readEvent(InputStream inputStream) throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        NetworkPacket event = (NetworkPacket) objectInputStream.readObject();
        if(event.type == NetworkEventType.END) {
            return null;
        }
        return event;
    }

    public static void writeEvent(NetworkPacket event, OutputStream outputStream) throws Exception {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(event);
    }

    public static NetworkPacket worldSnapshot(NetworkWorld world) {
        return new NetworkPacket(NetworkEventType.WORLD_SNAPSHOT, 0, world);
    }
    public static NetworkPacket END = new NetworkPacket(NetworkEventType.END, 0, null);
    public static NetworkPacket INITIALIZE_WORLD_HOST = new NetworkPacket(NetworkEventType.INITIALIZE_WORLD_HOST, 0, null);
    public static NetworkPacket INITIALIZE_WORLD_CLIENT = new NetworkPacket(NetworkEventType.INITIALIZE_WORLD_CLIENT, 0, null);
    public static NetworkPacket destroyEntity(int id) {
        return new NetworkPacket(NetworkEventType.DESTROY_ENTITY, id, null);
    }
}
