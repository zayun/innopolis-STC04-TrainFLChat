package service;

import models.dao.MessageDAO;
import models.pojo.Message;

/**
 * Created by smoldyrev on 25.02.17.
 * Сервис работы с сообщениями
 */
public class MessageService {

    public static boolean sendMessage(Message message) {

        MessageDAO messageDAO = new MessageDAO();

        return messageDAO.create(message);
    }
}
