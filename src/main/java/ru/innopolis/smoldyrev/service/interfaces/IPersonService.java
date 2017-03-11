package ru.innopolis.smoldyrev.service;

import ru.innopolis.smoldyrev.common.exceptions.PersonServiceException;
import ru.innopolis.smoldyrev.models.pojo.Person;

/**
 * Created by smoldyrev on 09.03.17.
 */
public interface IPersonService  {

    Person getPersonOnId(int personId);

    Person update(Person person) throws PersonServiceException;
}
