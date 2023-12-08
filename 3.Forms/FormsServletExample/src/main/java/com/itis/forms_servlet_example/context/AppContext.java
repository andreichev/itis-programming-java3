package com.itis.forms_servlet_example.context;

import com.itis.forms_servlet_example.dao.UsersRepository;
import com.itis.forms_servlet_example.dao.impl.UsersRepositoryFileImpl;

public class AppContext {
    public static UsersRepository usersRepository = new UsersRepositoryFileImpl();
}
