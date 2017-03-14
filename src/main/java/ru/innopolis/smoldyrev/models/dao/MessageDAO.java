package ru.innopolis.smoldyrev.models.dao;

import org.springframework.stereotype.Repository;
import ru.innopolis.smoldyrev.common.exceptions.MessageDaoException;
import ru.innopolis.smoldyrev.common.exceptions.UserDaoException;
import ru.innopolis.smoldyrev.models.connector.DatabaseManager;
import ru.innopolis.smoldyrev.models.dao.interfaces.IMessageDAO;
import ru.innopolis.smoldyrev.models.pojo.Message;
import ru.innopolis.smoldyrev.models.pojo.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smoldyrev on 25.02.17.
 */
@Repository
public class MessageDAO implements IMessageDAO {

    private static Logger logger = Logger.getLogger(MessageDAO.class);

    private static final String SQL_SELECT_ALL_MESSAGES = "SELECT * FROM main.r_messages" +
            "  ORDER BY date_time DESC";

    private static final String SQL_SELECT_MESSAGES_IN_CR = "SELECT * FROM main.r_messages " +
            "WHERE chatroom = ? ORDER BY date_time DESC";

    private static final String SQL_INSERT_MESSAGE = "INSERT INTO main.r_messages" +
            "(from_user, to_user, body_text, chatroom) " +
            "VALUES (?, ?, ?, ?)";

    private static final String SQL_DELETE_MESSAGE = "DELETE FROM main.r_messages\n" +
            "\tWHERE id = ?";


    public List<Message> getAll() throws MessageDaoException {
        List<Message> messages = new ArrayList<>();

        try (Statement statement = DatabaseManager.getStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_MESSAGES);
            while (resultSet.next()) {

                User userFrom = getUser(resultSet.getInt("from_user"));
                User userTo = getUser(resultSet.getInt("to_user"));
                messages.add(new Message(
                        resultSet.getInt("id"),
                        LocalDateTime.ofInstant(
                                resultSet.getTimestamp("date_time").toInstant(), ZoneId.systemDefault()),
                        userFrom,
                        userTo,
                        resultSet.getString("body_text"),
                        resultSet.getBoolean("is_read"),
                        resultSet.getInt("chatroom")));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new MessageDaoException();
        }

        return messages;
    }

    public List<Message> getAllInRoom(int chatRoom) throws MessageDaoException {
        List<Message> messages = new ArrayList<>();

        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_SELECT_MESSAGES_IN_CR)) {
            preparedStatement.setInt(1, chatRoom);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                User userFrom = getUser(resultSet.getInt("from_user"));
                User userTo = getUser(resultSet.getInt("to_user"));
                messages.add(new Message(
                        resultSet.getInt("id"),
                        LocalDateTime.ofInstant(
                                resultSet.getTimestamp("date_time").toInstant(), ZoneId.systemDefault()),
                        userFrom,
                        userTo,
                        resultSet.getString("body_text"),
                        resultSet.getBoolean("is_read"),
                        resultSet.getInt("chatroom")));
            }

        } catch (SQLException e) {
            logger.error(e);
            throw new MessageDaoException();
        }
        return messages;
    }

    public boolean delete(Integer id) throws MessageDaoException {

        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_DELETE_MESSAGE)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error(e);
            throw new MessageDaoException();
        }
    }

    public boolean create(Message entity) throws MessageDaoException {

        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_INSERT_MESSAGE)) {
            preparedStatement.setInt(1, entity.getFromUser().getUserID());
            preparedStatement.setInt(2, entity.getToUser().getUserID());
            preparedStatement.setString(3, entity.getBodyText());
            preparedStatement.setInt(4, entity.getChatRoom());
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            logger.error(e);
            throw new MessageDaoException();
        }
    }


    private User getUser(int id) {
        UserDAO userDAO = new UserDAO();
        try {

            return userDAO.getEntityById(id);
        } catch (UserDaoException e) {
            logger.error(e);
        }
        return null;
    }
}
