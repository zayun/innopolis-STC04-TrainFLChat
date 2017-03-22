package ru.innopolis.smoldyrev.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.smoldyrev.common.exceptions.ConverseDaoException;
import ru.innopolis.smoldyrev.common.exceptions.ConverseServiceException;
import ru.innopolis.smoldyrev.models.dao.interfaces.IConverseDAO;
import ru.innopolis.smoldyrev.models.dto.ConversationDTO;
import ru.innopolis.smoldyrev.models.dto.Transformer;
import ru.innopolis.smoldyrev.models.dto.UserDTO;
import ru.innopolis.smoldyrev.models.pojo.Conversation;
import ru.innopolis.smoldyrev.models.repository.ConverseRepository;
import ru.innopolis.smoldyrev.models.repository.UserRepository;
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

    private ConverseRepository converseRepository;
    private UserRepository userRepository;

    @Autowired
    public void setConverseRepository(ConverseRepository converseRepository) {
        this.converseRepository = converseRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int createConversation(int chatroom, Timestamp dateTime) throws ConverseServiceException {
        try {

            ConversationDTO conv =
                    converseRepository.findByChatroomAndStartTimeBeforeAndEndTimeAfter(chatroom,dateTime,dateTime);
            if (conv == null) {
                ConversationDTO newConv = new ConversationDTO();
                newConv.setChatroom(chatroom);
                newConv.setStartTime(dateTime);
                newConv.setEndTime(Timestamp.valueOf(LocalDateTime.now().plusDays(30)));
                conv = converseRepository.saveAndFlush(newConv);
            }

            return conv.getId();
        } catch (Exception e) {
            logger.error(e);
            throw new ConverseServiceException();
        }
    }

    public boolean addConverseMember(int userId, int converse) throws ConverseServiceException {
        try {
            ConversationDTO conv = converseRepository.findOne(converse);
            conv.addUser(userRepository.findOne(userId));
            converseRepository.saveAndFlush(conv);
            return true;
        } catch (Exception e) {
            logger.error(e);
            throw new ConverseServiceException();
        }
    }

    public List<Conversation> getActiveConversation(Timestamp dateTime) throws ConverseServiceException {
        try {
            List<Conversation> ls = new ArrayList<>();
            for (ConversationDTO c:
                    converseRepository.findByEndTimeAfter(dateTime)) {
                ls.add(Transformer.conversation(c));
            }
            return ls;
        } catch (Exception e) {
            logger.error(e);
            throw new ConverseServiceException();
        }

    }

    /**Заглушка
     * переделать на проверку наличия в беседе, а не чатруме
     * */
    @Override
    public boolean checkConverseMember(int chatroom, int userId) throws ConverseServiceException {
        try {
            ConversationDTO conv = converseRepository.findByChatroom(chatroom);

            for (UserDTO u:
                 conv.getUsers()) {
                if (u.getUserID()==userId) return true;
            }
            return false;
        } catch (Exception e) {
            logger.error(e);
            throw new ConverseServiceException();
        }
    }

    public Conversation getConversation(Integer converseId) {
        return Transformer.conversation(converseRepository.findOne(converseId));
    }

    public Conversation update(Conversation conv) {
        return Transformer.conversation(converseRepository.saveAndFlush(Transformer.conversation(conv)));
    }
}
