package ru.innopolis.smoldyrev.models.dao.interfaces;

import ru.innopolis.smoldyrev.common.exceptions.MessageDaoException;
import ru.innopolis.smoldyrev.models.pojo.Conversation;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by smoldyrev on 12.03.17.
 */
public interface IConverseDAO {


    int getConversation(int chatroom, LocalDateTime date) throws MessageDaoException;

    boolean addConverseMember(int userId, int converse) throws MessageDaoException;

    List<Conversation> getActiveConversation(LocalDateTime dateTime) throws MessageDaoException;
}
