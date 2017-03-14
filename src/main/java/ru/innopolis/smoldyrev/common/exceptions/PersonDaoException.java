package ru.innopolis.smoldyrev.common.exceptions;

/**
 * Ошибка DAO
 * @see ru.innopolis.smoldyrev.models.dao.PersonDAO
 */
public class PersonDaoException extends Exception {
    public PersonDaoException(String message) {

        super(message);

    }

    public PersonDaoException() {
    }
}
