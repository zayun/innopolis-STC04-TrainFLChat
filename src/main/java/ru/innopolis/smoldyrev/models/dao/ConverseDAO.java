package ru.innopolis.smoldyrev.models.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.innopolis.smoldyrev.common.exceptions.ConverseDaoException;
import ru.innopolis.smoldyrev.common.exceptions.MessageDaoException;
import ru.innopolis.smoldyrev.common.exceptions.UserDaoException;
import ru.innopolis.smoldyrev.models.connector.DatabaseManager;
import ru.innopolis.smoldyrev.models.dao.interfaces.IConverseDAO;
import ru.innopolis.smoldyrev.models.dto.ConversationDTO;
import ru.innopolis.smoldyrev.models.dto.UserDTO;
import ru.innopolis.smoldyrev.models.pojo.Conversation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smoldyrev on 12.03.17.
 */
@Repository
public class ConverseDAO implements IConverseDAO {

    private static Logger logger = Logger.getLogger(ConverseDAO.class);

    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory("LFLChat");

    @Override
    public int getConversation(int chatroom, LocalDateTime date) throws ConverseDaoException {

        EntityManager entityManager = FACTORY.createEntityManager();
        TypedQuery<ConversationDTO> query = entityManager.createQuery(
                "SELECT converse FROM ConversationDTO converse where converse.chatrooom = :chatroom " +
                        "and converse.endTime > :date and converse.startTime < :date", ConversationDTO.class);
        query.setParameter("chatroom", chatroom);
        query.setParameter("date", date);
        ConversationDTO conversation = query.getSingleResult();
        return conversation.getId();
    }

    @Override
    public boolean addConverseMember(int userId, int converse) throws ConverseDaoException {
        EntityManager entityManager = FACTORY.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            UserDAO udao = new UserDAO();
            ConverseDAO cdao = new ConverseDAO();

            ConversationDTO conversationDTO = cdao.getEntityById(converse);
            conversationDTO.addUser(udao.getEntityById(userId));
            conversationDTO = entityManager.merge(conversationDTO);

//            conversationDTO =
//                    entityManager.contains(conversationDTO) ? conversationDTO : entityManager.merge(conversationDTO);

            entityManager.getTransaction().commit();
            entityManager.close();
            return true;
        } catch (UserDaoException e) {
            logger.error(e);
            throw new ConverseDaoException();
        }
    }

    @Override
    public List<Conversation> getActiveConversation(LocalDateTime dateTime) throws ConverseDaoException {

        EntityManager entityManager = FACTORY.createEntityManager();
        TypedQuery<ConversationDTO> query = entityManager.createQuery(
                "SELECT converse FROM ConversationDTO converse where converse.endTime > :date", ConversationDTO.class);
        query.setParameter("date", dateTime);

        List<ConversationDTO> list = query.getResultList();

        List<Conversation> conversationList = new ArrayList<>(list.size());
        for (ConversationDTO conv:
             list) {
            conversationList.add(conv.transformToConversation());
        }
        return conversationList;
    }

    @Override
    public boolean checkUserInChatroom(int chatroom, int userId) throws ConverseDaoException {

        EntityManager entityManager = FACTORY.createEntityManager();
        TypedQuery<ConversationDTO> query = entityManager.createQuery(
                "SELECT converse FROM ConversationDTO converse where converse.chatrooom = :chatroom ",
                ConversationDTO.class);
        query.setParameter("chatroom", chatroom);
        ConversationDTO conversation = query.getSingleResult();
        for (UserDTO u:
             conversation.getUsers()) {
            if (u.transformToUser().getUserID().equals(userId)) return true;
        }
        return false;
    }

    @Override
    public ConversationDTO getEntityById(Integer id) {
        logger.debug("////////zzz/////"+id);
        EntityManager entityManager = FACTORY.createEntityManager();
        TypedQuery<ConversationDTO> query = entityManager.createQuery(
                "SELECT converse FROM ConversationDTO converse where converse.id = :id", ConversationDTO.class);
        return query.setParameter("id", id).getSingleResult();
    }

}
