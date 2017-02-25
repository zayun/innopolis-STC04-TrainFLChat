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
 */
public class GeneralChatServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(GeneralChatServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        MessageDAO messageDAO = new MessageDAO();
        List<Message> messages = messageDAO.getAll();

        UserDAO userDAO = new UserDAO();
        List<User> users =  userDAO.getAll();

        req.setAttribute("messages", messages);
        req.setAttribute("users", users);

        req.setAttribute("userFrom",req.getSession().getAttribute("idd"));
        req.setAttribute("chatRoom",0);

        req.getRequestDispatcher("/generalchat.jsp").forward(req, resp);
    }
}
