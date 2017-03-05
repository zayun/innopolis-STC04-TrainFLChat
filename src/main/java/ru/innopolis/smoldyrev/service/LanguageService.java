package ru.innopolis.smoldyrev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.smoldyrev.common.exceptions.LanguageDaoException;
import ru.innopolis.smoldyrev.common.exceptions.LanguageServiceException;
import ru.innopolis.smoldyrev.models.dao.LanguageDAO;
import ru.innopolis.smoldyrev.models.pojo.LangOwner;
import org.apache.log4j.Logger;


import java.util.List;

/**
 * Created by smoldyrev on 27.02.17.
 * Сервис работы с сущностями таблиц d_Languages и r_LangOwners
 */
@Service
public class LanguageService {

    private static Logger logger = Logger.getLogger(LanguageService.class);

    @Autowired(required = true)
    private LanguageDAO languageDAO;

    public List<LangOwner> getLanguagesOnPerson(int personId) throws LanguageServiceException {

        try {
            return languageDAO.getLanguagesOnPerson(personId);
        } catch (LanguageDaoException e) {
            logger.error(e);
            throw new LanguageServiceException();
        }
    }
}
