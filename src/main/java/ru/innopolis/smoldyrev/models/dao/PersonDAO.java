package ru.innopolis.smoldyrev.models.dao;

import org.springframework.stereotype.Repository;
import ru.innopolis.smoldyrev.common.exceptions.PersonDaoException;
import ru.innopolis.smoldyrev.models.dao.interfaces.IPersonDAO;
import ru.innopolis.smoldyrev.models.entity.PersonEntity;
import ru.innopolis.smoldyrev.models.pojo.Person;
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
 * Created by smoldyrev on 23.02.17.
 */
@Repository
public class PersonDAO implements IPersonDAO{

    private static Logger logger = Logger.getLogger(PersonDAO.class);

    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory("LFLChat");

    public List<PersonEntity> getAll() throws PersonDaoException {

        EntityManager em = FACTORY.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<PersonEntity> cq = cb.createQuery(PersonEntity.class);
        Root<PersonEntity> from = cq.from(PersonEntity.class);

        cq.select(from);
        TypedQuery<PersonEntity> q = em.createQuery(cq);
        List<PersonEntity> persones = q.getResultList();

        return persones;
    }

    public PersonEntity update(Person entity) throws PersonDaoException {

        EntityManager entityManager = FACTORY.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            PersonEntity personEntity = getEntityById(entity.getId());
            personEntity.setFirstName(entity.getFirstName());
            personEntity.setLastName(entity.getLastName());
            personEntity.setBirthday(entity.getBirthday());
            personEntity.setEmail(entity.getEmail());
            personEntity.setPhoneNumber(entity.getPhoneNumber());
            personEntity.setMale(entity.isMale());

            personEntity = entityManager.merge(personEntity);
            entityManager.getTransaction().commit();

            return personEntity;
        }finally {
            entityManager.close();
        }
    }

    public PersonEntity getEntityById(Integer id) {
        EntityManager entityManager = FACTORY.createEntityManager();

        PersonEntity personEntity = (PersonEntity) entityManager.find(PersonEntity.class, id);
        return personEntity;
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
            PersonEntity personEntity = new PersonEntity();

            personEntity.setId(entity.getId());
            personEntity.setFirstName(entity.getFirstName());
            personEntity.setLastName(entity.getLastName());
            personEntity.setEmail(entity.getEmail());
            personEntity.setPhoneNumber(entity.getPhoneNumber());
            personEntity.setMale(entity.isMale());
            personEntity.setBirthday(entity.getBirthday());

            personEntity = entityManager.merge(personEntity);
            entityManager.getTransaction().commit();

            entity.setId(personEntity.getId());

            return true;
        }finally {
            entityManager.close();
        }
    }
}
