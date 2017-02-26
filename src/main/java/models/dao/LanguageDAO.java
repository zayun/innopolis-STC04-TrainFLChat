package models.dao;

import models.pojo.LangOwner;
import models.pojo.Language;
import models.pojo.Person;
import models.pojo.User;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smoldyrev on 24.02.17.
 */
public class LanguageDAO extends AbstractDAO<Language, String> {

    private static Logger logger = Logger.getLogger(LanguageDAO.class);

    private static final String SQL_LANG_ON_PERSON = "SELECT * FROM \"Main\".\"r_LangOwner\"\n" +
            "LEFT JOIN \"Main\".\"d_Languages\" ON \"r_LangOwner\".\"idLang\" = \"d_Languages\".\"ShortName\"\n" +
            "WHERE \"idPerson\"=?";

    @Override
    public List<Language> getAll() {
        return null;
    }

    @Override
    public Language update(Language entity) {
        return null;
    }

    @Override
    public Language getEntityById(String id) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public boolean create(Language entity) {
        return false;
    }

    public List<LangOwner> getLanguagesOnPerson(int personId) {
        List<LangOwner> langOwners = new ArrayList<>();
        PersonDAO personDAO = new PersonDAO();
        Person person = personDAO.getEntityById(personId);
        PreparedStatement preparedStatement = getPrepareStatement(SQL_LANG_ON_PERSON);
        try {
            preparedStatement.setInt(1, personId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                LangOwner langOwner = new LangOwner(person,
                                new Language(
                                resultSet.getString("ShortName"),
                                resultSet.getString("FullName"),
                                resultSet.getString("dialekt")),
                                resultSet.getInt("level"));
                langOwners.add(langOwner);
            }
            closePrepareStatement(preparedStatement);
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closePrepareStatement(preparedStatement);
        }
        return langOwners;
    }
}
