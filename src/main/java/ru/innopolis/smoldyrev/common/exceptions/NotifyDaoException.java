package ru.innopolis.smoldyrev.common.exceptions;

/**
 * Ошибка DAO
 * @see ru.innopolis.smoldyrev.models.dao.NotifyDAO
 */
public class NotifyDaoException extends Exception {
    public NotifyDaoException(String message) {

        super(message);

    }

    public NotifyDaoException() {
    }
}
