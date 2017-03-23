package ru.innopolis.smoldyrev.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.smoldyrev.models.entity.PersonEntity;

/**
 * Created by smoldyrev on 21.03.17.
 */
@Repository
@Transactional
public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {

}
