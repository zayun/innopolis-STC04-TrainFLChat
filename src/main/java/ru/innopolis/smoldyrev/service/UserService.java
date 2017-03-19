package ru.innopolis.smoldyrev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.innopolis.smoldyrev.common.exceptions.PersonDaoException;
import ru.innopolis.smoldyrev.common.exceptions.UserDaoException;
import ru.innopolis.smoldyrev.common.exceptions.UserNotFoundException;
import ru.innopolis.smoldyrev.common.exceptions.UserServiceException;
import ru.innopolis.smoldyrev.models.dao.interfaces.IPersonDAO;
import ru.innopolis.smoldyrev.models.dao.interfaces.IUserDAO;
import ru.innopolis.smoldyrev.models.dto.DtoTransformer;
import ru.innopolis.smoldyrev.models.dto.Transformer;
import ru.innopolis.smoldyrev.models.dto.UserDTO;
import ru.innopolis.smoldyrev.models.pojo.Person;
import ru.innopolis.smoldyrev.models.pojo.User;
import org.apache.log4j.Logger;
import ru.innopolis.smoldyrev.service.interfaces.IUserService;

import java.util.ArrayList;
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

    /**
     * Создает в базе нового
     * пользователя
     *
     * @throws UserServiceException при любых проблемах
     * @see User
     * @see Person
     */
    public boolean registration(User user) throws UserServiceException {

        try {
            user.setPassword(bcryptEncoder.encode(user.getPassword()));
            return (userDAO.create(user));
        } catch (UserDaoException e) {
            logger.error(e);
            throw new UserServiceException();
        }
    }

    /**
     * Получить пользователя по id
     *
     * @throws UserServiceException при любых проблемах
     */
    public User getUserById(int id) throws UserServiceException, UserNotFoundException {
        try {
            User user = Transformer.userEntityToPojo(userDAO.getEntityById(id));

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

    /**
     * Получить всех пользователей
     *
     * @throws UserServiceException при любых проблемах
     */
    public List<User> getAll() throws UserServiceException {
        try {
//            List<User> users = new ArrayList<>();
//
//            for (UserDTO u :
//                    userDAO.getAll()) {
//                users.add(DtoTransformer.transform(u));
//            }
            return Transformer.userEntityToPojo(userDAO.getAll());
        } catch (UserDaoException e) {
            logger.error(e);
            throw new UserServiceException();
        }
    }

    /**
     * Получить всех пользователей
     * в беседе
     *
     * @throws UserServiceException при любых проблемах
     * @see ru.innopolis.smoldyrev.models.pojo.Conversation
     */
    public List<User> getAllInConverse(int converse) throws UserServiceException {

        try {

            List<User> users = new ArrayList<>();

            for (UserDTO u :
                    userDAO.getAllInConverse(converse)) {
                users.add(Transformer.userEntityToPojo(u));
            }
            return users;
        } catch (UserDaoException e) {
            logger.error(e);
            throw new UserServiceException();
        }
    }

    /**
     * Обновить данные
     *
     * @throws UserServiceException при любых проблемах
     */
    public User update(User user) throws UserServiceException, UserNotFoundException {

        user.setPassword(bcryptEncoder.encode(user.getPassword()));

        try {
            user = Transformer.userEntityToPojo(userDAO.update(user));

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

    /**
     * Обновить данные пользователя
     *  административный доступ
     *  пароль не меняется
     * @throws UserServiceException при любых проблемах
     */
    public User updateAdm(User user) throws UserServiceException, UserNotFoundException {
        try {
            user = Transformer.userEntityToPojo(userDAO.update(user));

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
