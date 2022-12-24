package ru.itis.gengine.network.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class NetworkEvent {
    public int type;
    // Если собиые пришло с сервера - id объекта, к которому применить событие
    // Если собиые отправляется на сервев - id объекта источника события
    public int objectId;
    public double[] data;

    public NetworkEvent(int type, int objectId, double[] data) {
        this.type = type;
        this.objectId = objectId;
        this.data = data;
    }

    public static NetworkEvent readEvent(DataInputStream dataInputStream) throws IOException {
        int type = dataInputStream.readInt();
        if(type == NetworkEvent.END) { return null; }
        int objectId = dataInputStream.readInt();
        double[] buffer = new double[10];
        for(int i = 0; i < 10; i++) {
            buffer[i] = dataInputStream.readDouble();
        }
        return new NetworkEvent(type, objectId, buffer);
    }

    public static void writeEvent(NetworkEvent event, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(event.type);
        dataOutputStream.writeInt(event.objectId);
        for(int i = 0; i < 10; i++) {
            dataOutputStream.writeDouble(event.data[i]);
        }
    }

    public static int END = 999;
}
