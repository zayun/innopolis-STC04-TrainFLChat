package ru.innopolis.smoldyrev.models.dao.interfaces;

import ru.innopolis.smoldyrev.common.exceptions.PersonDaoException;
import ru.innopolis.smoldyrev.models.pojo.Person;

import java.util.List;

/**
 * Created by smoldyrev on 09.03.17.
 */
public interface IPersonDAO {

    List<Person> getAll() throws PersonDaoException;

    Person update(Person entity) throws PersonDaoException;

    boolean create(Person entity) throws PersonDaoException;

}
