package ru.itis.database2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Optional;

public class Main {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "123123";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/first_lesson_database";

    public static void main(String[] args) throws Exception {
        Connection connection =
                DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        UsersRepository usersDao = new UsersRepositoryDBImpl(connection);
        Optional<User> optionalUser = usersDao.findById(3);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            System.out.println("User found! Name: " + user.getFirstName());
        } else {
            System.out.println("No users found!");
        }

        User user = User.builder()
                .firstName("Lera")
                .lastName("Slobodchikova")
                .courseName("Frontend")
                .age(20)
                .build();

        User savedUser = usersDao.save(user);
        System.out.println("Created user with id = " + savedUser.getId());
    }
}
