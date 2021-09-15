package ru.itis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Driver {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
}
