package ru.itis.car_parking.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class SignUpForm {
    private String fistName;
    private String lastName;
    private String email;
    private String password;
    private Instant birthdate;
}
