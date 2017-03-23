package ru.innopolis.smoldyrev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.innopolis.smoldyrev.common.exceptions.UserNotFoundException;
import ru.innopolis.smoldyrev.common.exceptions.UserServiceException;
import ru.innopolis.smoldyrev.models.dto.Transformer;
import ru.innopolis.smoldyrev.models.entity.ConversationEntity;
import ru.innopolis.smoldyrev.models.pojo.Person;
import ru.innopolis.smoldyrev.models.pojo.User;
import org.apache.log4j.Logger;
import ru.innopolis.smoldyrev.models.repository.ConverseRepository;
import ru.innopolis.smoldyrev.models.repository.UserRepository;
import ru.innopolis.smoldyrev.service.interfaces.IUserService;

import java.util.List;

/**
 * Created by smoldyrev on 23.02.17.
 * Сервис работы с пользователями
 */
@Service
public class UserService implements IUserService {

    private static Logger logger = Logger.getLogger(UserService.class);
    private UserRepository userRepository;
    private ConverseRepository converseRepository;


    private BCryptPasswordEncoder bcryptEncoder;


    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setConverseRepository(ConverseRepository converseRepository) {
        this.converseRepository = converseRepository;
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
            return (userRepository.saveAndFlush(Transformer.user(user))!=null);
        } catch (Exception e) {
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
            User user = Transformer.user(userRepository.findOne(id));

            if (user == null)
                throw new UserNotFoundException();

            return user;
        } catch (Exception e) {
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
            return Transformer.user(userRepository.findAll());
        } catch (Exception e) {
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

        ConversationEntity conv = converseRepository.findOne(converse);
        Transformer.user(conv.getUsers());
        try {
            return Transformer.user(conv.getUsers());
        } catch (Exception e) {
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
        return updateWp(user);

    }

    /**
     * Обновить данные пользователя
     *  административный доступ
     *  пароль не меняется
     * @throws UserServiceException при любых проблемах
     */
    public User updateWp(User user) throws UserServiceException, UserNotFoundException {
        try {
            user = Transformer.user(userRepository.saveAndFlush(Transformer.user(user)));

            if (user == null)
                throw new UserNotFoundException();
            return user;
        } catch (Exception e) {
            logger.error(e);
            throw new UserServiceException();
        }
    }

    @Autowired
    public void setBcryptEncoder(BCryptPasswordEncoder bcryptEncoder) {
        this.bcryptEncoder = bcryptEncoder;
    }
}
