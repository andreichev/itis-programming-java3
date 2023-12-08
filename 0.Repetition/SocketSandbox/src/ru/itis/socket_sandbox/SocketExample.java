package ru.itis.socket_sandbox;

public class SocketExample {
    public static void main(String[] args) {
        if (args[0].equals("server")) {
            new Server();
        } else {
            new Client();
        }
    }
}
