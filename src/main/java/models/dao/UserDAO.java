package models.dao;

import models.connector.DatabaseManager;
import models.pojo.Person;
import models.pojo.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;

/**
 * Created by smoldyrev on 23.02.17.
 */
public class UserDAO extends AbstractDAO<User, Integer>{

    private static Logger logger = Logger.getLogger(UserDAO.class);

    private static final String SQL_FIND_USER = "SELECT\n" +
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

    private static final String SQL_INSERT_USER = "INSERT INTO \"Main\".\"d_Users\"(\n" +
            "\tlogin, pwd, \"personID\", blocked, usertype)\n" +
            "\tVALUES (?, ?, ?, ?, ?) RETURNING \"userID\";";

    public static User getUserByLoginAndPassword(String login, String password) {

        User user = null;
        Person person = null;
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                person = new Person(
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
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public User getEntityById(Integer id) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean create(User entity) {
        try(PreparedStatement preparedStatement = super.getPrepareStatement(SQL_INSERT_USER)) {
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setInt(3, entity.getPerson().getId());
            preparedStatement.setBoolean(4, entity.isBlocked());
            preparedStatement.setString(5, entity.getUserType());

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                entity.setUserID(rs.getInt("userID"));
            }
            logger.debug(entity.getUserID()+"/"+entity.getLogin()+"/ user was insert");
        } catch (SQLException e) {
            logger.error(e);
        }
        return true;
    }
}
