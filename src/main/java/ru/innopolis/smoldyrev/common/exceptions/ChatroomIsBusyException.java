package ru.innopolis.smoldyrev.common.exceptions;

/**
 * Ошибка доступа к беседе
 * если пользователя нет в списке
 * участников беседы
 * @see ru.innopolis.smoldyrev.models.pojo.ConverseMember
 */
public class ChatroomIsBusyException extends Exception {
    public ChatroomIsBusyException(String message) {

        super(message);

    }

    public ChatroomIsBusyException() {
    }
}
