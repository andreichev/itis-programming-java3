package itis.dependencyinjection.example3;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class Car implements ReachType {
    @Override
    public void driveToSchool() {
        System.out.println("Выйди из дома");
        System.out.println("Завести автомобиль");
        System.out.println("Доехать до школы");
    }
}
