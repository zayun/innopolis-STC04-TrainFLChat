package ru.innopolis.smoldyrev.common.exceptions;

/**
 * Ошибка DAO
 * @see ru.innopolis.smoldyrev.models.dao.UserDAO
 */
public class UserDaoException extends Exception {
    public UserDaoException(String message) {

        super(message);

    }

    public UserDaoException() {
    }
}
