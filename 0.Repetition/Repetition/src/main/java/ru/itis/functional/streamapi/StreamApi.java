package ru.itis.functional.streamapi;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamApi {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("HELLO");
        list.add("1 ST");
        list.add("SEPTEMBER");
        list.add("REPETITION");
        list.add("ITIS");

        List<String> processed = list.stream()
                .filter((String item) -> item.length() >= 5)
                .map((String item) -> "STR: " + item)
                .toList();

        for (String line : processed) {
            System.out.println(line);
        }
    }
}
