package ru.innopolis.smoldyrev.models.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.innopolis.smoldyrev.common.exceptions.ConverseDaoException;
import ru.innopolis.smoldyrev.common.exceptions.UserDaoException;
import ru.innopolis.smoldyrev.models.dao.interfaces.IConverseDAO;
import ru.innopolis.smoldyrev.models.entity.ConversationEntity;
import ru.innopolis.smoldyrev.models.entity.UserEntity;
import ru.innopolis.smoldyrev.models.pojo.Conversation;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    public Integer getConversation(int chatroom, Timestamp date) throws ConverseDaoException {

        EntityManager entityManager = FACTORY.createEntityManager();
        TypedQuery<ConversationEntity> query = entityManager.createQuery(
                "SELECT converse FROM ConversationEntity converse where converse.chatroom = :chatroom " +
                        "and converse.endTime > :date and converse.startTime < :date", ConversationEntity.class);
        query.setParameter("chatroom", chatroom);
        query.setParameter("date", date);
        try {
            ConversationEntity conversation = query.getSingleResult();
            return conversation.getId();
        } catch (NoResultException e) {
            logger.trace(e);
            logger.trace("create new conversation");
            Conversation conv = new Conversation(
                    0,
                    chatroom,
                    date,
                    Timestamp.valueOf(LocalDateTime.now().plusDays(30)),
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

            ConversationEntity conversationEntity = getEntityById(converse);
            conversationEntity.addUser(udao.getEntityById(userId));
            conversationEntity = entityManager.merge(conversationEntity);

            entityManager.getTransaction().commit();

            return true;
        } catch (UserDaoException e) {
            logger.error(e);
            throw new ConverseDaoException();
        } finally {
            entityManager.close();
        }
    }

    public ConversationEntity update(Conversation conv) {
        EntityManager entityManager = FACTORY.createEntityManager();
        entityManager.getTransaction().begin();

        try {

            ConversationEntity conversationEntity = getEntityById(conv.getId());
            conversationEntity.setChatroom(conv.getChatroom());
            conversationEntity.setGradeConverse(conv.getGradeConverse());
            conversationEntity.setStartTime(conv.getStartTime());
            conversationEntity.setEndTime(conv.getEndTime());
            conversationEntity = entityManager.merge(conversationEntity);
            entityManager.getTransaction().commit();

            return conversationEntity;
        } finally {
            entityManager.close();
        }

    }

    @Override
    public List<ConversationEntity> getActiveConversation(Timestamp dateTime) throws ConverseDaoException {

        EntityManager entityManager = FACTORY.createEntityManager();
        TypedQuery<ConversationEntity> query = entityManager.createQuery(
                "SELECT converse FROM ConversationEntity converse where converse.endTime > :date", ConversationEntity.class);
        query.setParameter("date", dateTime);
        try {


            List<ConversationEntity> list = query.getResultList();

            return list;
        }finally {
            entityManager.close();
        }
    }

    @Override
    public boolean checkUserInChatroom(int chatroom, int userId) throws ConverseDaoException {

        EntityManager entityManager = FACTORY.createEntityManager();
        TypedQuery<ConversationEntity> query = entityManager.createQuery(
                "SELECT converse FROM ConversationEntity converse where converse.chatroom = :chatroom ",
                ConversationEntity.class);
        query.setParameter("chatroom", chatroom);
        try {
            ConversationEntity conversation = query.getSingleResult();
            for (UserEntity u :
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
    public ConversationEntity getEntityById(Integer id) {

        EntityManager entityManager = FACTORY.createEntityManager();

        ConversationEntity conversationEntity = (ConversationEntity) entityManager.find(ConversationEntity.class, id);
        return conversationEntity;

    }

    public ConversationEntity create(Conversation entity) {

        EntityManager entityManager = FACTORY.createEntityManager();
        entityManager.getTransaction().begin();
        try {

            ConversationEntity conversationEntity = new ConversationEntity();

            conversationEntity.setChatroom(entity.getChatroom());
            conversationEntity.setStartTime(entity.getStartTime());
            conversationEntity.setEndTime(entity.getEndTime());

            conversationEntity = entityManager.merge(conversationEntity);
            entityManager.getTransaction().commit();


            return conversationEntity;
        }finally {
            entityManager.close();
        }
    }

}
