package ru.innopolis.smoldyrev.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.smoldyrev.common.exceptions.MessageServiceException;
import ru.innopolis.smoldyrev.common.exceptions.UserServiceException;
import ru.innopolis.smoldyrev.common.utilities.ErrorForwarder;
import ru.innopolis.smoldyrev.models.pojo.Message;
import ru.innopolis.smoldyrev.models.pojo.User;
import ru.innopolis.smoldyrev.service.MessageService;

import java.util.List;


/**
 * Created by smoldyrev on 07.03.17.
 */
@Controller
public class PrivateChatroomController {

    private static Logger logger = Logger.getLogger(PrivateChatroomController.class);

    @Autowired
    private MessageService messageService;

    /**
     * Открываем чат комнату
     * загружаем все сообщения chatroom
     * загружаем всех пользователей
     * открываем форму
     */
    @RequestMapping(value = "/privatechatroom", method = RequestMethod.GET)
    public String showGenPage(Model model,
                              @ModelAttribute("sessionUserId") String userId,
                              @RequestParam(name = "chatroom") int chatroom) {

        List<Message> messages = null;
        try {
            messages = messageService.getAllInRoom(chatroom);

            model.addAttribute("messages", messages);
            model.addAttribute("chatroom", chatroom);

            return "/rooms/privatechatroom";
        } catch (MessageServiceException e) {
            logger.error(e);
            model.addAttribute("msg", "Ошибка получения сообщений");
            return "error";
        }
    }
}

