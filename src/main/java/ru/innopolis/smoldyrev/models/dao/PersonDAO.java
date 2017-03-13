package ru.innopolis.smoldyrev.models.dao;

import org.springframework.stereotype.Repository;
import ru.innopolis.smoldyrev.common.exceptions.PersonDaoException;
import ru.innopolis.smoldyrev.models.connector.DatabaseManager;
import ru.innopolis.smoldyrev.models.dao.interfaces.IPersonDAO;
import ru.innopolis.smoldyrev.models.pojo.Person;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smoldyrev on 23.02.17.
 */
@Repository
public class PersonDAO implements IPersonDAO{

    private static Logger logger = Logger.getLogger(PersonDAO.class);

    private static final String SQL_SELECT_ALL_PERSON = "SELECT * FROM main.d_persons";

    private static final String SQL_INSERT_PERSON = "INSERT INTO main.d_persons(\n" +
            "\tfirst_name, last_name, birthday, email, phone_number, male)\n" +
            "\tVALUES (?, ?, ?, ?, ?, ?) RETURNING person_id;";

    private static final String SQL_UPDATE_PERSON = "UPDATE main.d_persons\n" +
            "\tSET first_name=?, last_name=?, birthday=?, email=?, phone_number=?, male=?\n" +
            "\tWHERE d_persons.person_id=?;";

    public List<Person> getAll() throws PersonDaoException {

        List<Person> persones = new ArrayList<>();

        try (Statement statement = DatabaseManager.getStatement()){
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_PERSON);
            if (resultSet.next()) {
                persones.add(new Person(
                        resultSet.getInt("personID"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number"),
                        resultSet.getDate("birthday"),
                        resultSet.getBoolean("male")));
            } else {
                logger.debug("pesones not found");
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new PersonDaoException();
        }

        return persones;
    }

    public Person update(Person entity) throws PersonDaoException {

        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_UPDATE_PERSON)){
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setDate(3, entity.getBirthday());
            preparedStatement.setString(4, entity.getEmail());
            preparedStatement.setString(5, entity.getPhoneNumber());
            preparedStatement.setBoolean(6, new Boolean(entity.isMale()));
            preparedStatement.setInt(7, entity.getId());

            preparedStatement.executeUpdate();
            logger.debug(entity.getId() + " person was update");
            return entity;
        } catch (SQLException e) {
            logger.error(e);
            throw new PersonDaoException();
        }
    }

    public Person getEntityById(Integer id) {
        return null;
    }

    /**
     * Вставляет в таблицу значение полей полученной сущности
     * после вставки устанавливает ключевое поле сущности,
     * в значение, полученное от сервера
     *
     * @return true - если все успешно прошло
     */
    public boolean create(Person entity) throws PersonDaoException {


        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_INSERT_PERSON)){
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setDate(3, new Date(entity.getBirthday().getTime()));
            preparedStatement.setString(4, entity.getEmail());
            preparedStatement.setString(5, entity.getPhoneNumber());
            preparedStatement.setBoolean(6, entity.isMale());

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                entity.setId(rs.getInt("person_id"));
            }
            logger.debug(entity.getId() + "/" + entity.getFirstName() + "/ person was insert");
            return true;
        } catch (SQLException e) {
            logger.error(e);
            throw new PersonDaoException();
        } catch (NullPointerException e) {
            logger.error(e);
            throw new PersonDaoException();
        }
    }
}
