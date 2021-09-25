package itis.dependencyinjection.example2;

public class Car implements ReachType {
    @Override
    public void driveToSchool() {
        System.out.println("Выйди из дома");
        System.out.println("Завести автомобиль");
        System.out.println("Доехать до школы");
    }
}
