package ru.innopolis.smoldyrev.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.smoldyrev.common.exceptions.NotifyServiceException;
import ru.innopolis.smoldyrev.common.exceptions.UserNotFoundException;
import ru.innopolis.smoldyrev.common.exceptions.UserServiceException;
import ru.innopolis.smoldyrev.models.pojo.Notifyer;
import ru.innopolis.smoldyrev.service.NotifyService;
import ru.innopolis.smoldyrev.service.UserService;

import java.util.List;

/**
 * Created by smoldyrev on 07.03.17.
 */
@Controller
public class NotifyListController {

    private static Logger logger = Logger.getLogger(NotifyListController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private NotifyService notifyService;

    /**
     * Открываем список оповещений для этого пользователя
     */
    @RequestMapping(value = "/adm/notifylist", method = RequestMethod.GET)
    public String showNotifyListPage(Model model,
                                     @RequestParam(name = "userId") int userId) {

        try {
            List<Notifyer> notifyerList = notifyService.getAllByUser(userId);
            model.addAttribute("notifyers", notifyerList);
            model.addAttribute("userId", userId);

            return "/admin/notifyerlist";

        } catch (NotifyServiceException e) {
            logger.error(e);
            model.addAttribute("msg", "Ошибка при получении списка оповещени");
            return "error";
        }
    }

    /**
     * Добавляем оповещение для этого пользователя
     */
    @RequestMapping(value = "/adm/addnotify", method = RequestMethod.POST)
    public String addNotify(Model model,
                            @RequestParam(name = "userId") int userId,
                            @RequestParam(name = "notType") String notType) {

        try {
            Notifyer notifyer = new Notifyer();
            notifyer.setNotType(notType);
            notifyer.setUser(userService.getUserById(userId));
            notifyService.create(notifyer);
        } catch (UserServiceException e) {
            logger.error(e);
            model.addAttribute("msg", "Ошибка при получении доступа к таблице пользователей");
            return "error";
        } catch (UserNotFoundException e) {
            logger.error(e);
            model.addAttribute("msg", "Не найден пользователь");
            return "error";
        } catch (NotifyServiceException e) {
            logger.error(e);
            model.addAttribute("msg", "Ошибка при получении списка оповещений");
            return "error";
        }
        model.addAttribute("userId", userId);
        return "redirect:" + "/adm/notifylist";
    }

    /**
     * Удаляем оповещение по ИД
     */
    @RequestMapping(value = "/adm/delnotify", method = RequestMethod.POST)
    public String delNotify(Model model,
                            @RequestParam(name = "notId") int notId,
                            @RequestParam(name = "userId") int userId) {
        try {
            notifyService.delete(notId);
        } catch (NotifyServiceException e) {
            logger.error(e);
            model.addAttribute("msg", "Ошибка при получении списка оповещений");
            return "error";
        }
        model.addAttribute("userId", userId);
        return "redirect:" + "/adm/notifylist";
    }

    /**
     * Редактируем оповещение
     */
    @RequestMapping(value = "/adm/editnotify", method = RequestMethod.POST)
    public String editNotify(Model model,
                             @RequestParam(name = "notId") int notId,
                             @RequestParam(name = "notType") String notType,
                             @RequestParam(name = "userId") int userId) {

        try {
            Notifyer notifyer = notifyService.getNotifyById(notId);
            notifyer.setNotType(notType);
            notifyService.update(notifyer);
        } catch (NotifyServiceException e) {
            logger.error(e);
            model.addAttribute("msg", "Ошибка при получении списка оповещений");
            return "error";
        }

        model.addAttribute("userId", userId);
        return "redirect:" + "/adm/notifylist";
    }

}
