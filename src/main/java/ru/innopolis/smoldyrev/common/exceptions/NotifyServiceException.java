package ru.innopolis.smoldyrev.common.exceptions;

/**
 * Ошибка сервиса
 * @see ru.innopolis.smoldyrev.service.NotifyService
 */
public class NotifyServiceException extends Exception {
    public NotifyServiceException(String message) {

        super(message);

    }

    public NotifyServiceException() {
    }
}
