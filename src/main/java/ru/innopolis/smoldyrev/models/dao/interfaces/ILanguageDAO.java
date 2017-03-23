package ru.innopolis.smoldyrev.models.dao.interfaces;

import ru.innopolis.smoldyrev.common.exceptions.LanguageDaoException;
import ru.innopolis.smoldyrev.models.entity.LanguageEntity;
import ru.innopolis.smoldyrev.models.pojo.LangOwner;

/**
 * Created by smoldyrev on 09.03.17.
 */
public interface ILanguageDAO {

    LanguageEntity getEntityById(String short_name);

    boolean createLangOwner(LangOwner entity) throws LanguageDaoException;

}
