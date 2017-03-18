package ru.innopolis.smoldyrev.models.dao.interfaces;

import ru.innopolis.smoldyrev.common.exceptions.PersonDaoException;
import ru.innopolis.smoldyrev.models.dto.PersonDTO;
import ru.innopolis.smoldyrev.models.pojo.Person;

import java.util.List;

/**
 * Created by smoldyrev on 09.03.17.
 */
public interface IPersonDAO {

    List<PersonDTO> getAll() throws PersonDaoException;

    PersonDTO getEntityById(Integer id);

    PersonDTO update(Person entity) throws PersonDaoException;

    boolean create(Person entity) throws PersonDaoException;

}
