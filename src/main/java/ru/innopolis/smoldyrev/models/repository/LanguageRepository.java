package ru.innopolis.smoldyrev.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.smoldyrev.models.dto.LanguageDTO;

/**
 * Created by smoldyrev on 22.03.17.
 */

@Repository
@Transactional
public interface LanguageRepository extends JpaRepository<LanguageDTO, String> {

}
