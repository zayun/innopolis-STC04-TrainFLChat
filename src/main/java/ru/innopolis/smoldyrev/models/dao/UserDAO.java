package ru.innopolis.smoldyrev.models.dao;

import org.springframework.stereotype.Repository;
import ru.innopolis.smoldyrev.common.exceptions.UserDaoException;
import ru.innopolis.smoldyrev.models.dao.interfaces.IUserDAO;
import ru.innopolis.smoldyrev.models.dto.ConversationDTO;
import ru.innopolis.smoldyrev.models.dto.PersonDTO;
import ru.innopolis.smoldyrev.models.dto.UserDTO;
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
    public UserDTO getUserByLogin(String login) throws UserDaoException {

        EntityManager entityManager = FACTORY.createEntityManager();
        TypedQuery<UserDTO> query = entityManager.createQuery(
                "SELECT user FROM UserDTO user where user.login = :login", UserDTO.class);
        try {
            UserDTO userDTO = query.setParameter("login", login).getSingleResult();
            return userDTO;
        } finally {
            entityManager.close();
        }
    }

    public List<UserDTO> getAll() throws UserDaoException {

        EntityManager em = FACTORY.createEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();

        try {
            CriteriaQuery<UserDTO> cq = cb.createQuery(UserDTO.class);
            Root<UserDTO> from = cq.from(UserDTO.class);

            cq.select(from);
            cq.orderBy(cb.asc(from.get("login")));
            TypedQuery<UserDTO> q = em.createQuery(cq);
            List<UserDTO> users = q.getResultList();
            return users;
        } finally {
            em.close();
        }
    }

    public Set<UserDTO> getAllInConverse(int converse) throws UserDaoException {

        EntityManager entityManager = FACTORY.createEntityManager();
        TypedQuery<ConversationDTO> query = entityManager.createQuery(
                "SELECT converse FROM ConversationDTO converse where converse.id = :id", ConversationDTO.class);
        try {
            ConversationDTO conversation = query.setParameter("id", converse).getSingleResult();
            return conversation.getUsers();
        } finally {
            entityManager.close();
        }
    }

    public UserDTO update(User entity) throws UserDaoException {

        EntityManager entityManager = FACTORY.createEntityManager();
        entityManager.getTransaction().begin();

        try {

            UserDTO userDTO = getEntityById(entity.getUserID());
            userDTO.setLogin(entity.getLogin());
            userDTO.setPassword(entity.getPassword());
            userDTO.setUserType(entity.getUserType());
            userDTO.setBlocked(entity.isBlocked());
            userDTO.setPerson(userDTO.getPerson());
            entityManager.merge(userDTO);
            entityManager.getTransaction().commit();
            return userDTO;
        } finally {
            entityManager.close();
        }
    }

    public UserDTO getEntityById(Integer user_id) throws UserDaoException {
        EntityManager entityManager = FACTORY.createEntityManager();
        TypedQuery<UserDTO> query = entityManager.createQuery(
                "SELECT user FROM UserDTO user where user.userID = :user_id", UserDTO.class);
        try {
            UserDTO userDTO = query.setParameter("user_id", user_id).getSingleResult();
            userDTO.getPerson().getLanguages();
            return userDTO;
        } finally {
            entityManager.close();
        }
    }

    public boolean create(User entity) throws UserDaoException {

        EntityManager entityManager = FACTORY.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            UserDTO userDTO = new UserDTO();
            PersonDTO personDTO = new PersonDTO();

            personDTO.setId(entity.getPerson().getId());
            personDTO.setFirstName(entity.getPerson().getFirstName());
            personDTO.setLastName(entity.getPerson().getLastName());
            personDTO.setEmail(entity.getPerson().getEmail());
            personDTO.setPhoneNumber(entity.getPerson().getPhoneNumber());
            personDTO.setMale(entity.getPerson().isMale());
            personDTO.setBirthday(entity.getPerson().getBirthday());

            userDTO.setLogin(entity.getLogin());
            userDTO.setPassword(entity.getPassword());
            userDTO.setUserType(entity.getUserType());
            userDTO.setBlocked(entity.isBlocked());
            userDTO.setPerson(personDTO);

            userDTO = entityManager.merge(userDTO);
            entityManager.getTransaction().commit();

            return true;
        }finally {
            entityManager.close();
        }
    }
}

