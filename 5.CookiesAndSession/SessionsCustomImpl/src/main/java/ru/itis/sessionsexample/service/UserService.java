package ru.itis.sessionsexample.service;

import ru.itis.sessionsexample.model.User;
import ru.itis.sessionsexample.repository.UserRepository;

import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signIn(String username, String password) {
        Optional<User> optionalUser = userRepository.findByName(username);
        if(optionalUser.isPresent() == false) {
            throw new RuntimeException("User not found");
        }
        User user = optionalUser.get();
        if(password.equals(user.getPassword()) == false) {
            throw new RuntimeException("Password is incorrect");
        }
        return user;
    }
}
