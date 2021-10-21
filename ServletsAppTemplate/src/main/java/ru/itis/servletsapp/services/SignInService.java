package ru.itis.servletsapp.services;

import ru.itis.servletsapp.dto.out.UserDto;
import ru.itis.servletsapp.dto.in.UserForm;

public interface SignInService {
    UserDto signIn(UserForm userForm);
}
