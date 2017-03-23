package ru.innopolis.smoldyrev.models.dao.interfaces;

import ru.innopolis.smoldyrev.common.exceptions.UserDaoException;
import ru.innopolis.smoldyrev.models.entity.UserEntity;
import ru.innopolis.smoldyrev.models.pojo.User;

import java.util.List;
import java.util.Set;

/**
 * Created by smoldyrev on 09.03.17.
 */
public interface IUserDAO {

    List<UserEntity> getAll() throws UserDaoException;

    Set<UserEntity> getAllInConverse(int converse) throws UserDaoException;

    UserEntity getUserByLogin(String login) throws UserDaoException;

    UserEntity update(User entity) throws UserDaoException;

    UserEntity getEntityById(Integer id) throws UserDaoException;

    boolean create(User entity) throws UserDaoException;
}
