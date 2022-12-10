package ru.itis.socket_sandbox;

public class SocketExample {
    public static void main(String[] args) {
        if (args[0].equals("server")) {
            Server server = new Server();
        } else {
            Client client = new Client();
        }
    }
}
