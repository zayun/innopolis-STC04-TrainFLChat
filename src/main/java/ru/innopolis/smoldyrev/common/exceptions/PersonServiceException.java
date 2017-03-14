package ru.innopolis.smoldyrev.common.exceptions;

/**
 * Ошибка сервиса
 * @see ru.innopolis.smoldyrev.service.PersonService
 */
public class PersonServiceException extends Exception {
    public PersonServiceException(String message) {

        super(message);

    }

    public PersonServiceException() {
    }
}
