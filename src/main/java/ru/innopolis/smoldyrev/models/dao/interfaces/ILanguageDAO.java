package ru.innopolis.smoldyrev.models.dao.interfaces;

import ru.innopolis.smoldyrev.common.exceptions.LanguageDaoException;
import ru.innopolis.smoldyrev.models.dto.LanguageDTO;
import ru.innopolis.smoldyrev.models.pojo.LangOwner;

import java.util.List;

/**
 * Created by smoldyrev on 09.03.17.
 */
public interface ILanguageDAO {

    LanguageDTO getEntityById(String short_name);

    boolean createLangOwner(LangOwner entity) throws LanguageDaoException;

}
