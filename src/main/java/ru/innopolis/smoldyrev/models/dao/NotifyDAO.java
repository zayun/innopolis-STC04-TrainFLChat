package ru.innopolis.smoldyrev.models.dao;

import org.springframework.stereotype.Repository;
import ru.innopolis.smoldyrev.common.exceptions.NotifyDaoException;
import ru.innopolis.smoldyrev.models.connector.DatabaseManager;
import ru.innopolis.smoldyrev.models.dao.interfaces.INotifyDAO;
import ru.innopolis.smoldyrev.models.pojo.Notifyer;
import ru.innopolis.smoldyrev.models.pojo.Person;
import ru.innopolis.smoldyrev.models.pojo.User;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smoldyrev on 27.02.17.
 */
@Repository
public class NotifyDAO implements INotifyDAO{

    private static Logger logger = Logger.getLogger(NotifyDAO.class);

    private static final String SQL_SELECT_BY_TYPE = "SELECT *\n" +
            "FROM \"Main\".\"r_Notifyer\" as nt\n" +
            "    LEFT JOIN \"Main\".\"d_Users\" as us ON nt.\"userId\" = us.\"userID\"\n" +
            "    LEFT JOIN \"Main\".\"d_Persons\" as ps ON us.\"personID\" = ps.\"personID\"\n" +
            "                WHERE \"notType\" = ?";

    private static final String SQL_SELECT_ALL_NOTIFY = "SELECT *\n" +
            "\tFROM \"Main\".\"r_Notifyer\" as nt\n" +
            "    LEFT JOIN \"Main\".\"d_Users\" as us ON nt.\"userId\" = us.\"userID\"\n" +
            "    LEFT JOIN \"Main\".\"d_Persons\" as ps ON us.\"personID\" = ps.\"personID\"";

    private static final String SQL_SELECT_BY_USER = "SELECT * \n" +
            "FROM \"Main\".\"r_Notifyer\" as nt\n" +
            "    LEFT JOIN \"Main\".\"d_Users\" as us ON nt.\"userId\" = us.\"userID\"\n" +
            "    LEFT JOIN \"Main\".\"d_Persons\" as ps ON us.\"personID\" = ps.\"personID\"\n" +
            "WHERE \"userId\" = ?";

    private static final String SQL_UPDATE_NOTIFY = "UPDATE \"Main\".\"r_Notifyer\"\n" +
            "\tSET \"notType\"=?\n" +
            "\tWHERE id=?";

    private static final String SQL_SELECT_BY_ID = "SELECT *\n" +
            "FROM \"Main\".\"r_Notifyer\" as nt\n" +
            "    LEFT JOIN \"Main\".\"d_Users\" as us ON nt.\"userId\" = us.\"userID\"\n" +
            "    LEFT JOIN \"Main\".\"d_Persons\" as ps ON us.\"personID\" = ps.\"personID\"\n" +
            "    WHERE id = ?";

    private static final String SQL_INSERT_NOTIFY = "INSERT INTO \"Main\".\"r_Notifyer\"(\n" +
            "\t\"userId\", \"notType\")\n" +
            "\tVALUES (?, ?) RETURNING id;";

    private static final String SQL_DELETE_NOTIFY = "DELETE FROM \"Main\".\"r_Notifyer\"\n" +
            "\tWHERE id = ?";

    public List<Notifyer> getAll() throws NotifyDaoException {
        List<Notifyer> notifyers = new ArrayList<>();


        try (Statement statement = DatabaseManager.getStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_NOTIFY);
            while (resultSet.next()) {

                notifyers.add(new Notifyer(
                        resultSet.getInt("id"),
                        getUser(resultSet),
                        resultSet.getString("notType")));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new NotifyDaoException();
        }
        return notifyers;
    }

    public Notifyer update(Notifyer entity) throws NotifyDaoException {

        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_UPDATE_NOTIFY)) {
            preparedStatement.setString(1, entity.getNotType());
            preparedStatement.setInt(2, entity.getId());
            preparedStatement.executeUpdate();
            return entity;
        } catch (SQLException e) {
            logger.error(e);
            throw new NotifyDaoException();
        }
    }

    public Notifyer getEntityById(Integer id) throws NotifyDaoException {

        Notifyer notifyer = null;


        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                notifyer = new Notifyer(resultSet.getInt("id"),
                        getUser(resultSet), resultSet.getString("notType"));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new NotifyDaoException();
        }

        return notifyer;
    }

    public boolean delete(Integer id) throws NotifyDaoException {

        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_DELETE_NOTIFY)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error(e);
            throw new NotifyDaoException();
        }
    }

    public boolean create(Notifyer entity) throws NotifyDaoException {

        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_INSERT_NOTIFY)) {
            preparedStatement.setInt(1, entity.getUser().getUserID());
            preparedStatement.setString(2, entity.getNotType());

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                entity.setId(rs.getInt("id"));
            }
            return true;
        } catch (SQLException e) {
            logger.error(e);
            throw new NotifyDaoException();
        }
    }

    public List<Notifyer> getAllByNotType(String notyType) throws NotifyDaoException {

        List<Notifyer> notifyers = new ArrayList<>();
        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_SELECT_BY_TYPE)) {
            preparedStatement.setString(1, notyType);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                notifyers.add(new Notifyer(resultSet.getInt("id"),
                        getUser(resultSet), resultSet.getString("notType")));
            }

        } catch (SQLException e) {
            logger.error(e);
            throw new NotifyDaoException();
        }
        return notifyers;
    }

    public List<Notifyer> getAllByUser(int userId) throws NotifyDaoException {

        List<Notifyer> notifyers = new ArrayList<>();

        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_SELECT_BY_USER)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                notifyers.add(new Notifyer(resultSet.getInt("id"),
                        getUser(resultSet), resultSet.getString("notType")));
            }

        } catch (SQLException e) {
            logger.error(e);
            throw new NotifyDaoException();
        }
        return notifyers;
    }

    private User getUser(ResultSet resultSet) {
        User user = null;
        try {
            Person person = new Person(resultSet.getInt("personID"),
                    resultSet.getString("FirstName"),
                    resultSet.getString("LastName"),
                    resultSet.getString("email"),
                    resultSet.getString("phoneNumber"),
                    resultSet.getDate("birthDay"),
                    resultSet.getBoolean("male"));

            user = new User(resultSet.getInt("userID"),
                    resultSet.getString("usertype"),
                    resultSet.getString("login"),
                    resultSet.getString("pwd"),
                    person,
                    resultSet.getBoolean("blocked"));
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
