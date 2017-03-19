package ru.innopolis.smoldyrev.service.interfaces;

import ru.innopolis.smoldyrev.common.exceptions.ConverseServiceException;
import ru.innopolis.smoldyrev.models.pojo.Conversation;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by smoldyrev on 12.03.17.
 */
public interface IConverseService {


    int createConversation(int chatroom, Timestamp dateTime) throws  ConverseServiceException;

    boolean addConverseMember(int userId, int converse) throws  ConverseServiceException;

    List<Conversation> getActiveConversation(Timestamp dateTime) throws  ConverseServiceException;

    boolean checkConverseMember(int chatroom, int userId) throws ConverseServiceException;

    Conversation getConversation(Integer converseId);

    Conversation update(Conversation conv);
}
