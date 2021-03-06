package com.itis.servletsexamplethird.listeners;

import com.itis.servletsexamplethird.repository.UsersRepository;
import com.itis.servletsexamplethird.repository.jdbciml.UsersDaoJdbcImpl;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class CustomContextListener implements ServletContextListener {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "123123";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/breakthrough";
    private static final String DB_DRIVER = "org.postgresql.Driver";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setUrl(DB_URL);

        UsersRepository usersRepository = new UsersDaoJdbcImpl(dataSource);

        servletContext.setAttribute("usersRepository", usersRepository);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) { }
}
