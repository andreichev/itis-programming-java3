package itis.dependencyinjection.example3;

public class Taxi implements ReachType {
    @Override
    public void driveToSchool() {
        System.out.println("Выйди из дома");
        System.out.println("Заказать такси");
        System.out.println("Сесть в такси");
        System.out.println("Доехать до школы");
    }
}
