package com.itis.forms_servlet_example.dao.impl;

import com.itis.forms_servlet_example.dao.UsersRepository;
import com.itis.forms_servlet_example.model.User;

import java.util.ArrayList;
import java.util.List;

public class UsersRepositoryTempImpl implements UsersRepository {
    private final List<User> data = new ArrayList<>();
    private int incrementValue = 0;

    @Override
    public void save(User user) {
        if (user.getId() != null) {
            data.removeIf(item -> user.getId().equals(item.getId()));
        } else {
            user.setId(incrementValue++);
        }
        data.add(user);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(data);
    }

    @Override
    public User getById(int id) {
        return data.stream().filter(item -> item.getId() == id).findFirst().get();
    }

    @Override
    public void delete(int id) {
        data.removeIf(item -> item.getId() == id);
    }
}
