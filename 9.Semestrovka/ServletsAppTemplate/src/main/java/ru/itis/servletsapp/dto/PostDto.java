package ru.itis.servletsapp.dto;

import lombok.*;
import ru.itis.servletsapp.model.Post;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    private UserDto author;
    private String content;
    private Timestamp createdAt;

    public static PostDto from(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .author(UserDto.from(post.getAuthor()))
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
