package ru.itis.oop;

import java.util.Map;

public class GroupImpl implements StudentsGroup {
    private String name;
    private Map<String, Double> scores;

    public GroupImpl(String name, Map<String, Double> scores) {
        this.name = name;
        this.scores = scores;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int studentsCount() {
        return scores.size();
    }

    @Override
    public double getAverageScore() {
        double sum = 0;
        for (Map.Entry<String, Double> student : scores.entrySet()) {
            sum += student.getValue();
        }
        return sum / studentsCount();
    }
}
