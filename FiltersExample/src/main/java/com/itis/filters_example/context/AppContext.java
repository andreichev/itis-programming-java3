package com.itis.filters_example.context;

import com.itis.filters_example.dao.UsersRepository;
import com.itis.filters_example.dao.impl.UsersRepositoryDBImpl;
import com.itis.filters_example.services.HashService;
import com.itis.filters_example.services.UsersService;
import com.itis.filters_example.services.impl.HashServiceImpl;
import com.itis.filters_example.services.impl.UsersServiceImpl;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContext implements ServletContextListener {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "123123";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/first_lesson_database";
    private static final String DB_DRIVER = "org.postgresql.Driver";


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setUrl(DB_URL);

        UsersRepository usersRepository = new UsersRepositoryDBImpl(dataSource);

        HashService hashService = new HashServiceImpl();
        UsersService usersService = new UsersServiceImpl(usersRepository, hashService);
        context.setAttribute("usersService", usersService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {}
}
