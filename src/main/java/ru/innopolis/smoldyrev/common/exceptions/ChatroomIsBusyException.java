package ru.innopolis.smoldyrev.common.exceptions;

/**
 * Created by smoldyrev on 12.03.17.
 */
public class ChatroomIsBusyException extends Exception {
    public ChatroomIsBusyException(String message) {

        super(message);

    }

    public ChatroomIsBusyException() {
    }
}
