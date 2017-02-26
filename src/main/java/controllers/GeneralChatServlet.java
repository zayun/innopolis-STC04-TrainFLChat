package controllers;

import models.dao.MessageDAO;
import models.dao.UserDAO;
import models.pojo.Message;
import models.pojo.User;
import org.apache.log4j.Logger;

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int chatroom = 0;
        MessageDAO messageDAO = new MessageDAO();
        List<Message> messages = messageDAO.getAllInRoom(chatroom);

        UserDAO userDAO = new UserDAO();
        List<User> users =  userDAO.getAll();

        req.setAttribute("messages", messages);
        req.setAttribute("users", users);

        req.setAttribute("userFrom",req.getSession().getAttribute("sessionId"));
        req.setAttribute("chatRoom",chatroom);

        req.getRequestDispatcher("/rooms/generalchat.jsp").forward(req, resp);
    }
}
