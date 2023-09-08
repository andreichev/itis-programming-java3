package ru.itis.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Threads {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        Object o = new Object();
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName());
            for (int i = 0; i < 1000; i++) {
                synchronized (o) {
                    list.add(i);
                }
            }
            synchronized (o) {
                System.out.println("SIZE: " + list.size());
            }
        };
        for(int i = 0; i < 100; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
            // try {
            //     thread.join();
            // } catch (InterruptedException e) {
            //     throw new RuntimeException(e);
            // }
        }
        for (int item : list) {
            synchronized (o) {
                System.out.println(item);
            }
        }
    }
}
