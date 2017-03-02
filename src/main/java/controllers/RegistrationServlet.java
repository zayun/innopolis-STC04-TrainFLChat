package controllers;

import common.utilities.ErrorForwarder;
import exceptions.UserServiceException;
import models.pojo.Person;
import models.pojo.User;
import org.apache.log4j.Logger;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

/**
 * Created by smoldyrev on 23.02.17.
 * doGet открывает форму регистрации
 * doPost записывает данные в БД - регистрирует пользователя
 */
public class RegistrationServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(RegistrationServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/registration.jsp").forward(req, resp);
    }

    /**
     * Запись дынных внесенных на форму registration.jsp в БД
     * при любом исходе редирект на login.jsp
     * с сообщением о результате
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        Date date = new Date(1L);
        if (!"".equals(req.getParameter("birthday"))) {
            date = Date.valueOf(req.getParameter("birthday"));
        }

        Person person = new Person(
                0,
                req.getParameter("firstName"),
                req.getParameter("lastName"),
                req.getParameter("email"),
                req.getParameter("phoneNumber"),
                date,
                new Boolean(req.getParameter("male")));
        User user = new User(
                0,
                "user",
                req.getParameter("login"),
                req.getParameter("password"),
                person,
                false);
        try {
            UserService.registration(user);
            logger.trace(user.getUserID() + "/" + user.getLogin() + " registration successful");
            req.setAttribute("msg", "registration " + user.getLogin() + " completed successfully");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);

        } catch (UserServiceException e) {
            logger.error(e);
            ErrorForwarder.forwardToErrorPage(req, resp,
                    "Ошибка при получении доступа к таблице пользователей");
        }
    }
}
