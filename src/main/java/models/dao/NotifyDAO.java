package models.dao;

import models.pojo.Notifyer;
import models.pojo.User;
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
public class NotifyDAO extends AbstractDAO <Notifyer, Integer>{

    private static Logger logger = Logger.getLogger(NotifyDAO.class);

    private static final String SQL_SELECT_BY_TYPE = "SELECT id, \"userId\", \"notType\"\n" +
            "\tFROM \"Main\".\"r_Notifyer\"\n" +
            "    WHERE \"notType\" = ?;";

    private static final String SQL_SELECT_ALL_NOTIFY = "SELECT id, \"userId\", \"notType\"\n" +
            "\tFROM \"Main\".\"r_Notifyer\"\n";

    private static final String SQL_SELECT_BY_USER = "SELECT id, \"userId\", \"notType\"\n" +
            "\tFROM \"Main\".\"r_Notifyer\"\n" +
            "    WHERE \"userId\" = ?;";

    private static final String SQL_UPDATE_NOTIFY = "UPDATE \"Main\".\"r_Notifyer\"\n" +
            "\tSET \"notType\"=?\n" +
            "\tWHERE id=?";

    private static final String SQL_SELECT_BY_ID = "SELECT id, \"userId\", \"notType\"\n" +
            "\tFROM \"Main\".\"r_Notifyer\"\n" +
            "    WHERE \"id\" = ?;";

    private static final String SQL_INSERT_NOTIFY = "INSERT INTO \"Main\".\"r_Notifyer\"(\n" +
            "\t\"userId\", \"notType\")\n" +
            "\tVALUES (?, ?) RETURNING id;";

    private static final String SQL_DELETE_NOTIFY = "DELETE FROM \"Main\".\"r_Notifyer\"\n" +
            "\tWHERE id = ?" ;

    @Override
    public List<Notifyer> getAll() {
        List<Notifyer> notifyers = new ArrayList<>();

        UserDAO userDAO = new UserDAO();
        Statement statement = getStatement();
        try {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_NOTIFY);
            while (resultSet.next()) {
                User user = userDAO.getEntityById(resultSet.getInt("userId"));

                notifyers.add(new Notifyer(
                        resultSet.getInt("id"),
                        user,
                        resultSet.getString("notType")));
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closeStatement(statement);
        }
        return notifyers;
    }

    @Override
    public Notifyer update(Notifyer entity) {
        PreparedStatement preparedStatement = getPrepareStatement(SQL_UPDATE_NOTIFY);
        try {
            preparedStatement.setString(1, entity.getNotType());
            preparedStatement.setInt(2, entity.getId());
            preparedStatement.executeUpdate();
            return entity;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closePrepareStatement(preparedStatement);
        }
        return null;
    }

    @Override
    public Notifyer getEntityById(Integer id) {
        Notifyer notifyer = null;
        UserDAO userDAO = new UserDAO();
        PreparedStatement preparedStatement = getPrepareStatement(SQL_SELECT_BY_ID);
        try {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                User user = userDAO.getEntityById(resultSet.getInt("userId"));
                notifyer = new Notifyer(resultSet.getInt("id"),
                        user, resultSet.getString("notType"));
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closePrepareStatement(preparedStatement);
        }
        return notifyer;
    }

    @Override
    public boolean delete(Integer id) {
        PreparedStatement preparedStatement = getPrepareStatement(SQL_DELETE_NOTIFY);
        try {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closePrepareStatement(preparedStatement);
        }
        return false;
    }

    @Override
    public boolean create(Notifyer entity) {
        PreparedStatement preparedStatement = getPrepareStatement(SQL_INSERT_NOTIFY);
        try {
            preparedStatement.setInt(1, entity.getUser().getUserID());
            preparedStatement.setString(2, entity.getNotType());

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                entity.setId(rs.getInt("id"));
            }
            return true;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closePrepareStatement(preparedStatement);
        }
        return false;
    }

    public List<Notifyer> getAllByNotType(String notyType) {

        List<Notifyer> notifyers= new ArrayList<>();
        UserDAO userDAO = new UserDAO();

        PreparedStatement preparedStatement = getPrepareStatement(SQL_SELECT_BY_TYPE);

        try {
            preparedStatement.setString(1, notyType);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                User user = userDAO.getEntityById(resultSet.getInt("userId"));
                notifyers.add(new Notifyer(resultSet.getInt("id"),
                        user, resultSet.getString("notType")));
            }

        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closePrepareStatement(preparedStatement);
        }
        return notifyers;
    }

    public List<Notifyer> getAllByUser(int userId) {

        List<Notifyer> notifyers= new ArrayList<>();
        UserDAO userDAO = new UserDAO();

        PreparedStatement preparedStatement = getPrepareStatement(SQL_SELECT_BY_USER);

        try {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                User user = userDAO.getEntityById(resultSet.getInt("userId"));
                notifyers.add(new Notifyer(resultSet.getInt("id"),
                        user, resultSet.getString("notType")));
            }

        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closePrepareStatement(preparedStatement);
        }
        return notifyers;
    }
}
