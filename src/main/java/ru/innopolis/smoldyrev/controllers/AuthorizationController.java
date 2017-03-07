package ru.innopolis.smoldyrev.controllers;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import ru.innopolis.smoldyrev.common.exceptions.UserServiceException;
import ru.innopolis.smoldyrev.common.utilities.NotifyThread;
import ru.innopolis.smoldyrev.controllers.servlet.AuthorizationServlet;
import ru.innopolis.smoldyrev.models.pojo.User;
import ru.innopolis.smoldyrev.service.NotifyService;
import ru.innopolis.smoldyrev.service.UserService;

@Controller
@SessionAttributes({"sessionId", "sessionId"})
public class AuthorizationController {

    private static Logger logger = Logger.getLogger(AuthorizationServlet.class);

    static {
        DOMConfigurator.configure("log4j.xml");
    }

    @Autowired
    private UserService userService;

    @Autowired
    private NotifyService notifyService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage(Model model) {
        return "login";
    }

    /**
     * Проверка наличия пользователя с таким паролем в БД
     * если пользовательнайден и не имеет флага isBlocked = true
     * открываем generalchat
     * и заполняем параметры сессии
     * sessionLogin, sessionId, sessionUserType
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model,
                        @RequestParam(name = "login") String login,
                        @RequestParam(name = "password") String password) {

        try {
            User user = userService.authorize(login, password);

            if (user != null && !user.isBlocked()) {

                logger.trace(login + "/id=" + user.getUserID() + " authorize successful");

                model.addAttribute("userID", user.getUserID());
                model.addAttribute("userSession", user.getUserType());

                if ("admin".equals(user.getUserType())) new NotifyThread("admLog").run();

                return "redirect:" + "/generalchat";
            } else {
                logger.trace(login + " not authorize");
                String msg = "Комбинация логин/пароль не верна! Или пользователь заблокирован!";
                model.addAttribute("msg", msg);
                return "login";
            }
        } catch (UserServiceException e) {
            logger.error(e);
        }
        return "login";
    }

}
