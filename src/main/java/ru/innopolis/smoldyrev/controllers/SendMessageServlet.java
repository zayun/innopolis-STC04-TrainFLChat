package ru.innopolis.smoldyrev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.innopolis.smoldyrev.common.utilities.ErrorForwarder;
import ru.innopolis.smoldyrev.common.exceptions.MessageServiceException;
import ru.innopolis.smoldyrev.common.exceptions.UserNotFoundException;
import ru.innopolis.smoldyrev.common.exceptions.UserServiceException;
import ru.innopolis.smoldyrev.models.pojo.Message;
import ru.innopolis.smoldyrev.models.pojo.User;
import org.apache.log4j.Logger;
import ru.innopolis.smoldyrev.service.MessageService;
import ru.innopolis.smoldyrev.service.UserService;

import javax.servlet.ServletConfig;
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

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }


    /**
     * Отправка сообщения
     * парметры userFrom, userTo, textMessage
     * после записи сообщения в БД редирект
     * на privatechatroom если chatroom!=0
     * на generalchat если chatroom==0;
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        User userFrom = null;
        User userTo = null;

        int chatroom = Integer.parseInt(req.getParameter("chatroom"));
        String textMessage = req.getParameter("textMessage");
        try {
            String userFromId = ("".equals(req.getParameter("userFrom")) ? "0" : req.getParameter("userFrom"));
            String userToId = ("".equals(req.getParameter("userTo")) ? "999" : req.getParameter("userTo"));
            userFrom = userService.getUserById(Integer.parseInt(userFromId));
            userTo = userService.getUserById(Integer.parseInt(userToId));

            if (userFrom == null || userTo == null) {
                throw new UserNotFoundException();
            }

        } catch (UserServiceException e) {
            logger.error(e);
            ErrorForwarder.forwardToErrorPage(req, resp,
                    "Ошибка при получении доступа к таблице пользователей");
        } catch (UserNotFoundException e) {
            ErrorForwarder.forwardToErrorPage(req, resp,
                    "Отправка сообщения не удалась, не найден пользователь");
        }

        Message message = new Message();
        message.setFromUser(userFrom);
        message.setToUser(userTo);
        message.setBodyText(textMessage);
        message.setChatRoom(chatroom);

        try {
            messageService.sendMessage(message);
            if (chatroom == 0) {
                req.getRequestDispatcher("/rooms/generalchat").forward(req, resp);
            } else {
                req.getRequestDispatcher("/rooms/privatechatroom").forward(req, resp);
            }
        } catch (MessageServiceException e) {
            logger.error(e);
            ErrorForwarder.forwardToErrorPage(req, resp,
                    "Ошибка при отправке сообщения");
        }
    }
}
