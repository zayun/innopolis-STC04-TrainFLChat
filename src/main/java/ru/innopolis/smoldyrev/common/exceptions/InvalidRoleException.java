package ru.innopolis.smoldyrev.common.exceptions;


/**
 * Ошибка доступа
 * Неправильная роль
 * в связи с переходом на SpringSecurity
 * объявлен @Deprecated
 */
@Deprecated
public class InvalidRoleException extends Exception {
    public InvalidRoleException(String message) {

        super(message);

    }

    public InvalidRoleException() {
    }

}
