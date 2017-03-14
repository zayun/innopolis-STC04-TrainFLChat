package ru.innopolis.smoldyrev.common.exceptions;

/**
 * Ошибка сервиса
 * @see ru.innopolis.smoldyrev.service.UserService
 */
public class UserServiceException extends Exception {

    public UserServiceException(String message) {

        super(message);

    }

    public UserServiceException() {
    }
}
