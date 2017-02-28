package controllers;

import models.pojo.User;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by smoldyrev on 23.02.17.
 * Сервлет авторизации пользователей
 * проверяет соответсвие пользователя и пароля
 * и поле blocked должнобыть false
 */
public class AuthorizationServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(AuthorizationServlet.class);

    static {
        DOMConfigurator.configure("log4j.xml");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("authGet");
    }

    /**Проверка наличия пользователя с таким паролем в БД
     * если пользовательнайден и не имеет флага isBlocked = true
     * открываем generalchat
     * и заполняем параметры сессии
     * sessionLogin, sessionId, sessionUserType*/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        User user = UserService.authorize(login, password);
        if (user != null && !user.isBlocked()) {

            logger.trace(login +"/id=" + user.getUserID()+ " authorize successful");

            /*тут вопрос надо ли засовывать с атрибут сессии целый объект?*/
            req.getSession().setAttribute("sessionLogin",login);
            req.getSession().setAttribute("sessionId",user.getUserID());
            req.getSession().setAttribute("sessionUserType",user.getUserType());

            req.getRequestDispatcher("/rooms/generalchat").forward(req, resp);
        } else {
            logger.trace(login + " not authorize");
            String msg = "Комбинация логин/пароль не верна!\nИли пользователь заблокирован!";
            req.setAttribute("msg", msg);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
