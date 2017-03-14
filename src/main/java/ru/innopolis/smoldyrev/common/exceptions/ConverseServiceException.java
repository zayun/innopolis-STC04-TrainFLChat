package ru.innopolis.smoldyrev.common.exceptions;

/**
 * Ошибка сервиса
 * @see ru.innopolis.smoldyrev.service.ConverseService
 */
public class ConverseServiceException extends Exception{
    public ConverseServiceException(String message) {

        super(message);

    }

    public ConverseServiceException() {
    }
}

