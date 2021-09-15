package com.itis.servletsexamplesecond.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer age;
}
