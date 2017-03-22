package ru.innopolis.smoldyrev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.smoldyrev.common.exceptions.MessageDaoException;
import ru.innopolis.smoldyrev.common.exceptions.MessageServiceException;
import ru.innopolis.smoldyrev.models.dao.interfaces.IMessageDAO;
import ru.innopolis.smoldyrev.models.dto.Transformer;
import ru.innopolis.smoldyrev.models.pojo.Message;
import org.apache.log4j.Logger;
import ru.innopolis.smoldyrev.models.repository.MessageRepository;
import ru.innopolis.smoldyrev.service.interfaces.IMessageService;

import java.util.List;

/**
 * Created by smoldyrev on 25.02.17.
 * Сервис работы с сообщениями
 */
@Service
public class MessageService implements IMessageService {

    private static Logger logger = Logger.getLogger(MessageService.class);

    private MessageRepository messageRepository;

    @Autowired
    public void setMessageRepository(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public boolean sendMessage(Message message) throws MessageServiceException {

        try {
            return (messageRepository.saveAndFlush(Transformer.message(message))!=null);
        } catch (Exception e) {
            logger.error(e);
            throw new MessageServiceException();
        }
    }

    public List<Message> getAllInRoom(int chatroom) throws MessageServiceException {
        try {
            return Transformer.message(messageRepository.findByChatRoomOrderByDateDesc(chatroom));
        } catch (Exception e) {
            logger.error(e);
            throw new MessageServiceException();
        }

    }

    public void deleteMessage(int id) throws MessageServiceException {
        try {

            messageRepository.delete(id);
        } catch (Exception e) {
            logger.error(e);
            throw new MessageServiceException();
        }
    }

}
