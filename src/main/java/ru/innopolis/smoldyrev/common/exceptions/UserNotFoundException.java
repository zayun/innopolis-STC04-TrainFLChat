package ru.innopolis.smoldyrev.common.exceptions;

/**
 * Created by smoldyrev on 02.03.17.
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {

        super(message);

    }

    public UserNotFoundException() {
    }
}
