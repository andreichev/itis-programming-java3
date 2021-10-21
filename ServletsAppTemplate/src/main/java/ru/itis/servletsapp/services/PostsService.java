package ru.itis.servletsapp.services;

import ru.itis.servletsapp.dto.PostDto;

import java.util.List;

public interface PostsService {
    List<PostDto> getByAuthorId(Long authorId);
    PostDto addPost(PostDto postDto);
}
