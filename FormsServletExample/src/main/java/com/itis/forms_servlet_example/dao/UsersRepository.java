package com.itis.forms_servlet_example.dao;

import com.itis.forms_servlet_example.model.User;

import java.util.ArrayList;
import java.util.List;

public class UsersRepository {
    public static UsersRepository shared = new UsersRepository();
    public List<User> users = new ArrayList<>();
}
