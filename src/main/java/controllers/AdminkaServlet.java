package controllers;

import common.utilities.ErrorForwarder;
import exceptions.UserServiceException;
import models.pojo.User;
import org.apache.log4j.Logger;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by smoldyrev on 26.02.17.
 * данные админки
 */
public class AdminkaServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(AdminkaServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }


    /**Заполняем список пользователей программы
     * и выводим jsp с этим списком*/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<User> users = null;
        try {
            users = UserService.getAll();
        } catch (UserServiceException e) {
            logger.error(e);
            ErrorForwarder.forwardToErrorPage(req,resp,"Ошибка при получении доступа к таблице пользователей");
        }

        req.setAttribute("users", users);
        req.getRequestDispatcher("/admin/adminoffice.jsp").forward(req, resp);
    }
}
