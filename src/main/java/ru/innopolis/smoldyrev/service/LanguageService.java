package ru.innopolis.smoldyrev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.smoldyrev.common.exceptions.LanguageDaoException;
import ru.innopolis.smoldyrev.common.exceptions.LanguageServiceException;
import ru.innopolis.smoldyrev.common.exceptions.NotifyDaoException;
import ru.innopolis.smoldyrev.common.exceptions.NotifyServiceException;
import ru.innopolis.smoldyrev.models.dao.interfaces.ILanguageDAO;
import ru.innopolis.smoldyrev.models.pojo.LangOwner;
import org.apache.log4j.Logger;
import ru.innopolis.smoldyrev.models.pojo.Notifyer;
import ru.innopolis.smoldyrev.service.interfaces.ILanguageService;


import java.util.List;

/**
 * Created by smoldyrev on 27.02.17.
 * Сервис работы с сущностями таблиц d_languages и r_langowner
 */
@Service
public class LanguageService implements ILanguageService {

    private static Logger logger = Logger.getLogger(LanguageService.class);


    private ILanguageDAO languageDAO;

    @Autowired
    public void setLanguageDAO(ILanguageDAO languageDAO) {
        this.languageDAO = languageDAO;
    }

    public List<LangOwner> getLanguagesOnPerson(int personId) throws LanguageServiceException {

        try {
            return languageDAO.getLanguagesOnPerson(personId);
        } catch (LanguageDaoException e) {
            logger.error(e);
            throw new LanguageServiceException();
        }
    }

    public void create (LangOwner langOwner) throws LanguageServiceException {

        try {
            languageDAO.createLangOwner(langOwner);
        } catch (LanguageDaoException e) {
            logger.error(e);
            throw new LanguageServiceException(e.getMessage());
        }

    }
}
