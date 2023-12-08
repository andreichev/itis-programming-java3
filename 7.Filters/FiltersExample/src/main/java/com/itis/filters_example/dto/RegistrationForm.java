package com.itis.filters_example.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationForm {
    private String firstName;
    private String lastName;
    private String courseName;
    private String password;
    private Integer age;
}
