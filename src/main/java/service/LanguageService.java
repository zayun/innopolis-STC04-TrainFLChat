package service;

import models.dao.LanguageDAO;
import models.pojo.LangOwner;


import java.util.List;

/**
 * Created by smoldyrev on 27.02.17.
 * Сервис работы с сущностями таблиц d_Languages и r_LangOwners
 */
public class LanguageService {

    private static LanguageDAO languageDAO = new LanguageDAO();

    public static List<LangOwner> getLanguagesOnPerson(int personId) {

        return languageDAO.getLanguagesOnPerson(personId);
    }
}
