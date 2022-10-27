package com.itis.filters_example.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private String courseName;
    private String passwordHash;
    private Integer age;
}
