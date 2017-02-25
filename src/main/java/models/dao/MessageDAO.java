package models.dao;

import models.pojo.Message;
import models.pojo.User;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smoldyrev on 25.02.17.
 */
public class MessageDAO extends AbstractDAO<Message, Integer> {


    private static Logger logger = Logger.getLogger(MessageDAO.class);

    private static final String SQL_SELECT_ALL_MESSAGES = "SELECT * FROM \"Main\".\"r_Messages\"" +
            "  ORDER BY \"DateTime\" DESC";

    private static final String SQL_INSERT_MESSAGE = "INSERT INTO \"Main\".\"r_Messages\"" +
            "(\"FromUserID\", \"ToUserID\", \"BodyText\", chatroom) " +
            "VALUES (?, ?, ?, ?)" ;

    @Override
    public List<Message> getAll() {
        List<Message> messages = new ArrayList<>();

        try (Statement statement = getStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_MESSAGES);
            while (resultSet.next()) {
                UserDAO userDAO = new UserDAO();
                User userFrom = userDAO.getEntityById(resultSet.getInt("FromUserID"));
                User userTo = userDAO.getEntityById(resultSet.getInt("ToUserID"));
                messages.add(new Message(
                        resultSet.getInt("id"),
                        resultSet.getDate("DateTime"),
                        userFrom,
                        userTo,
                        resultSet.getString("BodyText"),
                        resultSet.getBoolean("isRead"),
                        resultSet.getInt("chatroom")));
            }
        } catch (SQLException e) {
            logger.error(e);
        }

        return messages;
    }

    @Override
    public Message update(Message entity) {
        return null;
    }

    @Override
    public Message getEntityById(Integer id) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean create(Message entity) {
        try (PreparedStatement preparedStatement = getPrepareStatement(SQL_INSERT_MESSAGE)) {
            preparedStatement.setInt(1, entity.getFromUser().getUserID());
            preparedStatement.setInt(2, entity.getToUser().getUserID());
            preparedStatement.setString(3, entity.getBodyText());
            preparedStatement.setInt(4, entity.getChatRoom());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error(e);
        }
        return false;
    }
}
