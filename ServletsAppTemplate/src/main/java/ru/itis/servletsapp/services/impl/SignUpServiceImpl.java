package ru.itis.servletsapp.services.impl;

import ru.itis.servletsapp.dao.UsersRepository;
import ru.itis.servletsapp.dto.UserForm;
import ru.itis.servletsapp.model.User;
import ru.itis.servletsapp.services.PasswordEncoder;
import ru.itis.servletsapp.services.SignUpService;

public class SignUpServiceImpl implements SignUpService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUpServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signUp(UserForm form) {
        // passwordEncoder.matches("123123", "HASH");
        User user = User.builder()
                .email(form.getEmail())
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .hashPassword(passwordEncoder.encode(form.getPassword()))
                .build();
        usersRepository.save(user);
    }
}
