package ru.innopolis.smoldyrev.controllers.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.innopolis.smoldyrev.common.utilities.ErrorForwarder;
import ru.innopolis.smoldyrev.common.exceptions.*;
import ru.innopolis.smoldyrev.models.pojo.LangOwner;
import ru.innopolis.smoldyrev.models.pojo.User;
import org.apache.log4j.Logger;
import ru.innopolis.smoldyrev.service.LanguageService;
import ru.innopolis.smoldyrev.service.PersonService;
import ru.innopolis.smoldyrev.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

/**
 * Created by smoldyrev on 24.02.17.
 * Личный кабинет, данные о пользователе
 * doGet заполнение формы из БД
 * doPost запись в БД
 */
public class PrivateOfficeServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(PrivateOfficeServlet.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PersonService personService;

    @Autowired
    private LanguageService languageService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    /**
     * Открытие формы личного кабинета
     * загружаем данные пользоавтеля с id из sessionId
     * если id не совпадают редирект на ошибку
     * <p>
     * загружаем данные user, person, langOwner
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String id = (req.getParameter("id") == null) ? "" : req.getParameter("id");
        String sessionId = (req.getSession().getAttribute("sessionId") == null) ? "" :
                req.getSession().getAttribute("sessionId").toString();

        if (!id.equals(sessionId)) {
            try {
                throw new InvalidRoleException();
            } catch (InvalidRoleException e) {
                ErrorForwarder.forwardToErrorPage(req, resp,
                        "Вам не положенобыть здесь");
            }
        }

        try {
            User user = userService.getUserById(Integer.parseInt(id));
            List<LangOwner> languages = languageService.getLanguagesOnPerson(user.getPerson().getId());

            req.setAttribute("user", user);
            req.setAttribute("languages", languages);

            req.getRequestDispatcher("/rooms/privateoffice.jsp").forward(req, resp);
        } catch (UserServiceException e) {
            logger.error(e);
            ErrorForwarder.forwardToErrorPage(req, resp,
                    "Ошибка при получении доступа к таблице пользователей");
        } catch (UserNotFoundException e) {
            logger.error(e);
            ErrorForwarder.forwardToErrorPage(req, resp,
                    "Не найден пользователь");
        } catch (LanguageServiceException e) {
            logger.error(e);
            ErrorForwarder.forwardToErrorPage(req, resp,
                    "Проблемы с получением языков");
        }
    }

    /**
     * Закрытие личного кабинета с сохранением внесенных изменения
     * если все прошло удачно - редирект на основную форму
     * если ошибка при update - редирект на error.jsp
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int id = (int) req.getSession().getAttribute("sessionId");
        User user = null; // а надо ли?
        try {
            user = userService.getUserById(id);
        } catch (UserServiceException e) {
            logger.error(e);
            ErrorForwarder.forwardToErrorPage(req, resp,
                    "Ошибка при получении доступа к таблице пользователей");
        } catch (UserNotFoundException e) {
            logger.error(e);
            ErrorForwarder.forwardToErrorPage(req, resp,
                    "Не найден пользователь");
        }

        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("password"));
        user.getPerson().setFirstName(req.getParameter("firstName"));
        user.getPerson().setLastName(req.getParameter("lastName"));
        user.getPerson().setBirthday(Date.valueOf(req.getParameter("birthday")));
        user.getPerson().setEmail(req.getParameter("email"));
        user.getPerson().setPhoneNumber(req.getParameter("phoneNumber"));
        user.getPerson().setMale(new Boolean(req.getParameter("isMale")));

        try {
            personService.update(user.getPerson());
            userService.update(user);
            logger.trace("update " + user.getUserID() + " is ok");
            req.getRequestDispatcher("/rooms/generalchat").forward(req, resp);
        } catch (UserServiceException e) {
            logger.error(e);
            ErrorForwarder.forwardToErrorPage(req, resp,
                    "Ошибка при получении доступа к таблице пользователей");
        } catch (UserNotFoundException e) {
            logger.error(e);
            ErrorForwarder.forwardToErrorPage(req, resp,
                    "Не найден пользователь");
        } catch (PersonServiceException e) {
            logger.error(e);
            ErrorForwarder.forwardToErrorPage(req, resp,
                    "Не получена запись физического лица");
        }
    }
}
