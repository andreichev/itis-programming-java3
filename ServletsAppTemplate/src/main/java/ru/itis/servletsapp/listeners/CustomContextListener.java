package ru.itis.servletsapp.listeners;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.itis.servletsapp.dao.FilesRepository;
import ru.itis.servletsapp.dao.UsersRepository;
import ru.itis.servletsapp.dao.impl.FilesRepositoryImpl;
import ru.itis.servletsapp.dao.impl.UsersRepositoryImpl;
import ru.itis.servletsapp.services.FilesService;
import ru.itis.servletsapp.services.PasswordEncoder;
import ru.itis.servletsapp.services.SignInService;
import ru.itis.servletsapp.services.SignUpService;
import ru.itis.servletsapp.services.impl.*;
import ru.itis.servletsapp.services.validation.Validator;

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

        FilesRepository filesRepository = new FilesRepositoryImpl(dataSource);
        FilesService filesService = new FilesServiceImpl(filesRepository);
        UsersRepository usersRepository = new UsersRepositoryImpl(dataSource);
        PasswordEncoder passwordEncoder = new PasswordEncoderImpl();
        SignInService signInService = new SignInServiceImpl(usersRepository, passwordEncoder);
        Validator validator = new ValidatorImpl(usersRepository);
        SignUpService signUpService = new SignUpServiceImpl(usersRepository, passwordEncoder, validator);

        servletContext.setAttribute("filesRepository", filesRepository);
        servletContext.setAttribute("filesService", filesService);
        servletContext.setAttribute("signInService", signInService);
        servletContext.setAttribute("signUpService", signUpService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {}
}
