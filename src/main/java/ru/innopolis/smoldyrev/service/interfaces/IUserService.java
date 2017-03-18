package ru.innopolis.smoldyrev.service.interfaces;

import ru.innopolis.smoldyrev.common.exceptions.UserNotFoundException;
import ru.innopolis.smoldyrev.common.exceptions.UserServiceException;
import ru.innopolis.smoldyrev.models.pojo.User;

import java.util.List;

/**
 * Created by smoldyrev on 09.03.17.
 */
public interface IUserService {

    boolean registration(User user) throws UserServiceException;

    User getUserById(int id) throws UserServiceException, UserNotFoundException;

    List<User> getAll() throws UserServiceException;

    List<User> getAllInConverse(int converse) throws UserServiceException;

    User update(User user) throws UserServiceException, UserNotFoundException;


}
