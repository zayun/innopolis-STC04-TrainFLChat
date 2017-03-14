package ru.innopolis.smoldyrev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.smoldyrev.common.exceptions.PersonDaoException;
import ru.innopolis.smoldyrev.common.exceptions.PersonServiceException;
import ru.innopolis.smoldyrev.models.dao.interfaces.IPersonDAO;
import ru.innopolis.smoldyrev.models.pojo.Person;
import org.apache.log4j.Logger;
import ru.innopolis.smoldyrev.service.interfaces.IPersonService;

/**
 * Created by smoldyrev on 27.02.17.
 * Сервис по работе с сущностями таблицы d_persones
 */
@Service
public class PersonService implements IPersonService {

    private static Logger logger = Logger.getLogger(PersonService.class);


    private IPersonDAO personDAO;

    @Autowired
    private void setPersonDAO(IPersonDAO personDAO) {
        this.personDAO = personDAO;
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
