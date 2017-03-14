package ru.innopolis.smoldyrev.common.exceptions;

/**
 * Ошибка доступа
 * Не найден пользователь
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {

        super(message);

    }

    public UserNotFoundException() {
    }
}
