package ru.innopolis.smoldyrev.common.exceptions;

/**
 * Ошибка сервиса
 * @see ru.innopolis.smoldyrev.service.MessageService
 */
public class MessageServiceException extends Exception {
    public MessageServiceException(String message) {

        super(message);

    }

    public MessageServiceException() {
    }
}
