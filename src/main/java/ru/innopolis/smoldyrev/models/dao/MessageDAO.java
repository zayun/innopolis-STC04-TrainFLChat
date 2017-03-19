package ru.innopolis.smoldyrev.models.dao;

import org.springframework.stereotype.Repository;
import ru.innopolis.smoldyrev.common.exceptions.MessageDaoException;
import ru.innopolis.smoldyrev.common.exceptions.UserDaoException;
import ru.innopolis.smoldyrev.models.connector.DatabaseManager;
import ru.innopolis.smoldyrev.models.dao.interfaces.IMessageDAO;
import ru.innopolis.smoldyrev.models.dto.MessageDTO;
import ru.innopolis.smoldyrev.models.dto.UserDTO;
import ru.innopolis.smoldyrev.models.pojo.Message;
import ru.innopolis.smoldyrev.models.pojo.User;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smoldyrev on 25.02.17.
 */
@Repository
public class MessageDAO implements IMessageDAO {

    private static Logger logger = Logger.getLogger(MessageDAO.class);

    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory("LFLChat");


    public List<MessageDTO> getAll() throws MessageDaoException {

        EntityManager em = FACTORY.createEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<MessageDTO> cq = cb.createQuery(MessageDTO.class);
        Root<MessageDTO> from = cq.from(MessageDTO.class);

        try {
            cq.select(from);
            TypedQuery<MessageDTO> q = em.createQuery(cq);
            List<MessageDTO> messages = q.getResultList();
            return messages;
        } finally {
            em.close();
        }

    }

    public List<MessageDTO> getAllInRoom(int chatRoom) throws MessageDaoException {
        EntityManager em = FACTORY.createEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<MessageDTO> cq = cb.createQuery(MessageDTO.class);
        Root<MessageDTO> from = cq.from(MessageDTO.class);
        try {
            cq.select(from);
            cq.where(cb.and(cb.equal(from.get("chatRoom"), chatRoom)));
            cq.orderBy(cb.desc(from.get("date")));
            TypedQuery<MessageDTO> q = em.createQuery(cq);
            List<MessageDTO> messages = q.getResultList();
            return messages;
        } finally {
            em.close();
        }

    }

    public boolean delete(Integer id) throws MessageDaoException {

        EntityManager entityManager = FACTORY.createEntityManager();
        entityManager.getTransaction().begin();
        MessageDTO messageDTO = getEntityById(id);

        entityManager.remove(entityManager.contains(messageDTO) ? messageDTO : entityManager.merge(messageDTO));
//        entityManager.remove(messageDTO);
        entityManager.getTransaction().commit();
        entityManager.close();
        return true;
    }

    public MessageDTO getEntityById(Integer id) {
        EntityManager entityManager = FACTORY.createEntityManager();
        TypedQuery<MessageDTO> query = entityManager.createQuery(
                "SELECT message FROM MessageDTO message where message.id= :id", MessageDTO.class);
        try {
            MessageDTO messageDTO = query.setParameter("id", id).getSingleResult();
            return messageDTO;
        } finally {
            entityManager.close();
        }
    }

    public boolean create(Message entity) throws MessageDaoException {

        EntityManager entityManager = FACTORY.createEntityManager();
        entityManager.getTransaction().begin();

        MessageDTO messageDTO = new MessageDTO();

        UserDAO userDAO = new UserDAO();
        try {

            messageDTO.setDate(entity.getDate());
            messageDTO.setToUser(userDAO.getEntityById(entity.getToUser().getUserID()));
            messageDTO.setFromUser(userDAO.getEntityById(entity.getFromUser().getUserID()));

            messageDTO.setBodyText(entity.getBodyText());
            messageDTO.setChatRoom(entity.getChatRoom());
            messageDTO.setViewed(entity.isViewed());

            messageDTO = entityManager.merge(messageDTO);
            entityManager.getTransaction().commit();

        } catch (UserDaoException e) {
            logger.error(e);
            throw new MessageDaoException();
        } finally {
            entityManager.close();
        }
        return true;
    }
}
