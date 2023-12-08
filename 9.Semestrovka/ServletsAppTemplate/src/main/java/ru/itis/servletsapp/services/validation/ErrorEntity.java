package ru.itis.servletsapp.services.validation;

import lombok.Getter;

@Getter
// @JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorEntity {
    // Общие ошибки
    INVALID_REQUEST(400, "Неверный запрос"),
    INVALID_TOKEN(403, "Ошибка авторизации"),
    FORBIDDEN(403, "Доступ запрещен"),
    NOT_FOUND(404, "Не найдено"),
    INVALID_NAME(450, "Неверное имя"),
    EMAIL_ALREADY_TAKEN(453, "Email уже занят"),

    // Вход
    USER_NOT_FOUND(404,"Пользователь не найден"),
    INCORRECT_PASSWORD(460, "Неверный пароль"),

    // Регистрация
    EMAIL_ALREADY_EXISTS(460,"Email уже занят"),
    INVALID_EMAIL(461,"Email не правильный"),
    PASSWORD_TOO_SIMPLE(462,"Пароль слишком простой"),

    // Выгрузка картинки
    ONLY_IMAGES_AVAILABLE_TO_UPLOAD(460, "Выгружать можно только картинки"),
    ;

    int status;
    String message;

    ErrorEntity(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
