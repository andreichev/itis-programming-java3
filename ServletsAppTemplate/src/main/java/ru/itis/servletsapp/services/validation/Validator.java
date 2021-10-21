package ru.itis.servletsapp.services.validation;

import ru.itis.servletsapp.dto.in.UserForm;

import java.util.Optional;

public interface Validator {
    Optional<ErrorEntity> validateRegistration(UserForm form);
}
