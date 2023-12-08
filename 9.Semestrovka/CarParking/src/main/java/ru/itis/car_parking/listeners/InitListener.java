package ru.itis.car_parking.listeners;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.itis.car_parking.repositories.UsersRepository;
import ru.itis.car_parking.repositories.impl.UsersRepositoryImpl;
import ru.itis.car_parking.services.AuthorizationService;
import ru.itis.car_parking.services.PasswordEncoder;
import ru.itis.car_parking.services.UserMapper;
import ru.itis.car_parking.services.impl.AuthorizationServiceImpl;
import ru.itis.car_parking.services.impl.PasswordEncoderImpl;
import ru.itis.car_parking.services.impl.UserMapperImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class InitListener implements ServletContextListener {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "123123";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/parking";
    private static final String DB_DRIVER = "org.postgresql.Driver";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Postgresql Driver not found.");
        }

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setUrl(DB_URL);

        PasswordEncoder passwordEncoder = new PasswordEncoderImpl();
        UserMapper userMapper = new UserMapperImpl();
        UsersRepository usersRepository = new UsersRepositoryImpl(dataSource);
        AuthorizationService authorizationService = new AuthorizationServiceImpl(usersRepository, userMapper, passwordEncoder);

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("usersRepository", usersRepository);
        servletContext.setAttribute("authorizationService", authorizationService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}
