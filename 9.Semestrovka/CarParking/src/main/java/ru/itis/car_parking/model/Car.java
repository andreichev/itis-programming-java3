package ru.itis.car_parking.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Car {
    private String carNumber;
    private UUID userId;
}
