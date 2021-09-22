package ru.itis.database2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// DATA ACCESS OBJECT
public class DriverRepository implements CrudRepository<Driver, Long> {

    private final Connection connection;

    private final static String SQL_SELECT_ALL = "select * from driver;";
    private final static String SQL_INSERT = "insert into driver (first_name, last_name, age) VALUES (?, ?, ?);";
    private final static String SQL_SELECT_BY_ID = "select * from driver where id = ?;";

    public DriverRepository(Connection connection) {
        this.connection = connection;
    }

    public Optional<Driver> findById(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next() == false) {
                return Optional.empty();
            }

            Driver driver = Driver.builder()
                    .id(resultSet.getLong("id"))
                    .firstName(resultSet.getString("first_name"))
                    .lastName(resultSet.getString("last_name"))
                    .age(resultSet.getInt("age"))
                    .build();
            return Optional.of(driver);
        } catch (SQLException throwable) {
            System.out.println("SQL Exception: " + throwable.getLocalizedMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<Driver> findAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Driver> result = new ArrayList<>();
            while(resultSet.next()) {
                Driver driver = Driver.builder()
                        .id(resultSet.getLong("id"))
                        .firstName(resultSet.getString("first_name"))
                        .lastName(resultSet.getString("last_name"))
                        .age(resultSet.getInt("age"))
                        .build();
                result.add(driver);
            }
            return result;
        } catch (SQLException throwable) {
            System.out.println("SQL Exception: " + throwable.getLocalizedMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public Driver save(Driver item) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    SQL_INSERT,  new String[] {"id"}
            );
            statement.setString(1, item.getFirstName());
            statement.setString(2, item.getLastName());
            statement.setInt(3, item.getAge());
            int affectedRows = statement.executeUpdate();
            System.out.println(affectedRows + " rows affected");
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            item.setId(generatedKeys.getLong(1));
            return item;
        } catch (SQLException throwable) {
            System.out.println("SQL Exception: " + throwable.getLocalizedMessage());
        }
        return item;
    }

    // TODO: - реализовать
    @Override
    public void update(Long id, Driver item) {}

    // TODO: - реализовать
    @Override
    public void delete(Long id) {}
}
