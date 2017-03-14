package ru.innopolis.smoldyrev.common.exceptions;

/**
 * Ошибка DAO
 * @see ru.innopolis.smoldyrev.models.dao.ConverseDAO
 */
public class ConverseDaoException extends Exception {
    public ConverseDaoException(String message) {

        super(message);

    }

    public ConverseDaoException() {
    }
}
