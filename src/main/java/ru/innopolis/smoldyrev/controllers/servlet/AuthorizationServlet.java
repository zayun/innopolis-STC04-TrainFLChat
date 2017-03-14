package ru.innopolis.smoldyrev.controllers.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.innopolis.smoldyrev.common.utilities.ErrorForwarder;
import ru.innopolis.smoldyrev.common.utilities.MailMaker;
import ru.innopolis.smoldyrev.common.exceptions.NotifyServiceException;
import ru.innopolis.smoldyrev.common.exceptions.UserServiceException;
import ru.innopolis.smoldyrev.models.pojo.Notifyer;
import ru.innopolis.smoldyrev.models.pojo.User;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import ru.innopolis.smoldyrev.service.NotifyService;
import ru.innopolis.smoldyrev.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by smoldyrev on 23.02.17.
 * Сервлет авторизации пользователей
 * проверяет соответсвие пользователя и пароля
 * и поле blocked должнобыть false
 */
public class AuthorizationServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(AuthorizationServlet.class);

    @Autowired
    private UserService userService;

    @Autowired
    private NotifyService notifyService;

    static {
        DOMConfigurator.configure("log4j.xml");
    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("authGet");
    }

    /**
     * Проверка наличия пользователя с таким паролем в БД
     * если пользовательнайден и не имеет флага isBlocked = true
     * открываем generalchat
     * и заполняем параметры сессии
     * sessionLogin, sessionId, sessionUserType
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User user = null;

        try {
            user = userService.authorize(login, password);
        } catch (UserServiceException e) {
            logger.error(e);
            ErrorForwarder.forwardToErrorPage(req, resp, "Ошибка при проверке введенных данных");
        }

        if (user != null && !user.isBlocked()) {

            logger.trace(login + "/id=" + user.getUserID() + " authorize successful");

            req.getSession().setAttribute("sessionLogin", login);
            req.getSession().setAttribute("sessionId", user.getUserID());
            req.getSession().setAttribute("sessionUserType", user.getUserType());

            if ("admin".equals(user.getUserType())) notifyAboutAdmLog();

            req.getRequestDispatcher("/rooms/generalchat").forward(req, resp);
        } else {
            logger.trace(login + " not authorize");
            String msg = "Комбинация логин/пароль не верна! Или пользователь заблокирован!";
            req.setAttribute("msg", msg);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    public void notifyAboutAdmLog() {
        try {
            List<Notifyer> notifyers = notifyService.getAllByNotType("admLog");
            /*запустим в отдельном потоке, чтобы не повесить систему при ожидании МэйлСервера*/
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {

                    for (Notifyer notifyer :
                            notifyers) {
                        logger.debug("mail send to " + notifyer.getUser().getPerson().getEmail());
                        MailMaker.sendEmail(notifyer.getUser().getPerson().getEmail(),
                                "admin " + notifyer.getUser().getLogin() + "is logged in",
                                "admin with login " + notifyer.getUser().getLogin() +
                                        " is logged in programm in " + new Timestamp(System.currentTimeMillis()));
                    }
                }
            });

//            thread.start(); //пока не надо, не мучаем мэйл
        } catch (NotifyServiceException e) {
            logger.error(e);
        }
    }
}
