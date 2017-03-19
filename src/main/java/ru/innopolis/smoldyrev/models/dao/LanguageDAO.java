package ru.innopolis.smoldyrev.models.dao;

import org.springframework.stereotype.Repository;
import ru.innopolis.smoldyrev.common.exceptions.LanguageDaoException;
import ru.innopolis.smoldyrev.common.exceptions.UserDaoException;
import ru.innopolis.smoldyrev.models.dao.interfaces.ILanguageDAO;
import ru.innopolis.smoldyrev.models.dto.LanguageDTO;
import ru.innopolis.smoldyrev.models.pojo.LangOwner;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

@Repository
public class LanguageDAO implements ILanguageDAO {

    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory("LFLChat");

    private static Logger logger = Logger.getLogger(LanguageDAO.class);

    public boolean createLangOwner(LangOwner entity) throws LanguageDaoException {
        return false;
    }

    public LanguageDTO getEntityById(String short_name) {
        EntityManager entityManager = FACTORY.createEntityManager();
        TypedQuery<LanguageDTO> query = entityManager.createQuery(
                "SELECT lang FROM LanguageDTO lang where lang.shortName = :short_name", LanguageDTO.class);

        try {
            LanguageDTO languageDTO = query.setParameter("short_name", short_name).getSingleResult();
            return languageDTO;
        } finally {
            entityManager.close();
        }

    }
}

