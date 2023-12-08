package ru.itis.car_parking.services;

import ru.itis.car_parking.dto.SignInForm;
import ru.itis.car_parking.dto.SignUpForm;
import ru.itis.car_parking.dto.UserDto;
import ru.itis.car_parking.model.User;

public interface UserMapper {
    UserDto toDto(User user);
    User toUser(UserDto dto);
    User toUser(SignUpForm dto);
}
