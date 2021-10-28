package ru.itis.servletsapp.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.servletsapp.dao.PostsRepository;
import ru.itis.servletsapp.model.Post;
import ru.itis.servletsapp.model.User;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class PostsRepositoryImpl implements PostsRepository {

    private final static String SQL_INSERT = "insert into posts(author_id, content, created_at) " +
            "values (?, ?, ?)";
    private final static String SQL_UPDATE = "update posts set author_id = ?, content = ?, created_at = ? where id = ?";
    private final static String SQL_SELECT_BY_ID = "select posts.id as posts_id, author_id, created_at, content, users.id as users_id, first_name, last_name, age, password_hash, email, avatar_id from posts left join users on posts.author_id = users.id where posts.id = ?";
    private final static String SQL_SELECT_ALL = "select posts.id as posts_id, author_id, created_at, content, users.id as users_id, first_name, last_name, age, password_hash, email, avatar_id from posts left join users on posts.author_id = users.id order by created_at desc";
    private final static String SQL_SELECT_BY_AUTHOR_ID = "select posts.id as posts_id, author_id, created_at, content, users.id as users_id, first_name, last_name, age, password_hash, email, avatar_id from posts left join users on posts.author_id = users.id where users.id = ? order by created_at desc";

    private final RowMapper<Post> rowMapper = (row, rowNumber) ->Post.builder()
                .id(row.getLong("posts_id"))
                .author(
                        User.builder()
                                .id(row.getLong("users_id"))
                                .firstName(row.getString("first_name"))
                                .lastName(row.getString("last_name"))
                                .age(row.getInt("age"))
                                .hashPassword(row.getString("password_hash"))
                                .email(row.getString("email"))
                                .avatarId(row.getLong("avatar_id"))
                                .build()
                )
                .content(row.getString("content"))
                .createdAt(row.getTimestamp("created_at"))
                .build();

    private final JdbcTemplate jdbcTemplate;

    public PostsRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Post> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, rowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Post> findByAuthorId(Long authorId) {
        return jdbcTemplate.query(SQL_SELECT_BY_AUTHOR_ID, rowMapper, authorId);
    }

    @Override
    public List<Post> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }

    @Override
    public Post save(Post item) {
        if(item.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[]{"id"});
                statement.setLong(1, item.getAuthor().getId());
                statement.setString(2, item.getContent());
                statement.setTimestamp(3, item.getCreatedAt());
                return statement;
            }, keyHolder);
            if (keyHolder.getKey() != null) {
                item.setId(keyHolder.getKey().longValue());
            }
        } else {
            jdbcTemplate.update(SQL_UPDATE,
                    item.getAuthor().getId(),
                    item.getContent(),
                    item.getCreatedAt(),
                    item.getId()
            );
        }
        return item;
    }

    @Override
    public void delete(Long id) {}
}
