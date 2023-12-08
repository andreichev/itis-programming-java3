package ru.itis.car_parking.exceptions;

public class ParkingException extends RuntimeException {
    public ParkingException(String message) {
        super(message);
    }
}
