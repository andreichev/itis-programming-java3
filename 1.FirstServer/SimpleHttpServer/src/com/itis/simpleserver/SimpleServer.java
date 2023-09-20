package com.itis.simpleserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SimpleServer {
    public static void main(String[] args) throws IOException {
        final int port = 8080;
        int x = 0;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Сервер запущен! порт: " + port);

        while (true) {
            // ожидаем подключения
            Socket socket = serverSocket.accept();
            System.out.println("Клиент присоединился!");

            // для подключившегося клиента открываем потоки
            // чтения и записи
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            PrintWriter output = new PrintWriter(socket.getOutputStream());

            // ждем первой строки запроса
            while (!input.ready()) ;

            // считываем и печатаем все что было отправлено клиентом
            System.out.println();
            while (input.ready()) {
                System.out.println(input.readLine());
            }

            String body = "HELLO " + x;
            x++;
            // отправляем ответ
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println("Content-Length: " + body.length());
            output.println();
            output.println(body);
            output.println();
            output.flush();

            output.close();
            input.close();
            socket.close();

            // по окончанию выполнения блока try-with-resources потоки,
            // а вместе с ними и соединение будут закрыты
            System.out.println("Клиент отсоединился!");
        }
    }
}
