package ru.itis.car_parking.repositories.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import ru.itis.car_parking.model.User;
import ru.itis.car_parking.repositories.UsersRepository;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UsersRepositoryImpl implements UsersRepository {

    private final JdbcTemplate jdbcTemplate;
    private final static String SQL_SELECT_ALL = "select * from users;";
    private final static String SQL_INSERT = "insert into users (first_name, last_name, password, email, birthdate) VALUES (?, ?, ?, ?, ?);";
    private final static String SQL_SELECT_BY_ID = "select * from users where id = ?;";
    private final static String SQL_SELECT_BY_EMAIL = "select * from users where email = ?";

    private final RowMapper<User> rowMapper = (row, rowNumber) -> User.builder()
            .id((UUID) row.getObject("id"))
            .fistName(row.getString("first_name"))
            .lastName(row.getString("last_name"))
            .email(row.getString("email"))
            .passwordHash(row.getString("password"))
            .birthdate(Instant.now())
            .build();

    public UsersRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findById(Integer id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, rowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_EMAIL, rowMapper, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public User save(User item) {
        if (item.getId() == null) {
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, item.getFistName());
                statement.setString(2, item.getLastName());
                statement.setString(3, item.getPasswordHash());
                statement.setString(4, item.getEmail());
                Instant birthdate = item.getBirthdate();
                statement.setDate(5, new Date(birthdate.getEpochSecond()));
                return statement;
            }, keyHolder);
            UUID id = (UUID) keyHolder.getKeys().get("id");
            item.setId(id);
            return item;
        } else {
            // TODO: - реализовать обновление
            return null;
        }
    }
    // TODO: - реализовать
    @Override
    public void delete(Integer id) {}
}
