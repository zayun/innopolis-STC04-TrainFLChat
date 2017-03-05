package ru.innopolis.smoldyrev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.innopolis.smoldyrev.common.exceptions.PersonDaoException;
import ru.innopolis.smoldyrev.common.exceptions.UserDaoException;
import ru.innopolis.smoldyrev.common.exceptions.UserNotFoundException;
import ru.innopolis.smoldyrev.common.exceptions.UserServiceException;
import ru.innopolis.smoldyrev.models.dao.PersonDAO;
import ru.innopolis.smoldyrev.models.dao.UserDAO;
import ru.innopolis.smoldyrev.models.pojo.User;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by smoldyrev on 23.02.17.
 * Сервис работы с пользователями
 */
@Service
public class UserService {

    private static Logger logger = Logger.getLogger(UserService.class);

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PersonDAO personDAO;

    public User authorize(String login, String password) throws UserServiceException {
        try {
            return (userDAO.getUserByLoginAndPassword(login, password));
        } catch (UserDaoException e) {
            logger.error(e);
            throw new UserServiceException();
        }
    }

    public boolean registration(User user) throws UserServiceException {

        try {
            personDAO.create(user.getPerson());
            return (userDAO.create(user));
        } catch (UserDaoException e) {
            logger.error(e);
            throw new UserServiceException();
        } catch (PersonDaoException e) {
            logger.error(e);
            throw new UserServiceException();
        }
    }

    public User getUserById(int id) throws UserServiceException, UserNotFoundException {
        try {
            User user= userDAO.getEntityById(id);

            if (user == null)
                throw new UserNotFoundException();

            return user;
        } catch (UserDaoException e) {
            logger.error(e);
            throw new UserServiceException();
        } catch (UserNotFoundException e) {
            logger.error(e);
            throw new UserNotFoundException();
        }
    }

    public List<User> getAll() throws UserServiceException {
        try {
            return userDAO.getAll();
        } catch (UserDaoException e) {
            logger.error(e);
            throw new UserServiceException();
        }
    }

    public User update(User user) throws UserServiceException, UserNotFoundException {
        try {
            user = userDAO.update(user);

            if (user == null)
                throw new UserNotFoundException();

            return user;
        } catch (UserDaoException e) {
            logger.error(e);
            throw new UserServiceException();
        } catch (UserNotFoundException e) {
            logger.error(e);
            throw new UserNotFoundException();
        }
    }
}
