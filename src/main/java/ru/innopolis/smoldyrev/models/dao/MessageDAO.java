package ru.innopolis.smoldyrev.models.dao;

import org.springframework.stereotype.Repository;
import ru.innopolis.smoldyrev.common.exceptions.MessageDaoException;
import ru.innopolis.smoldyrev.common.exceptions.UserDaoException;
import ru.innopolis.smoldyrev.models.dao.interfaces.IMessageDAO;
import ru.innopolis.smoldyrev.models.entity.MessageEntity;
import ru.innopolis.smoldyrev.models.pojo.Message;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by smoldyrev on 25.02.17.
 */
@Repository
public class MessageDAO implements IMessageDAO {

    private static Logger logger = Logger.getLogger(MessageDAO.class);

    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory("LFLChat");


    public List<MessageEntity> getAll() throws MessageDaoException {

        EntityManager em = FACTORY.createEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<MessageEntity> cq = cb.createQuery(MessageEntity.class);
        Root<MessageEntity> from = cq.from(MessageEntity.class);

        try {
            cq.select(from);
            TypedQuery<MessageEntity> q = em.createQuery(cq);
            List<MessageEntity> messages = q.getResultList();
            return messages;
        } finally {
            em.close();
        }

    }

    public List<MessageEntity> getAllInRoom(int chatRoom) throws MessageDaoException {
        EntityManager em = FACTORY.createEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<MessageEntity> cq = cb.createQuery(MessageEntity.class);
        Root<MessageEntity> from = cq.from(MessageEntity.class);
        try {
            cq.select(from);
            cq.where(cb.and(cb.equal(from.get("chatRoom"), chatRoom)));
            cq.orderBy(cb.desc(from.get("date")));
            TypedQuery<MessageEntity> q = em.createQuery(cq);
            List<MessageEntity> messages = q.getResultList();
            return messages;
        } finally {
            em.close();
        }

    }

    public boolean delete(Integer id) throws MessageDaoException {

        EntityManager entityManager = FACTORY.createEntityManager();
        entityManager.getTransaction().begin();
        MessageEntity messageEntity = getEntityById(id);

//        entityManager.remove(entityManager.contains(messageEntity) ? messageEntity : entityManager.merge(messageEntity));
        entityManager.remove(messageEntity);
        entityManager.getTransaction().commit();
        entityManager.close();
        return true;
    }

    public MessageEntity getEntityById(Integer id) {
        EntityManager entityManager = FACTORY.createEntityManager();

        MessageEntity messageEntity = (MessageEntity) entityManager.find(MessageEntity.class, id);
        return messageEntity;
    }

    public boolean create(Message entity) throws MessageDaoException {

        EntityManager entityManager = FACTORY.createEntityManager();
        entityManager.getTransaction().begin();

        MessageEntity messageEntity = new MessageEntity();

        UserDAO userDAO = new UserDAO();
        try {

            messageEntity.setDate(entity.getDate());
            messageEntity.setToUser(userDAO.getEntityById(entity.getToUser().getUserID()));
            messageEntity.setFromUser(userDAO.getEntityById(entity.getFromUser().getUserID()));

            messageEntity.setBodyText(entity.getBodyText());
            messageEntity.setChatRoom(entity.getChatRoom());
            messageEntity.setViewed(entity.isViewed());

            messageEntity = entityManager.merge(messageEntity);
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
