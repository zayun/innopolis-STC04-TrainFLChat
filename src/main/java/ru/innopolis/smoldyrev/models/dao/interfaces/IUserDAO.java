package ru.innopolis.smoldyrev.models.dao.interfaces;

import ru.innopolis.smoldyrev.common.exceptions.UserDaoException;
import ru.innopolis.smoldyrev.common.exceptions.UserServiceException;
import ru.innopolis.smoldyrev.models.pojo.User;

import java.util.List;

/**
 * Created by smoldyrev on 09.03.17.
 */
public interface IUserDAO {

    User getUserByLoginAndPassword(String login, String password) throws UserDaoException;

    List<User> getAll() throws UserDaoException;

    List<User> getAllInConverse(int converse) throws UserDaoException;

    User update(User entity) throws UserDaoException;

    User getEntityById(Integer id) throws UserDaoException;

    boolean create(User entity) throws UserDaoException;
}
