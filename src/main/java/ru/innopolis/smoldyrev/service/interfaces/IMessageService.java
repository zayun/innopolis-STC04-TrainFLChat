package ru.innopolis.smoldyrev.service.interfaces;

import ru.innopolis.smoldyrev.common.exceptions.MessageServiceException;
import ru.innopolis.smoldyrev.models.pojo.Conversation;
import ru.innopolis.smoldyrev.models.pojo.Message;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by smoldyrev on 09.03.17.
 */

public interface IMessageService {

    boolean sendMessage(Message message) throws MessageServiceException;

    List<Message> getAllInRoom(int chatroom) throws MessageServiceException;

    void deleteMessage(int id) throws MessageServiceException;

}
