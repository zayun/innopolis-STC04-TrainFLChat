package ru.innopolis.smoldyrev.models.dao.interfaces;

import ru.innopolis.smoldyrev.common.exceptions.ConverseDaoException;
import ru.innopolis.smoldyrev.common.exceptions.MessageDaoException;
import ru.innopolis.smoldyrev.models.dto.ConversationDTO;
import ru.innopolis.smoldyrev.models.pojo.Conversation;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by smoldyrev on 12.03.17.
 */
public interface IConverseDAO {


    Integer getConversation(int chatroom, LocalDateTime date) throws  ConverseDaoException;

    ConversationDTO getEntityById(Integer id);

    boolean addConverseMember(int userId, int converse) throws  ConverseDaoException;

    List<ConversationDTO> getActiveConversation(LocalDateTime dateTime) throws  ConverseDaoException;

    boolean checkUserInChatroom(int chatroom, int userId) throws ConverseDaoException;

    ConversationDTO update(Conversation conv);
}
