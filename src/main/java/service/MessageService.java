package service;

import exceptions.MessageDaoException;
import exceptions.MessageServiceException;
import exceptions.PersonServiceException;
import models.dao.MessageDAO;
import models.pojo.Message;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by smoldyrev on 25.02.17.
 * Сервис работы с сообщениями
 */
public class MessageService {

    private static Logger logger = Logger.getLogger(MessageService.class);

    private static MessageDAO messageDAO = new MessageDAO();

    public static boolean sendMessage(Message message) throws MessageServiceException {

        try {
            return messageDAO.create(message);
        } catch (MessageDaoException e) {
            logger.error(e);
            throw new MessageServiceException();
        }
    }

    public static List<Message> getAllInRoom(int chatroom) throws MessageServiceException {
        try {
            return messageDAO.getAllInRoom(chatroom);
        } catch (MessageDaoException e) {
            logger.error(e);
            throw new MessageServiceException();
        }

    }

    public static void deleteMessage(int id) throws MessageServiceException {
        try {
            messageDAO.delete(id);
        } catch (MessageDaoException e) {
            logger.error(e);
            throw new MessageServiceException();
        }
    }

}
