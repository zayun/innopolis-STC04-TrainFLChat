package ru.innopolis.smoldyrev.common.support;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.innopolis.smoldyrev.common.exceptions.UserDaoException;

/**
 * Created by smoldyrev on 17.03.17.
 */
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails user = null;
        try {
            user = getUserByLogin(username);
        } catch (UserDaoException e) {
            logger.error(e);
        }

        return user;
    }
}
