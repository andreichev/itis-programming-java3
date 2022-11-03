package com.itis.filters_example.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInForm {
    private String name;
    private String password;
}
