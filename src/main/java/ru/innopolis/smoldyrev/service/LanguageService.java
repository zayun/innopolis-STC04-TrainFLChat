package ru.innopolis.smoldyrev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.smoldyrev.common.exceptions.LanguageDaoException;
import ru.innopolis.smoldyrev.common.exceptions.LanguageServiceException;
import ru.innopolis.smoldyrev.common.exceptions.NotifyDaoException;
import ru.innopolis.smoldyrev.common.exceptions.NotifyServiceException;
import ru.innopolis.smoldyrev.models.dao.interfaces.ILanguageDAO;
import ru.innopolis.smoldyrev.models.dao.interfaces.IPersonDAO;
import ru.innopolis.smoldyrev.models.dto.DtoTransformer;
import ru.innopolis.smoldyrev.models.dto.Transformer;
import ru.innopolis.smoldyrev.models.pojo.LangOwner;
import org.apache.log4j.Logger;
import ru.innopolis.smoldyrev.models.pojo.Language;
import ru.innopolis.smoldyrev.models.pojo.Notifyer;
import ru.innopolis.smoldyrev.models.pojo.Person;
import ru.innopolis.smoldyrev.service.interfaces.ILanguageService;


import java.util.List;
import java.util.Set;

/**
 * Created by smoldyrev on 27.02.17.
 * Сервис работы с сущностями таблиц d_languages и r_langowner
 */
@Service
public class LanguageService implements ILanguageService {

    private static Logger logger = Logger.getLogger(LanguageService.class);

    private ILanguageDAO languageDAO;
    private IPersonDAO personDAO;

    @Autowired
    public void setLanguageDAO(ILanguageDAO languageDAO) {
        this.languageDAO = languageDAO;
    }

    @Autowired
    public void setPersonDAO(IPersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public Set<Language> getLanguagesOnPerson(Integer personId) throws LanguageServiceException {
        Person person = Transformer.personEntityToPojo(personDAO.getEntityById(personId));
        return person.getLanguages();
    }

    public void create(LangOwner langOwner) throws LanguageServiceException {

        try {
            languageDAO.createLangOwner(langOwner);
        } catch (LanguageDaoException e) {
            logger.error(e);
            throw new LanguageServiceException(e.getMessage());
        }

    }
}
