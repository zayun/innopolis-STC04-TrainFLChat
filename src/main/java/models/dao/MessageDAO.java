package models.dao;

import exceptions.MessageDaoException;
import exceptions.UserNotFoundException;
import exceptions.UserServiceException;
import models.connector.DatabaseManager;
import models.pojo.Message;
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
 * Created by smoldyrev on 25.02.17.
 */
public class MessageDAO {

    private static Logger logger = Logger.getLogger(MessageDAO.class);

    private static final String SQL_SELECT_ALL_MESSAGES = "SELECT * FROM \"Main\".\"r_Messages\"" +
            "  ORDER BY \"DateTime\" DESC";

    private static final String SQL_SELECT_MESSAGES_IN_CR = "SELECT * FROM \"Main\".\"r_Messages\" " +
            "WHERE chatroom = ? ORDER BY \"DateTime\" DESC";

    private static final String SQL_INSERT_MESSAGE = "INSERT INTO \"Main\".\"r_Messages\"" +
            "(\"FromUserID\", \"ToUserID\", \"BodyText\", chatroom) " +
            "VALUES (?, ?, ?, ?)" ;

    private static final String SQL_DELETE_MESSAGE = "DELETE FROM \"Main\".\"r_Messages\"\n" +
            "\tWHERE id = ?" ;

    public List<Message> getAll() throws MessageDaoException {
        List<Message> messages = new ArrayList<>();

        try (Statement statement = DatabaseManager.getStatement()){
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_MESSAGES);
            while (resultSet.next()) {

                User userFrom = UserService.getUserById(resultSet.getInt("FromUserID"));
                User userTo = UserService.getUserById(resultSet.getInt("ToUserID"));
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
            throw new MessageDaoException();
        } catch (UserNotFoundException e) {
            logger.error(e);
            throw new MessageDaoException();
        } catch (UserServiceException e) {
            logger.error(e);
            throw new MessageDaoException();
        }

        return messages;
    }

    public List<Message> getAllInRoom(int chatRoom) throws MessageDaoException {
        List<Message> messages = new ArrayList<>();

        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_SELECT_MESSAGES_IN_CR)){
            preparedStatement.setInt(1, chatRoom);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                User userFrom = UserService.getUserById(resultSet.getInt("FromUserID"));
                User userTo = UserService.getUserById(resultSet.getInt("ToUserID"));
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
            throw new MessageDaoException();
        } catch (UserNotFoundException e) {
            logger.error(e);
            throw new MessageDaoException();
        } catch (UserServiceException e) {
            logger.error(e);
            throw new MessageDaoException();
        }
        return messages;
    }

    public boolean delete(Integer id) throws MessageDaoException {

        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_DELETE_MESSAGE)){
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error(e);
            throw new MessageDaoException();
        }
    }

    public boolean create(Message entity) throws MessageDaoException {

        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_INSERT_MESSAGE)){
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
}
