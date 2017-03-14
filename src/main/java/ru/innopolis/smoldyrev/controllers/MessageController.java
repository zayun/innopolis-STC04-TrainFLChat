package ru.innopolis.smoldyrev.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.innopolis.smoldyrev.common.exceptions.*;
import ru.innopolis.smoldyrev.models.pojo.Message;
import ru.innopolis.smoldyrev.models.pojo.User;
import ru.innopolis.smoldyrev.service.interfaces.IMessageService;
import ru.innopolis.smoldyrev.service.interfaces.IUserService;

import java.time.LocalDateTime;

/**
 * Created by smoldyrev on 07.03.17.
 */
@Controller
@SessionAttributes("sessionUserId")
public class MessageController {


    private static Logger logger = Logger.getLogger(MessageController.class);

    private IUserService userService;

    private IMessageService messageService;

    @Autowired
    private void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Autowired
    private void setMessageService(IMessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Отправка сообщения
     * парметры userFrom, userTo, textMessage
     * после записи сообщения в БД редирект
     * на privatechatroom если chatroom!=0
     * на generalchat если chatroom==0;
     */
    @RequestMapping(value = "/sendmessage", method = RequestMethod.POST)
    public String sendMessage(Model model,
                              @ModelAttribute("sessionUserId") String fromUserId,
                              @RequestParam(name = "toUserId") String toUserId,
                              @RequestParam(name = "textMessage") String textMessage,
                              @RequestParam(name = "chatroom") int chatroom) throws UserServiceException, UserNotFoundException, MessageServiceException {


        toUserId = "".equals(toUserId) ? "999" : toUserId;
        User userFrom = userService.getUserById(Integer.parseInt(fromUserId));
        User userTo = userService.getUserById(Integer.parseInt(toUserId));

        if (userFrom == null || userTo == null) {
            throw new UserNotFoundException();
        }

        Message message = new Message();
        message.setDate(LocalDateTime.now());
        message.setFromUser(userFrom);
        message.setToUser(userTo);
        message.setBodyText(textMessage);
        message.setChatRoom(chatroom);

        messageService.sendMessage(message);

        model.addAttribute("chatroom", chatroom);
        if (chatroom == 0) {
            return "redirect:" + "/generalchat";
        } else {
            return "redirect:" + "/privatechatroom";
        }
    }

    /**
     * Получаем msgid - id сообщения в БД
     * отправляем запрос на удаление сообщения по id
     */
    @RequestMapping(value = "/delmessage", method = RequestMethod.POST)
    public String delMessage(Model model,
                             @RequestParam(name = "msgid") int msgid,
                             @RequestParam(name = "chatroom") int chatroom) throws Exception {
        messageService.deleteMessage(msgid);
        model.addAttribute("chatroom", chatroom);
        return (chatroom == 0) ? "redirect:/generalchat" : "redirect:/privatechatroom";
    }

    @ExceptionHandler({UserServiceException.class,
            MessageServiceException.class,
            UserNotFoundException.class,Exception.class})
    public ModelAndView handleServiceException(Exception e) {
        logger.error(e);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("msg",e.getMessage());
        return modelAndView;
    }
}
