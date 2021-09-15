package ru.itis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

// DATA ACCESS OBJECT
public class DriversDao implements CrudRepository<Driver, Long> {

    private Connection connection;

    public DriversDao(Connection connection) {
        this.connection = connection;
    }

    public Optional<Driver> findById(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from driver where id = ?;");
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
        } catch (SQLException throwables) {
            System.out.println("SQL Exception: " + throwables.getLocalizedMessage());
        }

        return Optional.empty();
    }

    // TODO: - реализовать
    @Override
    public List<Driver> findAll() {
        return null;
    }

    // TODO: - реализовать
    @Override
    public Optional<Driver> save(Driver item) {
        return Optional.empty();
    }

    // TODO: - реализовать
    @Override
    public Optional<Driver> update(Long id, Driver item) {
        return Optional.empty();
    }

    // TODO: - реализовать
    @Override
    public void delete(Long id) {}
}
