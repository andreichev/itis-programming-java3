package itis.dependencyinjection.example4;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean("gasBoiler")
    Boiler getBoiler() {
        return new GasBoiler();
    }
}
