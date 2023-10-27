package ru.itis.car_parking.services.impl;

import lombok.AllArgsConstructor;
import ru.itis.car_parking.dto.SignUpForm;
import ru.itis.car_parking.exceptions.ParkingException;
import ru.itis.car_parking.model.User;
import ru.itis.car_parking.repositories.UsersRepository;
import ru.itis.car_parking.services.AuthorizationService;

import java.util.Optional;

@AllArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {
    private UsersRepository usersRepository;

    @Override
    public User signUp(SignUpForm form) throws ParkingException {
        if(form.getEmail() == null) {
            throw new ParkingException("Email cannot be null");
        }
        Optional<User> optionalUser = usersRepository.findByEmail(form.getEmail());
        if(optionalUser.isPresent()) {
            throw new ParkingException("User with email " + form.getEmail() + " already exist");
        }
        User user = User.builder()
                .fistName(form.getFistName())
                .lastName(form.getLastName())
                .email(form.getEmail())
                .password(form.getPassword())
                .birthdate(form.getBirthdate())
                .build();
        return usersRepository.save(user);
    }
}
