package ru.itis.database2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// DATA ACCESS OBJECT
public class UsersRepositoryDBImpl implements UsersRepository {

    private final Connection connection;

    private final static String SQL_SELECT_ALL = "select * from users;";
    private final static String SQL_INSERT = "insert into users (first_name, last_name, course_name, age) VALUES (?, ?, ?, ?);";
    private final static String SQL_UPDATE = "update users set first_name = ?, last_name = ?, course_name = ?, age = ? where id = ?;";
    private final static String SQL_SELECT_BY_ID = "select * from users where id = ?;";

    public UsersRepositoryDBImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<User> findById(Integer id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next() == false) {
                return Optional.empty();
            }

            User user = User.builder()
                    .id(resultSet.getInt("id"))
                    .firstName(resultSet.getString("first_name"))
                    .lastName(resultSet.getString("last_name"))
                    .courseName(resultSet.getString("course_name"))
                    .age(resultSet.getInt("age"))
                    .build();
            return Optional.of(user);
        } catch (SQLException throwable) {
            System.out.println("SQL Exception: " + throwable.getLocalizedMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> result = new ArrayList<>();
            while(resultSet.next()) {
                User user = User.builder()
                        .id(resultSet.getInt("id"))
                        .firstName(resultSet.getString("first_name"))
                        .lastName(resultSet.getString("last_name"))
                        .courseName(resultSet.getString("course_name"))
                        .age(resultSet.getInt("age"))
                        .build();
                result.add(user);
            }
            return result;
        } catch (SQLException throwable) {
            System.out.println("SQL Exception: " + throwable.getLocalizedMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public User save(User item) {
        if (item.getId() == null) {
            try {
                PreparedStatement statement = connection.prepareStatement(
                        SQL_INSERT, new String[]{"id"}
                );
                statement.setString(1, item.getFirstName());
                statement.setString(2, item.getLastName());
                statement.setString(3, item.getCourseName());
                statement.setInt(4, item.getAge());
                int affectedRows = statement.executeUpdate();
                System.out.println(affectedRows + " rows affected");
                ResultSet generatedKeys = statement.getGeneratedKeys();
                generatedKeys.next();
                item.setId(generatedKeys.getInt(1));
                return item;
            } catch (SQLException throwable) {
                System.out.println("SQL Exception: " + throwable.getLocalizedMessage());
            }
            return item;
        } else {
            try {
                PreparedStatement statement = connection.prepareStatement(
                        SQL_UPDATE
                );
                statement.setString(1, item.getFirstName());
                statement.setString(2, item.getLastName());
                statement.setString(3, item.getCourseName());
                statement.setInt(4, item.getAge());
                statement.setInt(5, item.getId());
                int affectedRows = statement.executeUpdate();
                System.out.println(affectedRows + " rows affected");
                return item;
            } catch (SQLException throwable) {
                System.out.println("SQL Exception: " + throwable.getLocalizedMessage());
            }
            return item;
        }
    }

    // TODO: - реализовать
    @Override
    public List<User> getByCourseName(String courseName) {
        return null;
    }

    // TODO: - реализовать
    @Override
    public void delete(Integer id) {}
}
