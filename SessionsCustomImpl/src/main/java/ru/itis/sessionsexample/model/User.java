package ru.itis.sessionsexample.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String name;
    private String password;
    private String email;
}
