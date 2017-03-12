package ru.innopolis.smoldyrev.models.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.innopolis.smoldyrev.common.exceptions.MessageDaoException;
import ru.innopolis.smoldyrev.models.connector.DatabaseManager;
import ru.innopolis.smoldyrev.models.dao.interfaces.IConverseDAO;
import ru.innopolis.smoldyrev.models.pojo.Conversation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smoldyrev on 12.03.17.
 */
@Repository
public class ConverseDAO implements IConverseDAO {

    private static Logger logger = Logger.getLogger(ConverseDAO.class);

    private static final String SQL_CHECK_CONVERSATION = "    SELECT id, chatroom, start_time, end_time, grade_converse\n" +
            "        FROM \"Main\".r_conversation\n" +
            "        where chatroom=? and start_time < ? and end_time > ?";

    private static final String SQL_CREATE_CONVERSATION = "INSERT INTO \"Main\".r_conversation(\n" +
            "\tchatroom, start_time)\n" +
            "\tVALUES (?, ?) RETURNING \"id\"";

    private static final String SQL_ADD_CONVERSE_MEMBER = "INSERT INTO \"Main\".r_converse_members(\n" +
            "\tconverse, \"user\")\n" +
            "\tVALUES (?, ?);";

    private static final String SQL_GET_ALL_ACTIVE = "SELECT id, chatroom, start_time, end_time, grade_converse\n" +
            "\tFROM \"Main\".r_conversation WHERE end_time > ?";


    @Override
    public int getConversation(int chatroom, LocalDateTime date) throws MessageDaoException {

        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_CHECK_CONVERSATION)) {

            int converseId = 0;

            preparedStatement.setInt(1, chatroom);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(date));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(date));
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {

                PreparedStatement ps = DatabaseManager.getPrepareStatement(SQL_CREATE_CONVERSATION);
                ps.setInt(1, chatroom);
                ps.setTimestamp(2, Timestamp.valueOf(date));
                rs = ps.executeQuery();
                if (rs.next()) {
                    converseId = rs.getInt("id");
                }
            } else {
                converseId = rs.getInt("id");
            }
            return converseId;
        } catch (SQLException e) {
            logger.error(e);
            throw new MessageDaoException();
        }
    }

    @Override
    public boolean addConverseMember(int userId, int converse) throws MessageDaoException {
        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_ADD_CONVERSE_MEMBER)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, converse);
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            logger.error(e);
            throw new MessageDaoException();
        }
    }

    @Override
    public List<Conversation> getActiveConversation(LocalDateTime dateTime) throws MessageDaoException {
        List<Conversation> conversations = new ArrayList<>();
        try (PreparedStatement preparedStatement = DatabaseManager.getPrepareStatement(SQL_GET_ALL_ACTIVE)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(dateTime));
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                conversations.add(new Conversation(
                        rs.getInt("id"),
                        rs.getInt("chatroom"),
                        null,
                        null,
                        rs.getInt("grade_converse")));
            }
            return conversations;
        } catch (SQLException e) {
            logger.error(e);
            throw new MessageDaoException();
        }
    }


}
