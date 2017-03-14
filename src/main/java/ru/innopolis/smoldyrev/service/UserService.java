package ru.innopolis.smoldyrev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.innopolis.smoldyrev.common.exceptions.PersonDaoException;
import ru.innopolis.smoldyrev.common.exceptions.UserDaoException;
import ru.innopolis.smoldyrev.common.exceptions.UserNotFoundException;
import ru.innopolis.smoldyrev.common.exceptions.UserServiceException;
import ru.innopolis.smoldyrev.common.utilities.Crypt;
import ru.innopolis.smoldyrev.models.dao.interfaces.IPersonDAO;
import ru.innopolis.smoldyrev.models.dao.interfaces.IUserDAO;
import ru.innopolis.smoldyrev.models.pojo.Person;
import ru.innopolis.smoldyrev.models.pojo.User;
import org.apache.log4j.Logger;
import ru.innopolis.smoldyrev.service.interfaces.IUserService;

import java.util.List;

/**
 * Created by smoldyrev on 23.02.17.
 * Сервис работы с пользователями
 */
@Service
public class UserService implements IUserService {

    private static Logger logger = Logger.getLogger(UserService.class);
    private IUserDAO userDAO;
    private IPersonDAO personDAO;
    private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    private void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    private void setPersonDAO(IPersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public User authorize(String login, String password) throws UserServiceException {

        try {
            Thread.sleep(100);
            if (bcryptEncoder.matches(password,bcryptEncoder.encode(password))) {
                return (userDAO.getUserByLogin(login));
            } else return null;
        } catch (UserDaoException e) {
            logger.error(e);
            throw new UserServiceException("UserService trouble!");
        } catch (InterruptedException e) {
            logger.error(e);
            throw new UserServiceException("UserService trouble!");
        }
    }

    public boolean registration(User user) throws UserServiceException {

        try {
            user.setPassword(bcryptEncoder.encode(user.getPassword()));
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
            User user = userDAO.getEntityById(id);

            if (user == null)
                throw new UserNotFoundException();

            return user;
        } catch (UserDaoException e) {
            logger.error(e);
            throw new UserServiceException("");
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

    public List<User> getAllInConverse(int converse) throws UserServiceException{
        try {
            return userDAO.getAllInConverse(converse);
        } catch (UserDaoException e) {
            logger.error(e);
            throw new UserServiceException();
        }
    }

    public User update(User user) throws UserServiceException, UserNotFoundException {

        user.setPassword(Crypt.getCriptedPassword(user.getLogin(), user.getPassword()));

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

    @Autowired
    public void setBcryptEncoder(BCryptPasswordEncoder bcryptEncoder) {
        this.bcryptEncoder = bcryptEncoder;
    }
}
