package ru.innopolis.smoldyrev.service.interfaces;

import ru.innopolis.smoldyrev.common.exceptions.LanguageServiceException;
import ru.innopolis.smoldyrev.models.pojo.LangOwner;
import ru.innopolis.smoldyrev.models.pojo.Language;

import java.util.List;
import java.util.Set;

/**
 * Created by smoldyrev on 09.03.17.
 */

public interface ILanguageService {

        Set<Language> getLanguagesOnPerson(Integer personId) throws LanguageServiceException;
        boolean addLangToPerson(int personId, String language) throws LanguageServiceException;
}
