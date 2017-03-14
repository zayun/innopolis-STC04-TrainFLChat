package ru.innopolis.smoldyrev.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.innopolis.smoldyrev.common.exceptions.UserServiceException;
import ru.innopolis.smoldyrev.models.pojo.Person;
import ru.innopolis.smoldyrev.models.pojo.User;
import ru.innopolis.smoldyrev.service.interfaces.IUserService;

import java.sql.Date;

/**
 * Created by smoldyrev on 23.02.17.
 * doGet открывает форму регистрации
 * doPost записывает данные в БД - регистрирует пользователя
 */
@Controller
public class RegistrationController {

    private static Logger logger = Logger.getLogger(RegistrationController.class);


    private IUserService userService;

    @Autowired
    private void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationPage(Model model) {
        return "registration";
    }

    /**
     * Запись данных внесенных на форму registration.jsp в БД
     * при любом исходе редирект на login.jsp
     * с сообщением о результате
     */
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String login(Model model,
                        @RequestParam(name = "login") String login,
                        @RequestParam(name = "password") String password,
                        @RequestParam(name = "firstName") String firstName,
                        @RequestParam(name = "lastName") String lastName,
                        @RequestParam(name = "birthday") String birthday,
                        @RequestParam(name = "email") String email,
                        @RequestParam(name = "phoneNumber") String phoneNumber,
                        @RequestParam(name = "male", required = false) Boolean male) throws Exception {

        Date date = new Date(1L);
        if (!"".equals(birthday)) {
            date = Date.valueOf(birthday);
        }

        if (male==null) male=false;
        Person person = new Person(
                0,
                firstName,
                lastName,
                email,
                phoneNumber,
                date,
                (male==null)?false:male);
        User user = new User(
                0,
                "ROLE_USER",
                login,
                password,
                person,
                false);

            userService.registration(user);
            logger.trace(user.getUserID() + "/" + user.getLogin() + " registration successful");
            model.addAttribute("msg", "registration " + user.getLogin() + " completed successfully");
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