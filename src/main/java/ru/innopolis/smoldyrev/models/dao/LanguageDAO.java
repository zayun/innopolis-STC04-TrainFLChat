package ru.innopolis.smoldyrev.models.dao;

import org.springframework.stereotype.Repository;
import ru.innopolis.smoldyrev.common.exceptions.LanguageDaoException;
import ru.innopolis.smoldyrev.models.dao.interfaces.ILanguageDAO;
import ru.innopolis.smoldyrev.models.entity.LanguageEntity;
import ru.innopolis.smoldyrev.models.pojo.LangOwner;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Repository
public class LanguageDAO implements ILanguageDAO {

    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory("LFLChat");

    private static Logger logger = Logger.getLogger(LanguageDAO.class);

    public boolean createLangOwner(LangOwner entity) throws LanguageDaoException {
        return false;
    }

    public LanguageEntity getEntityById(String short_name) {
        EntityManager entityManager = FACTORY.createEntityManager();

        LanguageEntity languageEntity = (LanguageEntity) entityManager.find(LanguageEntity.class, short_name);
        return languageEntity;

    }
}

