package ru.itis.sessionsexample.repository;

import ru.itis.sessionsexample.model.User;

import java.util.Optional;

public class UserRepository {
    public Optional<User> findByName(String name) {
        return Optional.of(User.builder()
                .name(name)
                .password("123123")
                .email("qwe@mail.ru")
                .build()
        );
    }
}
