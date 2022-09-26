package itis.dependencyinjection.example4;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class House {
    private final Boiler b;

    public House(Boiler b) {
        System.out.println("HOUSE INITIALIZED");
        this.b = b;
    }

    void func() {
        b.warm();
    }
}
