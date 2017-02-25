package controllers;

import models.dao.MessageDAO;
import models.dao.UserDAO;
import models.pojo.Message;
import org.apache.log4j.Logger;
import service.MessageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

/**
 * Created by smoldyrev on 25.02.17.
 */
public class SendMessageServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(SendMessageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/chat/generalchat.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userFrom = req.getParameter("userFrom");
        String userTo = req.getParameter("userTo");
        String chatroom = req.getParameter("chatroom");
        String textMessage = req.getParameter("textMessage");

        UserDAO userDAO = new UserDAO();

        logger.debug("???"+userFrom+"???");
        Message message = new Message();
        message.setFromUser(userDAO.getEntityById(Integer.parseInt(userFrom)));
        message.setToUser(userDAO.getEntityById(Integer.parseInt(userTo)));
        message.setBodyText(textMessage);
        message.setChatRoom(Integer.parseInt(chatroom));

        if (MessageService.sendMessage(message)) {
            logger.trace("message was sended");
            if (chatroom.equals(0)) {
                req.getRequestDispatcher("/generalchat").forward(req, resp);
            }else {
                req.getRequestDispatcher("/privatechatroom").forward(req, resp);
            }

        } else {
            logger.trace("message was lost");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}
