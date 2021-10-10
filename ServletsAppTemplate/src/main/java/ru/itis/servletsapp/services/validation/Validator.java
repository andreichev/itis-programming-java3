package ru.itis.servletsapp.services.validation;

import ru.itis.servletsapp.dto.UserForm;

import java.util.Set;

public interface Validator {
    Set<ErrorEntity> validate(UserForm form);
}
