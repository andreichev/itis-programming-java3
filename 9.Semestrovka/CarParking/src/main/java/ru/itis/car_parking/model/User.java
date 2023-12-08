package ru.itis.car_parking.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class User {
    private UUID id;
    private String fistName;
    private String lastName;
    private String email;
    private String passwordHash;
    private Instant birthdate;
}
