package ru.innopolis.smoldyrev.common.exceptions;

/**
 * Ошибка DAO
 * @see ru.innopolis.smoldyrev.models.dao.LanguageDAO
 */
public class LanguageDaoException extends Exception {
    public LanguageDaoException(String message) {

        super(message);

    }

    public LanguageDaoException() {
    }
}
