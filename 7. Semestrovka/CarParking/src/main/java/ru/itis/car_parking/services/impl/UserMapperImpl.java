package ru.itis.car_parking.services.impl;

import ru.itis.car_parking.dto.SignUpForm;
import ru.itis.car_parking.dto.UserDto;
import ru.itis.car_parking.model.User;
import ru.itis.car_parking.services.UserMapper;

public class UserMapperImpl implements UserMapper {
    @Override
    public UserDto toDto(User user) {
        return UserDto.builder()
                .fistName(user.getFistName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .birthdate(user.getBirthdate())
                .build();
    }

    @Override
    public User toUser(UserDto dto) {
        return User.builder()
                .fistName(dto.getFistName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .birthdate(dto.getBirthdate())
                .build();
    }

    @Override
    public User toUser(SignUpForm dto) {
        return User.builder()
                .fistName(dto.getFistName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .birthdate(dto.getBirthdate())
                .passwordHash(dto.getPassword())
                .build();
    }
}
