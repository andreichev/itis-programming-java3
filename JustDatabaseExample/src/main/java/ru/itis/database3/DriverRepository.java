package ru.itis.database3;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class DriverRepository implements CrudRepository<Driver, Long> {

    private final JdbcTemplate jdbcTemplate;
    private final static String SQL_SELECT_ALL = "select * from driver;";
    private final static String SQL_INSERT = "insert into driver (first_name, last_name, age) VALUES (?, ?, ?);";
    private final static String SQL_SELECT_BY_ID = "select * from driver where id = ?;";

    private final RowMapper<Driver> driverRowMapper = (row, rowNumber) -> Driver.builder()
            .id(row.getLong("id"))
            .firstName(row.getString("first_name"))
            .lastName(row.getString("last_name"))
            .age(row.getInt("age"))
            .build();

    public DriverRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Optional<Driver> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, driverRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Driver> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, driverRowMapper);
    }

    @Override
    public Driver save(Driver item) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[] {"id"});
            statement.setString(1, item.getFirstName());
            statement.setString(2, item.getLastName());
            statement.setInt(3, item.getAge());
            return statement;
        }, keyHolder);
        item.setId(keyHolder.getKey().longValue());
        return item;
    }

    // TODO: - реализовать
    @Override
    public void update(Long id, Driver item) {}

    // TODO: - реализовать
    @Override
    public void delete(Long id) {}
}
