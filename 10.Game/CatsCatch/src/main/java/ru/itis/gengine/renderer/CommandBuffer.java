package ru.itis.gengine.renderer;

import java.util.Deque;
import java.util.LinkedList;

public class CommandBuffer {
    private CommandBuffer() {}

    public static CommandBuffer shared = new CommandBuffer();
    private final Deque<Command> buffer = new LinkedList<>();

    public synchronized void addCommand(Command command) {
        buffer.add(command);
    }

    public synchronized void executeAll() {
        while(!buffer.isEmpty()) {
            Command command = buffer.pollFirst();
            command.execute();
        }
    }
}
