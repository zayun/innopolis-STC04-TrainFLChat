package ru.innopolis.smoldyrev.controllers;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import ru.innopolis.smoldyrev.common.exceptions.UserServiceException;
import ru.innopolis.smoldyrev.models.pojo.User;
import ru.innopolis.smoldyrev.service.interfaces.INotifyService;
import ru.innopolis.smoldyrev.service.interfaces.IUserService;


/**
 * Created by smoldyrev on 23.02.17.
 * Сервлет авторизации пользователей
 * проверяет соответсвие пользователя и пароля
 * и поле blocked должнобыть false
 */
@Controller
@SessionAttributes({"sessionUserId", "sessionUserType", "sessionLogin"})
public class AuthorizationController {

    private static Logger logger = Logger.getLogger(AuthorizationController.class);

    static {
        DOMConfigurator.configure("log4j.xml");
    }


    private IUserService userService;

    @Autowired
    private void setUserService(IUserService userService) {
        this.userService = userService;
    }

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
                        @RequestParam(name = "password") String password) throws Exception {

            User user = userService.authorize(login, password);

            if (user != null && !user.isBlocked()) {

                logger.trace(login + "/id=" + user.getUserID() + " authorize successful");

                model.addAttribute("sessionUserId", user.getUserID());
                model.addAttribute("sessionUserType", user.getUserType());
                model.addAttribute("sessionLogin", user.getLogin());

                return "redirect:" + "/generalchat";
            } else {
                logger.trace(login + " not authorize");
                String msg = "Комбинация логин/пароль не верна! Или пользователь заблокирован!";
                model.addAttribute("msg", msg);
                return "login";
            }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String login(Model model,
                        SessionStatus status) {

        status.setComplete();

        return "login";
    }

    @ExceptionHandler({UserServiceException.class,Exception.class})
    public ModelAndView handleUserServiceException(Exception e) {
        logger.error(e);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("msg",e.getMessage());
        return modelAndView;
    }

}
