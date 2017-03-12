package ru.innopolis.smoldyrev.service.interfaces;

import ru.innopolis.smoldyrev.common.exceptions.MessageServiceException;
import ru.innopolis.smoldyrev.models.pojo.Conversation;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by smoldyrev on 12.03.17.
 */
public interface IConverseService {


    int createConversation(int chatroom, LocalDateTime dateTime) throws MessageServiceException;

    boolean addConverseMember(int userId, int converse) throws MessageServiceException;

    List<Conversation> getActiveConversation(LocalDateTime dateTime) throws MessageServiceException;
}
