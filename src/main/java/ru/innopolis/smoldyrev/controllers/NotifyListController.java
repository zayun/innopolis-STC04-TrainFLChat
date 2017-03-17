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
import ru.innopolis.smoldyrev.common.exceptions.MessageServiceException;
import ru.innopolis.smoldyrev.common.exceptions.NotifyServiceException;
import ru.innopolis.smoldyrev.common.exceptions.UserNotFoundException;
import ru.innopolis.smoldyrev.common.exceptions.UserServiceException;
import ru.innopolis.smoldyrev.models.pojo.Notifyer;
import ru.innopolis.smoldyrev.service.interfaces.INotifyService;
import ru.innopolis.smoldyrev.service.interfaces.IUserService;

import java.util.List;

/**
 * Created by smoldyrev on 07.03.17.
 */
@Controller
public class NotifyListController {

    private static Logger logger = Logger.getLogger(NotifyListController.class);

    private IUserService userService;

    private INotifyService notifyService;

    @Autowired
    private void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Autowired
    private void setNotifyService(INotifyService notifyService) {
        this.notifyService = notifyService;
    }

    /**
     * Открываем список оповещений для этого пользователя
     * @param userId - пользователь
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/adm/notifylist", method = RequestMethod.GET)
    public String showNotifyListPage(Model model,
                                     @RequestParam(name = "userId") int userId) throws Exception {

        List<Notifyer> notifyerList = notifyService.getAllByUser(userId);
        model.addAttribute("notifyers", notifyerList);
        model.addAttribute("userId", userId);

        return "/admin/notifyerlist";
    }

    /**
     * Добавляем оповещение для этого пользователя
     * @param notType -тип оповещения
     * @param userId - пользователь
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/adm/addnotify", method = RequestMethod.POST)
    public String addNotify(Model model,
                            @RequestParam(name = "userId") int userId,
                            @RequestParam(name = "notType") String notType) throws Exception {

        Notifyer notifyer = new Notifyer();
        notifyer.setNotType(notType);
        notifyer.setUser(userService.getUserById(userId));
        notifyService.create(notifyer);

        model.addAttribute("userId", userId);
        return "redirect:" + "/adm/notifylist";
    }

    /**
     * Удаляем оповещение по ИД
     * @param notId - айдишник оповещения
     * @param userId - пользователь
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/adm/delnotify", method = RequestMethod.POST)
    public String delNotify(Model model,
                            @RequestParam(name = "notId") int notId,
                            @RequestParam(name = "userId") int userId) throws Exception {
        notifyService.delete(notId);

        model.addAttribute("userId", userId);
        return "redirect:" + "/adm/notifylist";
    }

    /**
     * Редактируем оповещение
     * @param notId - айдишник оповещения
     * @param notType -тип оповещения
     * @param userId - пользователь
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/adm/editnotify", method = RequestMethod.POST)
    public String editNotify(Model model,
                             @RequestParam(name = "notId") int notId,
                             @RequestParam(name = "notType") String notType,
                             @RequestParam(name = "userId") int userId) throws Exception {

        Notifyer notifyer = notifyService.getNotifyById(notId);
        notifyer.setNotType(notType);
        notifyService.update(notifyer);

        model.addAttribute("userId", userId);
        return "redirect:" + "/adm/notifylist";
    }

}
