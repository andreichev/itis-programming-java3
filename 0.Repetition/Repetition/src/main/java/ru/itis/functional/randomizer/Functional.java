package ru.itis.functional.randomizer;

public class Functional {
    public static void main(String[] args) {
        Randomizer object = (int seed) -> Math.sin(seed * 43425450);

        System.out.println(object.getValue(1));
        System.out.println(object.getValue(2));
        System.out.println(object.getValue(3));
    }
}
