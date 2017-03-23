package ru.innopolis.smoldyrev.models.dao.interfaces;

import ru.innopolis.smoldyrev.common.exceptions.ConverseDaoException;
import ru.innopolis.smoldyrev.models.entity.ConversationEntity;
import ru.innopolis.smoldyrev.models.pojo.Conversation;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by smoldyrev on 12.03.17.
 */
public interface IConverseDAO {


    Integer getConversation(int chatroom, Timestamp date) throws  ConverseDaoException;

    ConversationEntity getEntityById(Integer id);

    boolean addConverseMember(int userId, int converse) throws  ConverseDaoException;

    List<ConversationEntity> getActiveConversation(Timestamp dateTime) throws  ConverseDaoException;

    boolean checkUserInChatroom(int chatroom, int userId) throws ConverseDaoException;

    ConversationEntity update(Conversation conv);
}
