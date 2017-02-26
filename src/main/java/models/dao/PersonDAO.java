package models.dao;

import models.connector.DatabaseManager;
import models.pojo.Person;
import models.pojo.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smoldyrev on 23.02.17.
 */
public class PersonDAO extends AbstractDAO<Person, Integer> {

    private static Logger logger = Logger.getLogger(PersonDAO.class);

    private static final String SQL_SELECT_ALL_PERSON = "SELECT * FROM \"Main\".\"d_Persons\"";

    private static final String SQL_INSERT_PERSON = "INSERT INTO \"Main\".\"d_Persons\"(\n" +
            "\t\"FirstName\", \"LastName\", \"birthDay\", email, \"phoneNumber\", male)\n" +
            "\tVALUES (?, ?, ?, ?, ?, ?) RETURNING \"personID\";";

    private static final String SQL_UPDATE_PERSON = "UPDATE \"Main\".\"d_Persons\"\n" +
            "\tSET \"FirstName\"=?, \"LastName\"=?, \"birthDay\"=?, email=?, \"phoneNumber\"=?, male=?\n" +
            "\tWHERE \"d_Persons\".\"personID\"=?;";

    @Override
    public List<Person> getAll() {

        List<Person> persones = new ArrayList<>();
        Statement statement = getStatement();
        try  {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_PERSON);
            if (resultSet.next()) {
                persones.add(new Person(
                        resultSet.getInt("personID"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("email"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getDate("birthday"),
                        resultSet.getBoolean("male")));
            } else {
                logger.debug("pesones not found");
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closeStatement(statement);
        }

        return persones;
    }

    @Override
    public Person update(Person entity) {

        PreparedStatement preparedStatement = getPrepareStatement(SQL_UPDATE_PERSON);
        try {
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setDate(3, entity.getBirthDay());
            preparedStatement.setString(4, entity.getEmail());
            preparedStatement.setString(5, entity.getPhoneNumber());
            preparedStatement.setBoolean(6, new Boolean(entity.isMale()));
            preparedStatement.setInt(7, entity.getId());

            preparedStatement.setInt(5, entity.getId());
            preparedStatement.executeUpdate();
            logger.debug(entity.getId() + " person was update");
            return entity;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closePrepareStatement(preparedStatement);
        }
        logger.error(entity.getId() + " person was not updated");
        return null;
    }

    @Override
    public Person getEntityById(Integer id) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    /**
     * Вставляет в таблицу значение полей полученной сущности
     * после вставки устанавливает ключевое поле сущности,
     * в значение, полученное от сервера
     *
     * @return true - если все успешно прошло
     */
    @Override
    public boolean create(Person entity) {

        PreparedStatement preparedStatement = getPrepareStatement(SQL_INSERT_PERSON);
        try {
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setDate(3, new Date(entity.getBirthDay().getTime()));
            preparedStatement.setString(4, entity.getEmail());
            preparedStatement.setString(5, entity.getPhoneNumber());
            preparedStatement.setBoolean(6, entity.isMale());

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                entity.setId(rs.getInt("personID"));
            }
            logger.debug(entity.getId() + "/" + entity.getFirstName() + "/ person was insert");
            return true;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closePrepareStatement(preparedStatement);
        }
        return false;
    }
}
