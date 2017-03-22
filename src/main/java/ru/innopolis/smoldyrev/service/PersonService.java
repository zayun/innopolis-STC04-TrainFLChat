package ru.innopolis.smoldyrev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.smoldyrev.common.exceptions.PersonDaoException;
import ru.innopolis.smoldyrev.common.exceptions.PersonServiceException;
import ru.innopolis.smoldyrev.models.dao.interfaces.IPersonDAO;
import ru.innopolis.smoldyrev.models.dto.Transformer;
import ru.innopolis.smoldyrev.models.pojo.Person;
import org.apache.log4j.Logger;
import ru.innopolis.smoldyrev.models.repository.PersonRepository;
import ru.innopolis.smoldyrev.service.interfaces.IPersonService;

/**
 * Created by smoldyrev on 27.02.17.
 * Сервис по работе с сущностями таблицы d_persones
 */
@Service
public class PersonService implements IPersonService {

    private static Logger logger = Logger.getLogger(PersonService.class);

    private PersonRepository personRepository;

    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person update(Person person) throws PersonServiceException {
        try {
            person = Transformer.person(personRepository.saveAndFlush(Transformer.person(person)));
            return person;
        } catch (Exception e) {
            logger.error(e);
            throw new PersonServiceException();
        }
    }
}
