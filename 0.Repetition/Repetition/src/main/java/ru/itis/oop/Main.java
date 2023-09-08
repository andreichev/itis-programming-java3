package ru.itis.oop;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, Double> students = new HashMap<>();
        students.put("Timur", 97.0);
        students.put("Rasim", 90.0);
        students.put("Tanya", 100.0);
        StudentsGroup group = new GroupImpl("11-203", students);
        School itis = new School();
        itis.printGroup(group);
    }
}