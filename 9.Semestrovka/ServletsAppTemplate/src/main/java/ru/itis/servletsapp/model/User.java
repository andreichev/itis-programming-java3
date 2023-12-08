package ru.itis.servletsapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String hashPassword;
    private String email;
    private Integer age;
    private Long avatarId;
    private List<Post> posts;
}
