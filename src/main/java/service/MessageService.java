package service;

import models.dao.MessageDAO;
import models.pojo.Message;

import java.util.List;

/**
 * Created by smoldyrev on 25.02.17.
 * Сервис работы с сообщениями
 */
public class MessageService {

    private static MessageDAO messageDAO = new MessageDAO();

    public static boolean sendMessage(Message message) {

        MessageDAO messageDAO = new MessageDAO();

        return messageDAO.create(message);
    }

    public static List<Message> getAllInRoom(int chatroom) {
        return messageDAO.getAllInRoom(chatroom);

    }

    public static void deleteMessage(int id) {
        messageDAO.delete(id);
    }

}
