package com.itis.forms_servlet_example.dao;

import com.itis.forms_servlet_example.model.User;

import java.util.List;

// Create
// Read
// Update
// Delete
public interface UsersRepository {
    void save(User user);
    List<User> getAll();
    User getById(int id);
    void delete(int id);
}
