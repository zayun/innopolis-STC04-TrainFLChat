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

@Repository
public class UserDAO implements IUserDAO{

    private static Logger logger = Logger.getLogger(UserDAO.class);

    private static final String SQL_FIND_USER_LP = "SELECT\n" +
            "  user_id AS id,\n" +
            "  login AS login,\n" +
            "  pwd AS pwd,\n" +
            "  main.d_persons.person_id AS pid,\n" +
            "  blocked AS blocked,\n" +
            "  usertype AS usertype,\n" +
            "  first_name AS first_name,\n" +
            "  last_name AS last_name,\n" +
            "  birthday AS birthday,\n" +
            "  email AS email,\n" +
            "  phone_number AS phone_number,\n" +
            "  male AS male\n" +
            "FROM main.d_users\n" +
            "LEFT JOIN main.d_persons\n" +
            "  ON d_users.person_id = d_persons.person_id\n" +
            "WHERE d_users.login=? AND d_users.pwd=?";

    private static final String SQL_SELECT_ALL_USERS = "SELECT\n" +
            "  user_id AS id,\n" +
            "  login AS login,\n" +
            "  pwd AS pwd,\n" +
            "  main.d_persons.person_id AS pid,\n" +
            "  blocked AS blocked,\n" +
            "  usertype AS usertype,\n" +
            "  first_name AS first_name,\n" +
            "  last_name AS last_name,\n" +
            "  birthday AS birthday,\n" +
            "  email AS email,\n" +
            "  phone_number AS phone_number,\n" +
            "  male AS male\n" +
            "FROM main.d_users\n" +
            "LEFT JOIN main.d_persons\n" +
            "  ON d_users.person_id = d_persons.person_id\n" +
            "ORDER BY login";

    private static final String SQL_SELECT_ALL_USERS_IN_CONVERSE = "SELECT\n" +
            "user_id AS id,\n" +
            "           login AS login,\n" +
            "            pwd AS pwd,\n" +
            "            main.d_persons.person_id AS pid,\n" +
            "            blocked AS blocked,\n" +
            "            usertype AS usertype,\n" +
            "            first_name AS first_name,\n" +
            "            last_name AS last_name,\n" +
            "            birthday AS birthday,\n" +
            "            email AS email,\n" +
            "            phone_number AS phone_number,\n" +
            "            male AS male,\n" +
            "            r_converse_members.id\n" +
            "            FROM main.d_users\n" +
            "            LEFT JOIN main.d_persons\n" +
            "             ON d_users.person_id = d_persons.person_id\n" +
            "             LEFT JOIN main.r_converse_members\n" +
            "             ON d_users.user_id = r_converse_members.user\n" +
            "             where r_converse_members.converse = ?" +
            "            ORDER BY login";

    private static final String SQL_FIND_USER_ID = "SELECT\n" +
            "  user_id AS id,\n" +
            "  login AS login,\n" +
            "  pwd AS pwd,\n" +
            "  main.d_persons.person_id AS pid,\n" +
            "  blocked AS blocked,\n" +
            "  usertype AS usertype,\n" +
            "  first_name AS first_name,\n" +
            "  last_name AS last_name,\n" +
            "  birthDay AS birthday,\n" +
            "  email AS email,\n" +
            "  phone_number AS phone_number,\n" +
            "  male AS male\n" +
            "FROM main.d_users\n" +
            "LEFT JOIN main.d_persons\n" +
            "  ON d_users.person_id = d_persons.person_id\n" +
            "WHERE d_users.user_id=?";

    private static final String SQL_INSERT_USER = "INSERT INTO main.d_users(\n" +
            "\tlogin, pwd, person_id, blocked, usertype)\n" +
            "\tVALUES (?, ?, ?, ?, ?) RETURNING user_id";

    private static final String SQL_UPDATE_USER = "UPDATE main.d_users\n" +
            "\tSET login=?, pwd=?, person_id=?, blocked=?, usertype=?\n" +
            "\tWHERE user_id = ?";

    public User getUserByLoginAndPassword(String login, String password) throws UserDaoException {

        User user = null;

        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_FIND_USER_LP)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Person person = new Person(
                        resultSet.getInt("pid"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number"),
                        resultSet.getDate("birthday"),
                        resultSet.getBoolean("male"));
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
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number"),
                        resultSet.getDate("birthday"),
                        resultSet.getBoolean("male"));
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

    public List<User> getAllInConverse(int converse) throws UserDaoException {
        List<User> users = new ArrayList<>();

        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_SELECT_ALL_USERS_IN_CONVERSE)) {
            preparedStatement.setInt(1, converse);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Person person = new Person(
                        resultSet.getInt("pid"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number"),
                        resultSet.getDate("birthday"),
                        resultSet.getBoolean("male"));
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
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number"),
                        resultSet.getDate("birthday"),
                        resultSet.getBoolean("male"));
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
                entity.setUserID(rs.getInt("user_id"));
            }
            logger.debug(entity.getUserID() + "/" + entity.getLogin() + "/ user was insert");
            return true;
        } catch (SQLException e) {
            logger.error(e);
            throw new UserDaoException();
        }
    }
}
