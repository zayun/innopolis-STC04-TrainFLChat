package service;

import exceptions.LanguageDaoException;
import exceptions.LanguageServiceException;
import exceptions.MessageServiceException;
import models.dao.LanguageDAO;
import models.pojo.LangOwner;
import org.apache.log4j.Logger;


import java.util.List;

/**
 * Created by smoldyrev on 27.02.17.
 * Сервис работы с сущностями таблиц d_Languages и r_LangOwners
 */
public class LanguageService {

    private static Logger logger = Logger.getLogger(LanguageService.class);

    private static LanguageDAO languageDAO = new LanguageDAO();

    public static List<LangOwner> getLanguagesOnPerson(int personId) throws LanguageServiceException {

        try {
            return languageDAO.getLanguagesOnPerson(personId);
        } catch (LanguageDaoException e) {
            logger.error(e);
            throw new LanguageServiceException();
        }
    }
}
