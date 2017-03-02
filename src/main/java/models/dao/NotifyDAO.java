package models.dao;

import exceptions.NotifyDaoException;
import exceptions.UserNotFoundException;
import exceptions.UserServiceException;
import models.connector.DatabaseManager;
import models.pojo.Notifyer;
import models.pojo.User;
import org.apache.log4j.Logger;
import service.UserService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smoldyrev on 27.02.17.
 */
public class NotifyDAO {

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

    public List<Notifyer> getAll() throws NotifyDaoException {
        List<Notifyer> notifyers = new ArrayList<>();


        try (Statement statement = DatabaseManager.getStatement()){
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_NOTIFY);
            while (resultSet.next()) {
                User user = UserService.getUserById(resultSet.getInt("userId"));

                notifyers.add(new Notifyer(
                        resultSet.getInt("id"),
                        user,
                        resultSet.getString("notType")));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new NotifyDaoException();
        } catch (UserNotFoundException e) {
            logger.error(e);
            throw new NotifyDaoException();
        } catch (UserServiceException e) {
            logger.error(e);
            throw new NotifyDaoException();
        }
        return notifyers;
    }

    public Notifyer update(Notifyer entity) throws NotifyDaoException {

        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_UPDATE_NOTIFY)){
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


        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_SELECT_BY_ID)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                User user = UserService.getUserById(resultSet.getInt("userId"));
                notifyer = new Notifyer(resultSet.getInt("id"),
                        user, resultSet.getString("notType"));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new NotifyDaoException();
        } catch (UserNotFoundException e) {
            logger.error(e);
            throw new NotifyDaoException();
        } catch (UserServiceException e) {
            logger.error(e);
            throw new NotifyDaoException();
        }
        return notifyer;
    }

    public boolean delete(Integer id) throws NotifyDaoException {

        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_DELETE_NOTIFY)){
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error(e);
            throw new NotifyDaoException();
        }
    }

    public boolean create(Notifyer entity) throws NotifyDaoException {

        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_INSERT_NOTIFY)){
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

        List<Notifyer> notifyers= new ArrayList<>();
        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_SELECT_BY_TYPE)){
            preparedStatement.setString(1, notyType);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                User user = UserService.getUserById(resultSet.getInt("userId"));
                notifyers.add(new Notifyer(resultSet.getInt("id"),
                        user, resultSet.getString("notType")));
            }

        } catch (SQLException e) {
            logger.error(e);
            throw new NotifyDaoException();
        } catch (UserNotFoundException e) {
            logger.error(e);
            throw new NotifyDaoException();
        } catch (UserServiceException e) {
            logger.error(e);
            throw new NotifyDaoException();
        }
        return notifyers;
    }

    public List<Notifyer> getAllByUser(int userId) throws NotifyDaoException {

        List<Notifyer> notifyers= new ArrayList<>();

        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_SELECT_BY_USER)){
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                User user = UserService.getUserById(resultSet.getInt("userId"));
                notifyers.add(new Notifyer(resultSet.getInt("id"),
                        user, resultSet.getString("notType")));
            }

        } catch (SQLException e) {
            logger.error(e);
            throw new NotifyDaoException();
        } catch (UserNotFoundException e) {
            logger.error(e);
            throw new NotifyDaoException();
        } catch (UserServiceException e) {
            logger.error(e);
            throw new NotifyDaoException();
        }
        return notifyers;
    }
}
