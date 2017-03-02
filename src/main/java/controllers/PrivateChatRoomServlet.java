package controllers;

import common.utilities.ErrorForwarder;
import exceptions.MessageServiceException;
import models.pojo.Message;
import org.apache.log4j.Logger;
import service.MessageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by smoldyrev on 25.02.17.
 * Приватная комната чата
 */
public class PrivateChatRoomServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(PrivateChatRoomServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }


    /**форма приватной chatroom
     * загружаем все сообщения
     * открываем chatroom
     * */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String chatroom;
        if (req.getParameter("chatroomin") != null) {
            chatroom = req.getParameter("chatroomin");
        } else {
            chatroom = req.getParameter("chatroom");
        }

        List<Message> messages = null;
        try {
            messages = MessageService.getAllInRoom(Integer.parseInt(chatroom));
        } catch (MessageServiceException e) {
            logger.error(e);
            ErrorForwarder.forwardToErrorPage(req,resp,
                    "Ошибка получения сообщений");
        }

        req.setAttribute("messages", messages);
        req.setAttribute("chatroom", chatroom);

        req.getRequestDispatcher("/rooms/privatechatroom.jsp").forward(req, resp);
    }
}
