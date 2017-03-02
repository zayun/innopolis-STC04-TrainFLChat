package service;

import exceptions.PersonDaoException;
import exceptions.PersonServiceException;
import models.dao.PersonDAO;
import models.pojo.Person;
import org.apache.log4j.Logger;

/**
 * Created by smoldyrev on 27.02.17.
 * Сервис по работе с сущностями таблицы d_Persones
 */
public class PersonService {

    private static Logger logger = Logger.getLogger(PersonService.class);

    private static PersonDAO personDAO = new PersonDAO();

    public static Person getPersonOnId(int personId) {
        return personDAO.getEntityById(personId);
    }

    public static Person update(Person person) throws PersonServiceException {
        try {
            person = personDAO.update(person);
            return person;
        } catch (PersonDaoException e) {
            logger.error(e);
            throw new PersonServiceException();
        }
    }
}
