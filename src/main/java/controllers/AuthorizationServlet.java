package controllers;

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (UserService.authorize(login, password)) {
            logger.trace(login + " authorize successful");
            req.setAttribute("wname",login);
            req.getRequestDispatcher("/generalchat.jsp").forward(req, resp);
//            resp.sendRedirect("/chat/generalchat");
        } else {
            logger.trace(login + " not authorize");
            String msg = "Комбинация логин/пароль не верна!";
            req.setAttribute("msg",msg);
            req.getRequestDispatcher("/").forward(req, resp);
        }
    }
}
