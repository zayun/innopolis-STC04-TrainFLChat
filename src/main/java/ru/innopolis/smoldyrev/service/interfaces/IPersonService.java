package ru.innopolis.smoldyrev.service.interfaces;

import ru.innopolis.smoldyrev.common.exceptions.PersonServiceException;
import ru.innopolis.smoldyrev.models.pojo.Person;

public interface IPersonService  {
    Person update(Person person) throws PersonServiceException;
}
