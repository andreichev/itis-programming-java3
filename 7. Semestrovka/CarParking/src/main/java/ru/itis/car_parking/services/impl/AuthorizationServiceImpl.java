package ru.itis.car_parking.services.impl;

import lombok.AllArgsConstructor;
import ru.itis.car_parking.dto.SignInForm;
import ru.itis.car_parking.dto.SignUpForm;
import ru.itis.car_parking.dto.UserDto;
import ru.itis.car_parking.exceptions.ParkingException;
import ru.itis.car_parking.model.User;
import ru.itis.car_parking.repositories.UsersRepository;
import ru.itis.car_parking.services.AuthorizationService;
import ru.itis.car_parking.services.PasswordEncoder;
import ru.itis.car_parking.services.UserMapper;
import java.util.Optional;

@AllArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {
    private UsersRepository usersRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto signUp(SignUpForm form) throws ParkingException {
        if (form.getEmail() == null) {
            throw new ParkingException("Email cannot be null");
        }
        Optional<User> optionalUser = usersRepository.findByEmail(form.getEmail());
        if (optionalUser.isPresent()) {
            throw new ParkingException("User with email " + form.getEmail() + " already exist");
        }
        form.setPassword(passwordEncoder.encode(form.getPassword()));
        User user = userMapper.toUser(form);
        User savedUser = usersRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDto signIn(SignInForm form) throws ParkingException {
        if(form.getEmail() == null) {
            throw new ParkingException("Email cannot be null");
        }
        Optional<User> optionalUser = usersRepository.findByEmail(form.getEmail());
        if(optionalUser.isEmpty()) {
            throw new ParkingException("User with email " + form.getEmail() + " not found.");
        }
        User user = optionalUser.get();
        if(!passwordEncoder.matches(form.getPassword(), user.getPasswordHash())) {
            throw new ParkingException("Wrong password");
        }
        return userMapper.toDto(user);
    }
}
