package controllers;

import models.pojo.Message;
import models.pojo.User;
import org.apache.log4j.Logger;
import service.MessageService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by smoldyrev on 25.02.17.
 * Отправка сообщения
 */
public class SendMessageServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(SendMessageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }


    /**Отправка сообщения
     * парметры userFrom, userTo, textMessage
     * после записи сообщения в БД редирект
     * на privatechatroom если chatroom!=0
     * на generalchat если chatroom==0;
     * */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        logger.debug("020317////"+req.getContextPath());
        String userFromId = ("".equals(req.getParameter("userFrom"))?"0":req.getParameter("userFrom"));
        String userToId = ("".equals(req.getParameter("userTo"))?"999":req.getParameter("userTo"));

        User userFrom = UserService.getUserById(Integer.parseInt(userFromId));
        User userTo = UserService.getUserById(Integer.parseInt(userToId));

        int chatroom = Integer.parseInt(req.getParameter("chatroom"));

        String textMessage = req.getParameter("textMessage");
        if (userFrom == null || userTo == null) {
            req.setAttribute("msg", "Отправка сообщения не удалась, не найден пользователь");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }


        Message message = new Message();
        message.setFromUser(userFrom);
        message.setToUser(userTo);
        message.setBodyText(textMessage);
        message.setChatRoom(chatroom);

        if (MessageService.sendMessage(message)) {
            if (chatroom == 0) {
                req.getRequestDispatcher("/rooms/generalchat").forward(req, resp);
            } else {
                req.getRequestDispatcher("/rooms/privatechatroom").forward(req, resp);
            }

        } else {
            logger.error("message was lost");
            req.setAttribute("msg", "Отправка сообщения не удалась");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}
