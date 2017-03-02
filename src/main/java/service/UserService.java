package service;

import exceptions.PersonDaoException;
import exceptions.UserDaoException;
import exceptions.UserNotFoundException;
import exceptions.UserServiceException;
import models.dao.PersonDAO;
import models.dao.UserDAO;
import models.pojo.User;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by smoldyrev on 23.02.17.
 * Сервис работы с пользователями
 */
public class UserService {

    private static Logger logger = Logger.getLogger(UserService.class);

    private static UserDAO userDAO = new UserDAO();

    private static PersonDAO personDAO = new PersonDAO();

    public static User authorize(String login, String password) throws UserServiceException {
        try {
            return (userDAO.getUserByLoginAndPassword(login, password));
        } catch (UserDaoException e) {
            logger.error(e);
            throw new UserServiceException();
        }
    }

    public static boolean registration(User user) throws UserServiceException {


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

    public static User getUserById(int id) throws UserServiceException, UserNotFoundException {
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

    public static List<User> getAll() throws UserServiceException {
        try {
            return userDAO.getAll();
        } catch (UserDaoException e) {
            logger.error(e);
            throw new UserServiceException();
        }
    }

    public static User update(User user) throws UserServiceException, UserNotFoundException {
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
