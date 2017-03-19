package ru.innopolis.smoldyrev.models.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.innopolis.smoldyrev.common.exceptions.ConverseDaoException;
import ru.innopolis.smoldyrev.common.exceptions.MessageDaoException;
import ru.innopolis.smoldyrev.common.exceptions.UserDaoException;
import ru.innopolis.smoldyrev.models.connector.DatabaseManager;
import ru.innopolis.smoldyrev.models.dao.interfaces.IConverseDAO;
import ru.innopolis.smoldyrev.models.dto.ConversationDTO;
import ru.innopolis.smoldyrev.models.dto.MessageDTO;
import ru.innopolis.smoldyrev.models.dto.UserDTO;
import ru.innopolis.smoldyrev.models.pojo.Conversation;
import ru.innopolis.smoldyrev.models.pojo.Message;

import javax.persistence.*;
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
    public Integer getConversation(int chatroom, LocalDateTime date) throws ConverseDaoException {

        EntityManager entityManager = FACTORY.createEntityManager();
        TypedQuery<ConversationDTO> query = entityManager.createQuery(
                "SELECT converse FROM ConversationDTO converse where converse.chatrooom = :chatroom " +
                        "and converse.endTime > :date and converse.startTime < :date", ConversationDTO.class);
        query.setParameter("chatroom", chatroom);
        query.setParameter("date", date);
        try {
            ConversationDTO conversation = query.getSingleResult();
            return conversation.getId();
        } catch (NoResultException e) {
            logger.trace(e);
            logger.trace("create new conversation");
            Conversation conv = new Conversation(
                    0,
                    chatroom,
                    date,
                    date.plusDays(30),
                    0);
            return create(conv).getId();
        } finally {
            entityManager.close();
        }

    }

    @Override
    public boolean addConverseMember(int userId, int converse) throws ConverseDaoException {
        EntityManager entityManager = FACTORY.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            UserDAO udao = new UserDAO();

            ConversationDTO conversationDTO = getEntityById(converse);
            conversationDTO.addUser(udao.getEntityById(userId));
            conversationDTO = entityManager.merge(conversationDTO);

            entityManager.getTransaction().commit();

            return true;
        } catch (UserDaoException e) {
            logger.error(e);
            throw new ConverseDaoException();
        } finally {
            entityManager.close();
        }
    }

    public ConversationDTO update(Conversation conv) {
        EntityManager entityManager = FACTORY.createEntityManager();
        entityManager.getTransaction().begin();

        try {

            ConversationDTO conversationDTO = getEntityById(conv.getId());
            conversationDTO.setChatrooom(conv.getChatrooom());
            conversationDTO.setGradeConverse(conv.getGradeConverse());
            conversationDTO.setStartTime(conv.getStartTime());
            conversationDTO.setEndTime(conv.getEndTime());
            conversationDTO = entityManager.merge(conversationDTO);
            entityManager.getTransaction().commit();

            return conversationDTO;
        } finally {
            entityManager.close();
        }

    }

    @Override
    public List<ConversationDTO> getActiveConversation(LocalDateTime dateTime) throws ConverseDaoException {

        EntityManager entityManager = FACTORY.createEntityManager();
        TypedQuery<ConversationDTO> query = entityManager.createQuery(
                "SELECT converse FROM ConversationDTO converse where converse.endTime > :date", ConversationDTO.class);
        query.setParameter("date", dateTime);
        try {


            List<ConversationDTO> list = query.getResultList();

            return list;
        }finally {
            entityManager.close();
        }
    }

    @Override
    public boolean checkUserInChatroom(int chatroom, int userId) throws ConverseDaoException {

        EntityManager entityManager = FACTORY.createEntityManager();
        TypedQuery<ConversationDTO> query = entityManager.createQuery(
                "SELECT converse FROM ConversationDTO converse where converse.chatrooom = :chatroom ",
                ConversationDTO.class);
        query.setParameter("chatroom", chatroom);
        try {
            ConversationDTO conversation = query.getSingleResult();
            for (UserDTO u :
                    conversation.getUsers()) {
                if (u.getUserID().equals(userId)) return true;
            }
        } catch (NoResultException e) {
            throw new ConverseDaoException("Такой комнаты не сущесвует!");
        } finally {
            entityManager.close();
        }
        return false;
    }

    @Override
    public ConversationDTO getEntityById(Integer id) {

        EntityManager entityManager = FACTORY.createEntityManager();
        TypedQuery<ConversationDTO> query = entityManager.createQuery(
                "SELECT converse FROM ConversationDTO converse where converse.id = :id", ConversationDTO.class);
        try {
            return query.setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            logger.error(e);
            return null;
        } finally {
            entityManager.close();
        }

    }

    public ConversationDTO create(Conversation entity) {

        EntityManager entityManager = FACTORY.createEntityManager();
        entityManager.getTransaction().begin();
        try {

            ConversationDTO conversationDTO = new ConversationDTO();

            conversationDTO.setChatrooom(entity.getChatrooom());
            conversationDTO.setStartTime(entity.getStartTime());
            conversationDTO.setEndTime(entity.getEndTime());

            conversationDTO = entityManager.merge(conversationDTO);
            entityManager.getTransaction().commit();


            return conversationDTO;
        }finally {
            entityManager.close();
        }
    }

}
