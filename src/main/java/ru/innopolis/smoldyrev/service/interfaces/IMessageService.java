package ru.innopolis.smoldyrev.service;

import ru.innopolis.smoldyrev.common.exceptions.MessageServiceException;
import ru.innopolis.smoldyrev.models.pojo.Message;

import java.util.List;

/**
 * Created by smoldyrev on 09.03.17.
 */

public interface IMessageService {

    boolean sendMessage(Message message) throws MessageServiceException;

    List<Message> getAllInRoom(int chatroom) throws MessageServiceException;

    void deleteMessage(int id) throws MessageServiceException;

}
