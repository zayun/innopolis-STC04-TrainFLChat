package ru.innopolis.smoldyrev.models.dao;

import org.springframework.stereotype.Repository;
import ru.innopolis.smoldyrev.common.exceptions.UserDaoException;
import ru.innopolis.smoldyrev.models.connector.DatabaseManager;
import ru.innopolis.smoldyrev.models.dao.interfaces.IUserDAO;
import ru.innopolis.smoldyrev.models.pojo.Person;
import ru.innopolis.smoldyrev.models.pojo.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smoldyrev on 23.02.17.
 */
@Repository
public class UserDAO implements IUserDAO{

    private static Logger logger = Logger.getLogger(UserDAO.class);

    private static final String SQL_FIND_USER_LP = "SELECT\n" +
            "  \"userID\" AS id,\n" +
            "  \"login\" AS login,\n" +
            "  \"pwd\" AS pwd,\n" +
            "  \"Main\".\"d_Persons\".\"personID\" AS pid,\n" +
            "  \"blocked\" AS blocked,\n" +
            "  \"usertype\" AS usertype,\n" +
            "  \"FirstName\" AS firstName,\n" +
            "  \"LastName\" AS lastName,\n" +
            "  \"birthDay\" AS birthday,\n" +
            "  \"email\" AS email,\n" +
            "  \"phoneNumber\" AS phoneNumber,\n" +
            "  \"male\" AS isMale\n" +
            "FROM \"Main\".\"d_Users\"\n" +
            "LEFT JOIN \"Main\".\"d_Persons\"\n" +
            "  ON \"d_Users\".\"personID\" = \"d_Persons\".\"personID\"\n" +
            "WHERE \"d_Users\".\"login\"=? AND \"d_Users\".\"pwd\"=?";

    private static final String SQL_SELECT_ALL_USERS = "SELECT\n" +
            "  \"userID\" AS id,\n" +
            "  \"login\" AS login,\n" +
            "  \"pwd\" AS pwd,\n" +
            "  \"Main\".\"d_Persons\".\"personID\" AS pid,\n" +
            "  \"blocked\" AS blocked,\n" +
            "  \"usertype\" AS usertype,\n" +
            "  \"FirstName\" AS firstName,\n" +
            "  \"LastName\" AS lastName,\n" +
            "  \"birthDay\" AS birthday,\n" +
            "  \"email\" AS email,\n" +
            "  \"phoneNumber\" AS phoneNumber,\n" +
            "  \"male\" AS isMale\n" +
            "FROM \"Main\".\"d_Users\"\n" +
            "LEFT JOIN \"Main\".\"d_Persons\"\n" +
            "  ON \"d_Users\".\"personID\" = \"d_Persons\".\"personID\"\n" +
            "ORDER BY \"login\"";

    private static final String SQL_FIND_USER_ID = "SELECT\n" +
            "  \"userID\" AS id,\n" +
            "  \"login\" AS login,\n" +
            "  \"pwd\" AS pwd,\n" +
            "  \"Main\".\"d_Persons\".\"personID\" AS pid,\n" +
            "  \"blocked\" AS blocked,\n" +
            "  \"usertype\" AS usertype,\n" +
            "  \"FirstName\" AS firstName,\n" +
            "  \"LastName\" AS lastName,\n" +
            "  \"birthDay\" AS birthday,\n" +
            "  \"email\" AS email,\n" +
            "  \"phoneNumber\" AS phoneNumber,\n" +
            "  \"male\" AS isMale\n" +
            "FROM \"Main\".\"d_Users\"\n" +
            "LEFT JOIN \"Main\".\"d_Persons\"\n" +
            "  ON \"d_Users\".\"personID\" = \"d_Persons\".\"personID\"\n" +
            "WHERE \"d_Users\".\"userID\"=?";

    private static final String SQL_INSERT_USER = "INSERT INTO \"Main\".\"d_Users\"(\n" +
            "\tlogin, pwd, \"personID\", blocked, usertype)\n" +
            "\tVALUES (?, ?, ?, ?, ?) RETURNING \"userID\"";

    private static final String SQL_UPDATE_USER = "UPDATE \"Main\".\"d_Users\"\n" +
            "\tSET \"login\"=?, \"pwd\"=?, \"personID\"=?, \"blocked\"=?, \"usertype\"=?\n" +
            "\tWHERE \"userID\" = ?";

    public User getUserByLoginAndPassword(String login, String password) throws UserDaoException {

        User user = null;

        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_FIND_USER_LP)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Person person = new Person(
                        resultSet.getInt("pid"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("email"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getDate("birthday"),
                        resultSet.getBoolean("isMale"));
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("usertype"),
                        resultSet.getString("login"),
                        resultSet.getString("pwd"),
                        person,
                        resultSet.getBoolean("blocked"));
            } else {
                logger.debug(login + " " + password + " not found");
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new UserDaoException();
        } catch (NullPointerException e) {
            logger.error(e);
            throw new UserDaoException();
        }
        return user;
    }

    public List<User> getAll() throws UserDaoException {
        List<User> users = new ArrayList<>();

        try (Statement statement = DatabaseManager.getStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS);
            while (resultSet.next()) {
                Person person = new Person(
                        resultSet.getInt("pid"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("email"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getDate("birthday"),
                        resultSet.getBoolean("isMale"));
                users.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("usertype"),
                        resultSet.getString("login"),
                        resultSet.getString("pwd"),
                        person,
                        resultSet.getBoolean("blocked")));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new UserDaoException();
        }
        return users;
    }

    public User update(User entity) throws UserDaoException {

        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_UPDATE_USER)){
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setInt(3, entity.getPerson().getId());
            preparedStatement.setBoolean(4, entity.isBlocked());
            preparedStatement.setString(5, entity.getUserType());
            preparedStatement.setInt(6, entity.getUserID());
            preparedStatement.executeUpdate();

            logger.debug(entity.getUserID()+" user was update");
            return entity;
        } catch (SQLException e) {
            logger.error(e);
            throw new UserDaoException();
        }
    }

    public User getEntityById(Integer id) throws UserDaoException {
        User user = null;

        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_FIND_USER_ID)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Person person = new Person(
                        resultSet.getInt("pid"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("email"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getDate("birthday"),
                        resultSet.getBoolean("isMale"));
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("usertype"),
                        resultSet.getString("login"),
                        resultSet.getString("pwd"),
                        person,
                        resultSet.getBoolean("blocked"));
            } else {
                logger.error(id + " user not found");
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new UserDaoException();
        }
        return user;
    }

    public boolean create(User entity) throws UserDaoException {

        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_INSERT_USER)){
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setInt(3, entity.getPerson().getId());
            preparedStatement.setBoolean(4, entity.isBlocked());
            preparedStatement.setString(5, entity.getUserType());

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                entity.setUserID(rs.getInt("userID"));
            }
            logger.debug(entity.getUserID() + "/" + entity.getLogin() + "/ user was insert");
            return true;
        } catch (SQLException e) {
            logger.error(e);
            throw new UserDaoException();
        }
    }
}
