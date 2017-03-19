package ru.innopolis.smoldyrev.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.smoldyrev.common.exceptions.ConverseDaoException;
import ru.innopolis.smoldyrev.common.exceptions.ConverseServiceException;
import ru.innopolis.smoldyrev.common.exceptions.MessageDaoException;
import ru.innopolis.smoldyrev.common.exceptions.MessageServiceException;
import ru.innopolis.smoldyrev.models.dao.interfaces.IConverseDAO;
import ru.innopolis.smoldyrev.models.dto.ConversationDTO;
import ru.innopolis.smoldyrev.models.dto.DtoTransformer;
import ru.innopolis.smoldyrev.models.dto.Transformer;
import ru.innopolis.smoldyrev.models.pojo.Conversation;
import ru.innopolis.smoldyrev.models.pojo.Message;
import ru.innopolis.smoldyrev.models.pojo.User;
import ru.innopolis.smoldyrev.service.interfaces.IConverseService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smoldyrev on 12.03.17.
 * Сервис работы с сущностями таблиц r_conversation и r_converse_members
 */
@Service
public class ConverseService implements IConverseService {

    private static Logger logger = Logger.getLogger(ConverseService.class);


    private IConverseDAO converseDAO;

    @Autowired
    private void setConverseDAO(IConverseDAO converseDAO) {
        this.converseDAO = converseDAO;
    }

    public int createConversation(int chatroom, Timestamp dateTime) throws ConverseServiceException {
        try {
            return converseDAO.getConversation(chatroom, dateTime);
        } catch (ConverseDaoException e) {
            logger.error(e);
            throw new ConverseServiceException();
        }
    }

    public boolean addConverseMember(int userId, int converse) throws ConverseServiceException {
        try {
            return converseDAO.addConverseMember(userId, converse);
        } catch (ConverseDaoException e) {
            logger.error(e);
            throw new ConverseServiceException();
        }
    }

    public List<Conversation> getActiveConversation(Timestamp dateTime) throws ConverseServiceException {
        try {
            return Transformer.conversationEntityToPojo(converseDAO.getActiveConversation(dateTime));
        } catch (ConverseDaoException e) {
            logger.error(e);
            throw new ConverseServiceException();
        }

    }

    @Override
    public boolean checkConverseMember(int chatroom, int userId) throws ConverseServiceException {
        try {
            return converseDAO.checkUserInChatroom(chatroom, userId);
        } catch (ConverseDaoException e) {
            logger.error(e);
            throw new ConverseServiceException();
        }
    }

    public Conversation getConversation(Integer converseId) {
        return Transformer.conversationEntityToPojo(converseDAO.getEntityById(converseId));
    }

    public Conversation update(Conversation conv) {
        return Transformer.conversationEntityToPojo(converseDAO.update(conv));
    }
}
