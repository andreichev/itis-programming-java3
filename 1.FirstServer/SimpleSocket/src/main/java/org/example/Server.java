package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(1234);
        System.out.println("СЕРВЕР ЗАПУЩЕН. ПОРТ: 1234");
        while (true) {
            Socket socket = server.accept();
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String message = reader.readLine();
            System.out.println("СООБЩЕНИЕ С КЛИЕНТА:");
            System.out.println(message);
            socket.close();
            System.out.println("СЕАНС ЗАКОНЧЕН");
        }
    }
}