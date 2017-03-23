package ru.innopolis.smoldyrev.common.exceptions;

/**
 * Created by smoldyrev on 23.03.17.
 */
public class InvalidObjectVersionException extends Exception{
    public InvalidObjectVersionException(String message) {

        super(message);

    }

    public InvalidObjectVersionException() {
    }
}
