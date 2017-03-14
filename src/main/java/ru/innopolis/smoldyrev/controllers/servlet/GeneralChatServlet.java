package ru.innopolis.smoldyrev.controllers.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.innopolis.smoldyrev.common.utilities.ErrorForwarder;
import ru.innopolis.smoldyrev.common.exceptions.MessageServiceException;
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
import java.util.List;

/**
 * Created by smoldyrev on 23.02.17.
 * Сервлет заполнения основной формы чата
 * загружает все сообщения и пользователей
 */
public class GeneralChatServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(GeneralChatServlet.class);

    @Autowired
    private UserService userService;

    @Autowired(required = true)
    private MessageService messageService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("userTo", req.getParameter("userTo"));
        doPost(req,resp);
    }

    /**Открываем основную форму программы
     * загружаем все сообщения chatroom#0
     * загружаем всехпользователе
     * открываем форму*/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int chatroom = 0;
        List<Message> messages = null;
        try {
            messages = messageService.getAllInRoom(chatroom);
        } catch (MessageServiceException e) {
            logger.error(e);
            ErrorForwarder.forwardToErrorPage(req,resp,
                    "Ошибка получения списка сообщений");
        }

        List<User> users = null;
        try {
            users = userService.getAll();
        } catch (UserServiceException e) {
            logger.error(e);
            ErrorForwarder.forwardToErrorPage(req,resp,
                    "Ошибка при получении доступа к таблице пользователей");
        }

        req.setAttribute("messages", messages);
        req.setAttribute("users", users);

        req.setAttribute("userFrom",req.getSession().getAttribute("sessionId"));
        req.setAttribute("chatRoom",chatroom);
        req.setAttribute("userTo", req.getParameter("userTo"));

        req.getRequestDispatcher("/rooms/generalchat.jsp").forward(req, resp);
    }
}
