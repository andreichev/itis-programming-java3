package ru.itis.servletsapp.services;

import ru.itis.servletsapp.dto.UserDto;
import ru.itis.servletsapp.dto.UserForm;

public interface SignInService {
    UserDto signIn(UserForm userForm);
    UserDto signIn(String token);
}
