package com.itis.filters_example.dao;

import com.itis.filters_example.model.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User, Integer> {
    Optional<User> getByName(String name);
}
