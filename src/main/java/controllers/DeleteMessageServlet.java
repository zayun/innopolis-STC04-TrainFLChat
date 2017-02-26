package controllers;

import models.dao.MessageDAO;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by smoldyrev on 26.02.17.
 */
public class DeleteMessageServlet extends HttpServlet{

    private static Logger logger = Logger.getLogger(DeleteMessageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("msgid: "+req.getParameter("msgid"));
        MessageDAO messageDAO = new MessageDAO();
        messageDAO.delete(Integer.parseInt(req.getParameter("msgid")));

        req.getRequestDispatcher("/rooms/generalchat").forward(req, resp);
    }
}
