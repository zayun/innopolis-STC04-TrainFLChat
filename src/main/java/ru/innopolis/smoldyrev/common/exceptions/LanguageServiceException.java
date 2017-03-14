package ru.innopolis.smoldyrev.common.exceptions;

/**
 * Ошибка сервиса
 * @see ru.innopolis.smoldyrev.service.LanguageService
 */
public class LanguageServiceException extends Exception {
    public LanguageServiceException(String message) {

        super(message);

    }

    public LanguageServiceException() {
    }
}
