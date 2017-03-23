package ru.innopolis.smoldyrev.models.dao;

import org.springframework.stereotype.Repository;
import ru.innopolis.smoldyrev.common.exceptions.UserDaoException;
import ru.innopolis.smoldyrev.models.dao.interfaces.IUserDAO;
import ru.innopolis.smoldyrev.models.entity.ConversationEntity;
import ru.innopolis.smoldyrev.models.entity.PersonEntity;
import ru.innopolis.smoldyrev.models.entity.UserEntity;
import ru.innopolis.smoldyrev.models.pojo.User;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;

@Repository
public class UserDAO implements IUserDAO {

    private static Logger logger = Logger.getLogger(UserDAO.class);

    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory("LFLChat");

    /**
     * Получение пользователя для проверки авторизации
     * SpringSecurity
     *
     * @param login - логин пользователя
     * @throws UserDaoException
     */
    public UserEntity getUserByLogin(String login) throws UserDaoException {

        EntityManager entityManager = FACTORY.createEntityManager();
        TypedQuery<UserEntity> query = entityManager.createQuery(
                "SELECT user FROM UserEntity user where user.login = :login", UserEntity.class);
        try {
            UserEntity userEntity = query.setParameter("login", login).getSingleResult();
            System.out.println(userEntity.getPerson());
            return userEntity;
        } finally {
            entityManager.close();
        }
    }

    public List<UserEntity> getAll() throws UserDaoException {

        EntityManager em = FACTORY.createEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();

        try {
            CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
            Root<UserEntity> from = cq.from(UserEntity.class);

            cq.select(from);
            cq.orderBy(cb.asc(from.get("login")));
            TypedQuery<UserEntity> q = em.createQuery(cq);
            List<UserEntity> users = q.getResultList();
            return users;
        } finally {
            em.close();
        }
    }

    public Set<UserEntity> getAllInConverse(int converse) throws UserDaoException {

        EntityManager entityManager = FACTORY.createEntityManager();
        TypedQuery<ConversationEntity> query = entityManager.createQuery(
                "SELECT converse FROM ConversationEntity converse where converse.id = :id", ConversationEntity.class);
        try {
            ConversationEntity conversation = query.setParameter("id", converse).getSingleResult();
            return conversation.getUsers();
        } finally {
            entityManager.close();
        }
    }

    public UserEntity update(User entity) throws UserDaoException {

        EntityManager entityManager = FACTORY.createEntityManager();
        entityManager.getTransaction().begin();

        try {

            UserEntity userEntity = getEntityById(entity.getUserID());
            userEntity.setLogin(entity.getLogin());
            userEntity.setPassword(entity.getPassword());
            userEntity.setUserType(entity.getUserType());
            userEntity.setBlocked(entity.isBlocked());
            userEntity.setPerson(userEntity.getPerson());
            entityManager.merge(userEntity);
            entityManager.getTransaction().commit();
            return userEntity;
        } finally {
            entityManager.close();
        }
    }

    public UserEntity getEntityById(Integer user_id) throws UserDaoException {

        EntityManager entityManager = FACTORY.createEntityManager();

        UserEntity userEntity = (UserEntity) entityManager.find(UserEntity.class, user_id);
        return userEntity;
    }

    public boolean create(User entity) throws UserDaoException {

        EntityManager entityManager = FACTORY.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            UserEntity userEntity = new UserEntity();
            PersonEntity personEntity = new PersonEntity();

            personEntity.setId(entity.getPerson().getId());
            personEntity.setFirstName(entity.getPerson().getFirstName());
            personEntity.setLastName(entity.getPerson().getLastName());
            personEntity.setEmail(entity.getPerson().getEmail());
            personEntity.setPhoneNumber(entity.getPerson().getPhoneNumber());
            personEntity.setMale(entity.getPerson().isMale());
            personEntity.setBirthday(entity.getPerson().getBirthday());

            userEntity.setLogin(entity.getLogin());
            userEntity.setPassword(entity.getPassword());
            userEntity.setUserType(entity.getUserType());
            userEntity.setBlocked(entity.isBlocked());
            userEntity.setPerson(personEntity);

            userEntity = entityManager.merge(userEntity);
            entityManager.getTransaction().commit();

            return true;
        }finally {
            entityManager.close();
        }
    }
}

