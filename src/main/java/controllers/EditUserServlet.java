package controllers;

import models.dao.PersonDAO;
import models.dao.UserDAO;
import models.pojo.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

/**
 * Created by smoldyrev on 23.02.17.
 */
public class EditUserServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(GeneralChatServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("here i was");
        String id = req.getParameter("userID");
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getEntityById(Integer.parseInt(id));

        if (req.getParameter("block")!=null) {
            user.setBlocked(!user.isBlocked());
        }
        if (req.getParameter("usertype")!=null) {
            user.setUserType(req.getParameter("usertype"));
        }

        if (userDAO.update(user)!=null) {
            logger.trace("update "+user.getUserID()+" is ok");
            req.getRequestDispatcher("/admin/adminoffice").forward(req, resp);
        } else {
            logger.trace("update "+user.getUserID()+" is false");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}
