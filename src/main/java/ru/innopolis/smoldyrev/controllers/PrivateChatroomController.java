package ru.innopolis.smoldyrev.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.innopolis.smoldyrev.common.exceptions.MessageServiceException;
import ru.innopolis.smoldyrev.common.exceptions.PersonServiceException;
import ru.innopolis.smoldyrev.models.pojo.Message;
import ru.innopolis.smoldyrev.service.interfaces.IMessageService;

import java.util.List;


/**
 * Created by smoldyrev on 07.03.17.
 */
@Controller
public class PrivateChatroomController {

    private static Logger logger = Logger.getLogger(PrivateChatroomController.class);

    private IMessageService messageService;

    @Autowired
    private void setMessageService(IMessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Открываем чат комнату
     * загружаем все сообщения chatroom
     * загружаем всех пользователей
     * открываем форму
     */
    @RequestMapping(value = "/privatechatroom", method = RequestMethod.GET)
    public String showGenPage(Model model,
                              @ModelAttribute("sessionUserId") String userId,
                              @RequestParam(name = "chatroom") int chatroom) throws MessageServiceException {

        List<Message> messages = null;
            messages = messageService.getAllInRoom(chatroom);

            model.addAttribute("messages", messages);
            model.addAttribute("chatroom", chatroom);

            return "/rooms/privatechatroom";
    }

    @ExceptionHandler(MessageServiceException.class)
    public ModelAndView handleMessageServiceException(Exception e) {
        logger.error(e);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("msg",e.getMessage());
        return modelAndView;
    }
}

