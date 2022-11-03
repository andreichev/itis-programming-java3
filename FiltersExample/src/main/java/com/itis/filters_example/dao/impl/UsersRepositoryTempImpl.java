package com.itis.filters_example.dao.impl;

import com.itis.filters_example.dao.UsersRepository;
import com.itis.filters_example.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryTempImpl implements UsersRepository {
    private final List<User> data = new ArrayList<>();
    private int incrementValue = 0;

    @Override
    public User save(User user) {
        if (user.getId() != null) {
            data.removeIf(item -> user.getId().equals(item.getId()));
        } else {
            user.setId(incrementValue++);
        }
        data.add(user);
        return user;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(data);
    }

    @Override
    public Optional<User> getById(Integer id) {
        return data.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    @Override
    public Optional<User> getByName(String name) {
        return Optional.empty();
    }

    @Override
    public void delete(Integer id) {
        data.removeIf(item -> item.getId().equals(id));
    }
}
