package controllers;

import models.pojo.User;
import org.apache.log4j.Logger;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by smoldyrev on 23.02.17.
 * Изменение данных пользователя
 * сразу меняется User и Person
 */
public class EditUserServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(GeneralChatServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    /**Изменяем административные данные пользователя
     * isBlocked - блокируем пользователя
     * userType - роль для работы (admin/administrator/moder/user)*/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        logger.debug("here i was");
        String id = req.getParameter("userID");

        User user = UserService.getUserById(Integer.parseInt(id));

        if (req.getParameter("block")!=null) {
            user.setBlocked(!user.isBlocked());
        }
        if (req.getParameter("usertype")!=null) {
            user.setUserType(req.getParameter("usertype"));
        }

        if (UserService.update(user)!=null) {
            logger.trace("update "+user.getUserID()+" is ok");
            req.getRequestDispatcher("/admin/adminoffice").forward(req, resp);
        } else {
            logger.trace("update "+user.getUserID()+" is false");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}
