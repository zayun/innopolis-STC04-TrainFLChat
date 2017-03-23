package ru.innopolis.smoldyrev.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.smoldyrev.models.entity.UserEntity;

/**
 * Created by smoldyrev on 21.03.17.
 */

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByLoginAndPassword(@Param("login") String login, @Param("password") String password);

    UserEntity findByLogin(@Param("login") String login);

}

