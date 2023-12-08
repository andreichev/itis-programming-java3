package ru.itis.car_parking.services;

import ru.itis.car_parking.dto.SignInForm;
import ru.itis.car_parking.dto.SignUpForm;
import ru.itis.car_parking.dto.UserDto;
import ru.itis.car_parking.exceptions.ParkingException;

public interface AuthorizationService {
    UserDto signUp(SignUpForm form) throws ParkingException;
    UserDto signIn(SignInForm form) throws ParkingException;
}
