package ru.itis.car_parking.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class UserDto {
    private UUID id;
    private String fistName;
    private String lastName;
    private String email;
    private Instant birthdate;
}
