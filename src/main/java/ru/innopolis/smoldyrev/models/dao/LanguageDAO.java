package ru.innopolis.smoldyrev.models.dao;

import org.springframework.stereotype.Repository;
import ru.innopolis.smoldyrev.common.exceptions.LanguageDaoException;
import ru.innopolis.smoldyrev.models.connector.DatabaseManager;
import ru.innopolis.smoldyrev.models.pojo.LangOwner;
import ru.innopolis.smoldyrev.models.pojo.Language;
import ru.innopolis.smoldyrev.models.pojo.Person;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smoldyrev on 24.02.17.
 */
@Repository
public class LanguageDAO {

    private static Logger logger = Logger.getLogger(LanguageDAO.class);

    private static final String SQL_LANG_ON_PERSON = "SELECT * FROM \"Main\".\"r_LangOwner\"\n" +
            "LEFT JOIN \"Main\".\"d_Languages\" ON \"r_LangOwner\".\"idLang\" = \"d_Languages\".\"ShortName\"\n" +
            "WHERE \"idPerson\"=?";

    public List<LangOwner> getLanguagesOnPerson(int personId) throws LanguageDaoException {
        List<LangOwner> langOwners = new ArrayList<>();

        Person person = getPerson(personId);

        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_LANG_ON_PERSON)) {
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
            DatabaseManager.closePrepareStatement(preparedStatement);
        } catch (SQLException e) {
            logger.error(e);
            throw new LanguageDaoException();
        }
        return langOwners;
    }

    private Person getPerson(int id) {

        PersonDAO personDAO = new PersonDAO();

        return personDAO.getEntityById(id);

    }
}
