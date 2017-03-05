package ru.innopolis.smoldyrev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.smoldyrev.common.exceptions.PersonDaoException;
import ru.innopolis.smoldyrev.common.exceptions.PersonServiceException;
import ru.innopolis.smoldyrev.models.dao.PersonDAO;
import ru.innopolis.smoldyrev.models.pojo.Person;
import org.apache.log4j.Logger;

/**
 * Created by smoldyrev on 27.02.17.
 * Сервис по работе с сущностями таблицы d_Persones
 */
@Service
public class PersonService {

    private static Logger logger = Logger.getLogger(PersonService.class);

    @Autowired
    private PersonDAO personDAO;

    public Person getPersonOnId(int personId) {
        return personDAO.getEntityById(personId);
    }

    public Person update(Person person) throws PersonServiceException {
        try {
            person = personDAO.update(person);
            return person;
        } catch (PersonDaoException e) {
            logger.error(e);
            throw new PersonServiceException();
        }
    }
}
