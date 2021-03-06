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
public class AuthorizationController {

    private static Logger logger = Logger.getLogger(AuthorizationController.class);

    static {
        DOMConfigurator.configure("log4j.xml");
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage(Model model,
                                @RequestParam(name = "msg") String msg) {
        model.addAttribute("msg",msg);
        return "login";
    }

}
