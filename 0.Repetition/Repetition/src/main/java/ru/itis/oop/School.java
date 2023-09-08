package ru.itis.oop;

public class School {
    void printGroup(StudentsGroup group) {
        System.out.println("GROUP " + group.getName() + ", AVERAGE SCORE: " + group.getAverageScore() + ", STUDENTS COUNT: " + group.studentsCount());
    }
}
