package ru.innopolis.smoldyrev.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.smoldyrev.common.exceptions.ConverseDaoException;
import ru.innopolis.smoldyrev.common.exceptions.ConverseServiceException;
import ru.innopolis.smoldyrev.common.exceptions.MessageDaoException;
import ru.innopolis.smoldyrev.common.exceptions.MessageServiceException;
import ru.innopolis.smoldyrev.models.dao.interfaces.IConverseDAO;
import ru.innopolis.smoldyrev.models.pojo.Conversation;
import ru.innopolis.smoldyrev.models.pojo.Message;
import ru.innopolis.smoldyrev.service.interfaces.IConverseService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by smoldyrev on 12.03.17.
 */
@Service
public class ConverseService implements IConverseService {

    private static Logger logger = Logger.getLogger(ConverseService.class);


    private IConverseDAO converseDAO;

    @Autowired
    private void setConverseDAO(IConverseDAO converseDAO) {
        this.converseDAO = converseDAO;
    }

    public int createConversation(int chatroom, LocalDateTime dateTime) throws ConverseServiceException {
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

    public List<Conversation> getActiveConversation(LocalDateTime dateTime) throws ConverseServiceException {
        try {
            return converseDAO.getActiveConversation(dateTime);
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
}
