package ru.innopolis.smoldyrev.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.smoldyrev.common.exceptions.UserNotFoundException;
import ru.innopolis.smoldyrev.common.exceptions.UserServiceException;
import ru.innopolis.smoldyrev.common.utilities.ErrorForwarder;
import ru.innopolis.smoldyrev.controllers.servlet.AdminkaServlet;
import ru.innopolis.smoldyrev.models.pojo.User;
import ru.innopolis.smoldyrev.service.UserService;

import java.util.List;

/**
 * Created by smoldyrev on 07.03.17.
 */
@Controller
public class AdminkaController {

    private static Logger logger = Logger.getLogger(AdminkaController.class);

    @Autowired
    private UserService userService;

    /**Заполняем список пользователей программы
     * и выводим jsp с этим списком*/
    @RequestMapping(value = "/adm/adminoffice", method = RequestMethod.GET)
    public String showAdmPage(Model model) {

        try {
            List<User> users = userService.getAll();
            model.addAttribute("users", users);
        } catch (UserServiceException e) {
            logger.error(e);
            model.addAttribute("msg", "Ошибка при получении доступа к таблице пользователей");
            return "error";
        }
        return "admin/adminoffice";
    }

    /**
     * Изменяем административные данные пользователя
     * isBlocked - блокируем пользователя)
     */
    @RequestMapping(value = "/adm/edituserblock", method = RequestMethod.POST)
    public String editUser(Model model,
                           @RequestParam(name = "userId") String userId,
                           @RequestParam(name = "block") Boolean block) {

        try {
            User user = userService.getUserById(Integer.parseInt(userId));

            if (block != null) {
                user.setBlocked(!user.isBlocked());
            }

            user = userService.update(user);
            logger.trace("update " + user.getUserID() + " is ok");

            return "redirect:" + "/adm/adminoffice";
        } catch (UserServiceException e) {
            logger.error(e);
            model.addAttribute("msg", "Ошибка при получении доступа к таблице пользователей");
            return "error";
        } catch (UserNotFoundException e) {
            logger.error(e);
            model.addAttribute("msg", "Не найден обновляемый пользователь");
            return "error";
        }
    }

    /**
     * Изменяем административные данные пользователя
     * userType - роль для работы (admin/administrator/moder/user)
     */
    @RequestMapping(value = "/adm/editusertype", method = RequestMethod.POST)
    public String editUser(Model model,
                           @RequestParam(name = "userId") String userId,
                           @RequestParam(name = "usertype") String usertype) {

        try {
            User user = userService.getUserById(Integer.parseInt(userId));
            if (usertype != null) {
                user.setUserType(usertype);
            }

            user = userService.update(user);
            logger.trace("update " + user.getUserID() + " is ok");

            return "redirect:" + "/adm/adminoffice";

        } catch (UserServiceException e) {
            logger.error(e);
            model.addAttribute("msg", "Ошибка при получении доступа к таблице пользователей");
            return "error";
        } catch (UserNotFoundException e) {
            logger.error(e);
            model.addAttribute("msg", "Не найден обновляемый пользователь");
            return "error";
        }
    }
}
