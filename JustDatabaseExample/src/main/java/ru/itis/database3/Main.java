package ru.itis.database3;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.Optional;

public class Main {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "123123";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/first_lesson_database";
    private static final String DB_DRIVER = "org.postgresql.Driver";

    public static void main(String[] args) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setUrl(DB_URL);

        UsersRepository usersDao = new UsersRepositoryDBImpl(dataSource);
        Optional<User> optionalUser = usersDao.findById(1);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            System.out.println("User found! Name: " + user.getFirstName());
        } else {
            System.out.println("No users found!");
        }

        User user =  User.builder()
                .firstName("Vladimir")
                .lastName("Nechaev")
                .courseName("IOT")
                .age(22)
                .build();

        User savedUser = usersDao.save(user);
        System.out.println("Created user with id = " + savedUser.getId());
    }
}
