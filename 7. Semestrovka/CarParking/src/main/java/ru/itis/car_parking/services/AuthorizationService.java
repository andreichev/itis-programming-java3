package ru.itis.car_parking.services;

import ru.itis.car_parking.dto.SignUpForm;
import ru.itis.car_parking.exceptions.ParkingException;
import ru.itis.car_parking.model.User;

public interface AuthorizationService {
    User signUp(SignUpForm form) throws ParkingException;
}
