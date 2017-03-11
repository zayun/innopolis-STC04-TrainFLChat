package ru.innopolis.smoldyrev.service;

import ru.innopolis.smoldyrev.common.exceptions.LanguageServiceException;
import ru.innopolis.smoldyrev.models.pojo.LangOwner;

import java.util.List;

/**
 * Created by smoldyrev on 09.03.17.
 */

public interface ILanguageService {
        List<LangOwner> getLanguagesOnPerson(int personId) throws LanguageServiceException;
}
