package controllers;

import org.apache.log4j.Logger;
import service.MessageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by smoldyrev on 26.02.17.
 * Сервлет удаления сообщения по id
 */
public class DeleteMessageServlet extends HttpServlet{

    private static Logger logger = Logger.getLogger(DeleteMessageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    }

    /**Получаем msgid - id сообщения в БД
     * отправляем запрос на удаление сообщения по id*/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("msgid: "+req.getParameter("msgid"));

        logger.debug("020317////"+req.getContextPath());
        MessageService.deleteMessage(Integer.parseInt(req.getParameter("msgid")));

        req.getRequestDispatcher("/rooms/generalchat").forward(req, resp);
    }
}
