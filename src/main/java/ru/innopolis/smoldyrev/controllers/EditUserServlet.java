package ru.innopolis.smoldyrev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.innopolis.smoldyrev.common.utilities.ErrorForwarder;
import ru.innopolis.smoldyrev.common.exceptions.UserNotFoundException;
import ru.innopolis.smoldyrev.common.exceptions.UserServiceException;
import ru.innopolis.smoldyrev.models.pojo.User;
import org.apache.log4j.Logger;
import ru.innopolis.smoldyrev.service.UserService;

import javax.servlet.ServletConfig;
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

    @Autowired
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    /**
     * Изменяем административные данные пользователя
     * isBlocked - блокируем пользователя
     * userType - роль для работы (admin/administrator/moder/user)
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        try {
            String id = req.getParameter("userID");
            User user = userService.getUserById(Integer.parseInt(id));

            if (req.getParameter("block") != null) {
                user.setBlocked(!user.isBlocked());
            }
            if (req.getParameter("usertype") != null) {
                user.setUserType(req.getParameter("usertype"));
            }

            user = userService.update(user);
            logger.trace("update " + user.getUserID() + " is ok");
            req.getRequestDispatcher("/admin/adminoffice").forward(req, resp);

        } catch (UserServiceException e) {
            logger.error(e);
            ErrorForwarder.forwardToErrorPage(req,resp,
                    "Ошибка при получении доступа к таблице пользователей");
        } catch (UserNotFoundException e) {
            logger.error(e);
            ErrorForwarder.forwardToErrorPage(req,resp,
                    "Не найден обновляемый пользователь");
        }
    }
}
