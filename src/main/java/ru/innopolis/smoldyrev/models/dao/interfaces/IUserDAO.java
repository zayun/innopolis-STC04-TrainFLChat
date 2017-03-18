package ru.innopolis.smoldyrev.models.dao.interfaces;

import ru.innopolis.smoldyrev.common.exceptions.UserDaoException;
import ru.innopolis.smoldyrev.common.exceptions.UserServiceException;
import ru.innopolis.smoldyrev.models.dto.UserDTO;
import ru.innopolis.smoldyrev.models.pojo.User;

import java.util.List;
import java.util.Set;

/**
 * Created by smoldyrev on 09.03.17.
 */
public interface IUserDAO {

    List<UserDTO> getAll() throws UserDaoException;

    Set<UserDTO> getAllInConverse(int converse) throws UserDaoException;

    UserDTO getUserByLogin(String login) throws UserDaoException;

    UserDTO update(User entity) throws UserDaoException;

    UserDTO getEntityById(Integer id) throws UserDaoException;

    boolean create(User entity) throws UserDaoException;
}
