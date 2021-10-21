package ru.itis.servletsapp.services.impl;

import ru.itis.servletsapp.dao.UsersRepository;
import ru.itis.servletsapp.dto.UserDto;
import ru.itis.servletsapp.dto.UserForm;
import ru.itis.servletsapp.exceptions.ValidationException;
import ru.itis.servletsapp.model.User;
import ru.itis.servletsapp.services.PasswordEncoder;
import ru.itis.servletsapp.services.SignInService;
import ru.itis.servletsapp.services.validation.ErrorEntity;

public class SignInServiceImpl implements SignInService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public SignInServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto signIn(UserForm userForm) {
        User user = usersRepository.findByEmail(userForm.getEmail())
                .orElseThrow(() -> new ValidationException(ErrorEntity.NOT_FOUND));
        if (passwordEncoder.matches(userForm.getPassword(), user.getHashPassword()) == false) {
            throw new ValidationException(ErrorEntity.INCORRECT_PASSWORD);
        }
        return UserDto.from(user);
    }
}
