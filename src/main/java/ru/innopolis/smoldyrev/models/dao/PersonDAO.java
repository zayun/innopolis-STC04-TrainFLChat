package ru.innopolis.smoldyrev.models.dao;

import org.springframework.stereotype.Repository;
import ru.innopolis.smoldyrev.common.exceptions.PersonDaoException;
import ru.innopolis.smoldyrev.models.connector.DatabaseManager;
import ru.innopolis.smoldyrev.models.dao.interfaces.IPersonDAO;
import ru.innopolis.smoldyrev.models.dto.PersonDTO;
import ru.innopolis.smoldyrev.models.dto.UserDTO;
import ru.innopolis.smoldyrev.models.pojo.Person;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smoldyrev on 23.02.17.
 */
@Repository
public class PersonDAO implements IPersonDAO{

    private static Logger logger = Logger.getLogger(PersonDAO.class);

    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory("LFLChat");

    public List<PersonDTO> getAll() throws PersonDaoException {

        EntityManager em = FACTORY.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<PersonDTO> cq = cb.createQuery(PersonDTO.class);
        Root<PersonDTO> from = cq.from(PersonDTO.class);

        cq.select(from);
        TypedQuery<PersonDTO> q = em.createQuery(cq);
        List<PersonDTO> persones = q.getResultList();

        return persones;
    }

    public PersonDTO update(Person entity) throws PersonDaoException {

        EntityManager entityManager = FACTORY.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            PersonDTO personDTO = getEntityById(entity.getId());
            personDTO.setFirstName(entity.getFirstName());
            personDTO.setLastName(entity.getLastName());
            personDTO.setBirthday(entity.getBirthday());
            personDTO.setEmail(entity.getEmail());
            personDTO.setPhoneNumber(entity.getPhoneNumber());
            personDTO.setMale(entity.isMale());

            personDTO = entityManager.merge(personDTO);
            entityManager.getTransaction().commit();

            return personDTO;
        }finally {
            entityManager.close();
        }
    }

    public PersonDTO getEntityById(Integer id) {
        EntityManager entityManager = FACTORY.createEntityManager();

        PersonDTO personDTO = (PersonDTO) entityManager.find(PersonDTO.class, id);
        return personDTO;
    }

    /**
     * Вставляет в таблицу значение полей полученной сущности
     * после вставки устанавливает ключевое поле сущности,
     * в значение, полученное от сервера
     *
     * @return true - если все успешно прошло
     */
    public boolean create(Person entity) throws PersonDaoException {

        EntityManager entityManager = FACTORY.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            PersonDTO personDTO = new PersonDTO();

            personDTO.setId(entity.getId());
            personDTO.setFirstName(entity.getFirstName());
            personDTO.setLastName(entity.getLastName());
            personDTO.setEmail(entity.getEmail());
            personDTO.setPhoneNumber(entity.getPhoneNumber());
            personDTO.setMale(entity.isMale());
            personDTO.setBirthday(entity.getBirthday());

            personDTO = entityManager.merge(personDTO);
            entityManager.getTransaction().commit();

            entity.setId(personDTO.getId());

            return true;
        }finally {
            entityManager.close();
        }
    }
}
