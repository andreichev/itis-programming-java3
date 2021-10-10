package ru.itis.servletsapp.exceptions;

import ru.itis.servletsapp.services.validation.ErrorEntity;

public class NotFoundException extends ValidationException {
    public NotFoundException(String message) {
        super(ErrorEntity.NOT_FOUND, message);
    }
}
