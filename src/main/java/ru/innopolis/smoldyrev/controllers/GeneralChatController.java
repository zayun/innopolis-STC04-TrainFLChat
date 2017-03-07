package ru.innopolis.smoldyrev.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.smoldyrev.common.exceptions.MessageServiceException;
import ru.innopolis.smoldyrev.common.exceptions.UserServiceException;
import ru.innopolis.smoldyrev.models.pojo.Message;
import ru.innopolis.smoldyrev.models.pojo.User;
import ru.innopolis.smoldyrev.service.MessageService;
import ru.innopolis.smoldyrev.service.UserService;

import java.util.List;

@Controller
@SessionAttributes({"sessionUserId", "sessionUserType", "sessionLogin"})
public class GeneralChatController {


    private static Logger logger = Logger.getLogger(GeneralChatController.class);

    @Autowired
    private UserService userService;

    @Autowired(required = true)
    private MessageService messageService;


    /**Открываем основную форму программы
     * загружаем все сообщения chatroom#0
     * загружаем всех пользователей
     * открываем форму*/
    @RequestMapping(value = "/generalchat", method = RequestMethod.GET)
    public String showGenPage(Model model,
                              @ModelAttribute("sessionUserId") String userId) {

        try {
            List<Message> messages = messageService.getAllInRoom(0);
            List<User> users = userService.getAll();

            model.addAttribute("messages", messages);
            model.addAttribute("users", users);
        } catch (MessageServiceException e) {
            logger.error(e);
            model.addAttribute("msg", "messages load is failed");
            return "error";
        } catch (UserServiceException e) {
            logger.error(e);
            model.addAttribute("msg", "messages load is failed");
            return "error";
        }

        model.addAttribute("userFrom",userId);
        model.addAttribute("chatRoom",0);

        return "rooms/generalchat";
    }
}
