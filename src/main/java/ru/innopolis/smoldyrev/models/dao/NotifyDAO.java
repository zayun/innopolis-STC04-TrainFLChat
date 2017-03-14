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
            "FROM main.r_notifyer as nt\n" +
            "    LEFT JOIN main.d_users as us ON nt.user_id = us.user_id\n" +
            "    LEFT JOIN main.d_persons as ps ON us.person_id = ps.person_id\n" +
            "                WHERE not_type = ?";

    private static final String SQL_SELECT_ALL_NOTIFY = "SELECT *\n" +
            "\tFROM main.r_notifyer as nt\n" +
            "    LEFT JOIN main.d_users as us ON nt.user_id = us.user_id\n" +
            "    LEFT JOIN main.d_persons as ps ON us.person_id = ps.person_id";

    private static final String SQL_SELECT_BY_USER = "SELECT * \n" +
            "FROM main.r_notifyer as nt\n" +
            "    LEFT JOIN main.d_users as us ON nt.user_id = us.user_id\n" +
            "    LEFT JOIN main.d_persons as ps ON us.person_id = ps.person_id\n" +
            "WHERE us.user_id = ?";

    private static final String SQL_UPDATE_NOTIFY = "UPDATE main.r_notifyer\n" +
            "\tSET not_type=?\n" +
            "\tWHERE id=?";

    private static final String SQL_SELECT_BY_ID = "SELECT *\n" +
            "FROM main.r_notifyer as nt\n" +
            "    LEFT JOIN main.d_users as us ON nt.user_id = us.user_id\n" +
            "    LEFT JOIN main.d_persons as ps ON us.person_id = ps.person_id\n" +
            "    WHERE id = ?";

    private static final String SQL_INSERT_NOTIFY = "INSERT INTO main.r_notifyer(\n" +
            "\tuser_id, not_type)\n" +
            "\tVALUES (?, ?) RETURNING id;";

    private static final String SQL_DELETE_NOTIFY = "DELETE FROM main.r_notifyer\n" +
            "\tWHERE id = ?";

    public List<Notifyer> getAll() throws NotifyDaoException {
        List<Notifyer> notifyers = new ArrayList<>();


        try (Statement statement = DatabaseManager.getStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_NOTIFY);
            while (resultSet.next()) {

                notifyers.add(new Notifyer(
                        resultSet.getInt("id"),
                        getUser(resultSet),
                        resultSet.getString("no_type")));
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
                        getUser(resultSet), resultSet.getString("not_type"));
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
                        getUser(resultSet), resultSet.getString("not_type")));
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
                        getUser(resultSet), resultSet.getString("not_type")));
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
            Person person = new Person(resultSet.getInt("person_id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("email"),
                    resultSet.getString("phone_number"),
                    resultSet.getDate("birthday"),
                    resultSet.getBoolean("male"));

            user = new User(resultSet.getInt("user_id"),
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
