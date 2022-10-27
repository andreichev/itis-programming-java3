package com.itis.filters_example.dao.impl;

import com.itis.filters_example.dao.UsersRepository;
import com.itis.filters_example.model.User;

import java.util.List;
import java.util.Optional;

// TODO: Implement
public class UsersRepositoryFileImpl implements UsersRepository {
    @Override
    public User save(User user) { return null; }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public Optional<User> getById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void delete(Integer id) {}
}
