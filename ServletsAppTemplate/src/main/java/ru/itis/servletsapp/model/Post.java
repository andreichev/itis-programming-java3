package ru.itis.servletsapp.model;

import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Post {
    Long id;
    User author;
    String content;
    Timestamp createdAt;
}
