package com.itis.servletsexamplethird.repository.jdbciml;

import com.itis.servletsexamplethird.model.User;
import com.itis.servletsexamplethird.repository.UsersRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class UsersDaoJdbcImpl implements UsersRepository {

    private final JdbcTemplate jdbcTemplate;
    private final static String SQL_SELECT_ALL = "select * from users;";
    private final static String SQL_INSERT = "insert into users (first_name, last_name, age) VALUES (?, ?, ?);";
    private final static String SQL_SELECT_BY_ID = "select * from users where id = ?;";

    private final RowMapper<User> usersRowMapper = (row, rowNumber) -> User.builder()
            .id(row.getLong("id"))
            .firstName(row.getString("first_name"))
            .lastName(row.getString("last_name"))
            .age(row.getInt("age"))
            .build();

    public UsersDaoJdbcImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Optional<User> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, usersRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, usersRowMapper);
    }

    @Override
    public User save(User item) {
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
    public void update(Long id, User item) {}

    // TODO: - реализовать
    @Override
    public void delete(Long id) {}
}
