package ru.itis.servletsapp.dao;

import ru.itis.servletsapp.dao.base.CrudRepository;
import ru.itis.servletsapp.model.Post;

import java.util.List;

public interface PostsRepository extends CrudRepository<Post, Long> {
    List<Post> findByAuthorId(Long authorId);
}
