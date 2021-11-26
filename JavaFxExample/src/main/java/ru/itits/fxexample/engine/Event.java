package ru.itits.fxexample.engine;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Event {
    public int type;
    // Если собиые пришло с сервера - id объекта, к которому применить событие
    // Если собиые отправляется на сервев - id объекта источника события
    public int objectId;
    public int[] data;

    public Event(int type, int objectId, int[] data) {
        this.type = type;
        this.objectId = objectId;
        this.data = data;
    }

    public static Event readEvent(DataInputStream dataInputStream) throws IOException {
        int type = dataInputStream.readInt();
        if(type == Event.END) { return null; }
        int objectId = dataInputStream.readInt();
        int[] buffer = new int[10];
        for(int i = 0; i < 10; i++) {
            buffer[i] = dataInputStream.readInt();
        }
        return new Event(type, objectId, buffer);
    }

    public static void writeMessage(Event event, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(event.type);
        dataOutputStream.writeInt(event.objectId);
        for(int i = 0; i < 10; i++) {
            dataOutputStream.writeInt(event.data[i]);
        }
    }

    public static int END = 999;
}
