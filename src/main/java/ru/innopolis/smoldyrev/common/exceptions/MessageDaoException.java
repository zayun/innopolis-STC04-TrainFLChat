package ru.innopolis.smoldyrev.common.exceptions;

/**
 * Ошибка DAO
 * @see ru.innopolis.smoldyrev.models.dao.MessageDAO
 */
public class MessageDaoException extends Exception {
    public MessageDaoException(String message) {

        super(message);

    }

    public MessageDaoException() {
    }
}
