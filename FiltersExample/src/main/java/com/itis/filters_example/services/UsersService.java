package com.itis.filters_example.services;

import com.itis.filters_example.dto.RegistrationForm;
import com.itis.filters_example.dto.SignInForm;
import com.itis.filters_example.dto.UserDto;

import java.util.List;

public interface UsersService {
    UserDto register(RegistrationForm user);
    UserDto getProfile(Integer id);
    UserDto signIn(SignInForm form);
    List<UserDto> getAll();
}
