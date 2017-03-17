package ru.innopolis.smoldyrev.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.innopolis.smoldyrev.common.exceptions.UserNotFoundException;
import ru.innopolis.smoldyrev.common.exceptions.UserServiceException;
import ru.innopolis.smoldyrev.models.pojo.User;
import ru.innopolis.smoldyrev.service.interfaces.IUserService;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * Created by smoldyrev on 07.03.17.
 */
@Controller
public class AdminkaController {

    private static Logger logger = Logger.getLogger(AdminkaController.class);


    private IUserService userService;

    @Autowired
    private void setUserService(IUserService userService) {
        this.userService = userService;
    }

    /**
     * Заполняем список пользователей программы
     * и выводим jsp с этим списком
     */
    @RequestMapping(value = "/adm/adminoffice", method = RequestMethod.GET)
    @Secured("ROLE_ADMIN")
    public String showAdmPage(Model model) throws UserServiceException {

        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "admin/adminoffice";
    }

    /**
     * Изменяем административные данные пользователя
     * isBlocked - блокируем пользователя)
     */
    @RequestMapping(value = "/adm/edituserblock", method = RequestMethod.POST)
    @Secured("ROLE_ADMIN")
    public String editUser(Model model,
                           @RequestParam(name = "userId") String userId,
                           @RequestParam(name = "block") Boolean block) throws Exception {

        User user = userService.getUserById(Integer.parseInt(userId));

        if (block != null) {
            user.setBlocked(!user.isBlocked());
        }

        user = userService.update(user);
        logger.trace("update " + user.getUserID() + " is ok");

        return "redirect:" + "/adm/adminoffice";
    }

    /**
     * Изменяем административные данные пользователя
     * userType - роль для работы (admin/administrator/moder/user)
     */
    @RequestMapping(value = "/adm/editusertype", method = RequestMethod.POST)
    @Secured("ROLE_ADMIN")
    public String editUser(Model model,
                           @RequestParam(name = "userId") String userId,
                           @RequestParam(name = "usertype") String usertype) throws Exception {

        User user = userService.getUserById(Integer.parseInt(userId));
        if (usertype != null) {
            user.setUserType(usertype);
        }

        user = userService.update(user);
        logger.trace("update " + user.getUserID() + " is ok");

        return "redirect:" + "/adm/adminoffice";
    }

}
