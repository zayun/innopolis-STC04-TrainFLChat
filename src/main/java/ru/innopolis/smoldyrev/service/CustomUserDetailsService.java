package ru.innopolis.smoldyrev.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.innopolis.smoldyrev.common.exceptions.UserDaoException;
import ru.innopolis.smoldyrev.models.dao.interfaces.IUserDAO;
import ru.innopolis.smoldyrev.models.dto.Transformer;
import ru.innopolis.smoldyrev.models.repository.UserRepository;

/**
 * Created by smoldyrev on 16.03.17.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static Logger logger = Logger.getLogger(CustomUserDetailsService.class);

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails user = null;
        try {
            user = Transformer.user(userRepository.findByLogin(username));
        } catch (Exception e) {
            logger.error(e);
        }

        return user;
    }

}
