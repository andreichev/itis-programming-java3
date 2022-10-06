package com.itis.forms_servlet_example.context;

import com.itis.forms_servlet_example.dao.UsersRepository;
import com.itis.forms_servlet_example.dao.impl.UsersRepositoryFileImpl;
import com.itis.forms_servlet_example.dao.impl.UsersRepositoryTempImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContext implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        UsersRepository usersRepository = new UsersRepositoryTempImpl();
        context.setAttribute("usersRepository", usersRepository);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {}
}
