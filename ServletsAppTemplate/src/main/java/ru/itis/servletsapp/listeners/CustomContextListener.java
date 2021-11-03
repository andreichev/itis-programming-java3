package ru.itis.servletsapp.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.itis.servletsapp.dao.FilesRepository;
import ru.itis.servletsapp.dao.PostsRepository;
import ru.itis.servletsapp.dao.UsersRepository;
import ru.itis.servletsapp.dao.impl.FilesRepositoryImpl;
import ru.itis.servletsapp.dao.impl.PostsRepositoryImpl;
import ru.itis.servletsapp.dao.impl.UsersRepositoryImpl;
import ru.itis.servletsapp.services.*;
import ru.itis.servletsapp.services.impl.*;
import ru.itis.servletsapp.services.validation.Validator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebListener
public class CustomContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String DB_USERNAME;
        String DB_PASSWORD;
        String DB_URL;
        String DB_DRIVER;
        String JWT_SECRET;
        String IMAGES_STORAGE_PATH;
        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Требуется файл properties");
        }
        DB_USERNAME = (String) properties.get("spring.datasource.username");
        DB_PASSWORD = (String) properties.get("spring.datasource.password");
        DB_URL = (String) properties.get("spring.datasource.url");
        DB_DRIVER = (String) properties.get("spring.datasource.driver-class-name");
        JWT_SECRET = (String) properties.get("jwt.secret");
        IMAGES_STORAGE_PATH = (String) properties.get("storage.images");

        ServletContext servletContext = servletContextEvent.getServletContext();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setUrl(DB_URL);

        FilesRepository filesRepository = new FilesRepositoryImpl(dataSource);
        UsersRepository usersRepository = new UsersRepositoryImpl(dataSource);
        FilesService filesService = new FilesServiceImpl(IMAGES_STORAGE_PATH, filesRepository, usersRepository);
        PasswordEncoder passwordEncoder = new PasswordEncoderImpl();
        SignInService signInService = new SignInServiceImpl(JWT_SECRET, usersRepository, passwordEncoder);
        Validator validator = new ValidatorImpl(usersRepository);
        SignUpService signUpService = new SignUpServiceImpl(usersRepository, passwordEncoder, validator);
        PostsRepository postsRepository = new PostsRepositoryImpl(dataSource);
        PostsService postsService = new PostsServiceImpl(postsRepository);
        ObjectMapper objectMapper = new ObjectMapper();

        servletContext.setAttribute("filesService", filesService);
        servletContext.setAttribute("signInService", signInService);
        servletContext.setAttribute("signUpService", signUpService);
        servletContext.setAttribute("postsService", postsService);
        servletContext.setAttribute("passwordEncoder", passwordEncoder);
        servletContext.setAttribute("objectMapper", objectMapper);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
