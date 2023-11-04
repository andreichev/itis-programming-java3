package ru.itis.car_parking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInForm {
    private String email;
    private String password;
}
