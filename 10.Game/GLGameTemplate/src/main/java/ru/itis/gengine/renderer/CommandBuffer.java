package ru.itis.gengine.renderer;

import java.util.LinkedList;
import java.util.Queue;

public class CommandBuffer {
    private CommandBuffer() {}

    public static CommandBuffer shared = new CommandBuffer();
    private final Queue<Command> buffer = new LinkedList<>();

    public synchronized void addCommand(Command command) {
        buffer.add(command);
    }

    public synchronized void executeAll() {
        while(!buffer.isEmpty()) {
            Command command = buffer.poll();
            command.execute();
        }
    }
}
