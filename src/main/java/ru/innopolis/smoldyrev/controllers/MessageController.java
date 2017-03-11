package ru.innopolis.smoldyrev.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.smoldyrev.common.exceptions.MessageServiceException;
import ru.innopolis.smoldyrev.common.exceptions.UserNotFoundException;
import ru.innopolis.smoldyrev.common.exceptions.UserServiceException;
import ru.innopolis.smoldyrev.models.pojo.Message;
import ru.innopolis.smoldyrev.models.pojo.User;
import ru.innopolis.smoldyrev.service.MessageService;
import ru.innopolis.smoldyrev.service.UserService;

/**
 * Created by smoldyrev on 07.03.17.
 */
@Controller
@SessionAttributes("sessionUserId")
public class MessageController {


    private static Logger logger = Logger.getLogger(MessageController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

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
                              @RequestParam(name = "chatroom") int chatroom) {


        try {

            toUserId = "".equals(toUserId)?"999":toUserId;
            User userFrom = userService.getUserById(Integer.parseInt(fromUserId));
            User userTo = userService.getUserById(Integer.parseInt(toUserId));

            if (userFrom == null || userTo == null) {
                throw new UserNotFoundException();
            }

            Message message = new Message();
            message.setFromUser(userFrom);
            message.setToUser(userTo);
            message.setBodyText(textMessage);
            message.setChatRoom(chatroom);

            messageService.sendMessage(message);

            model.addAttribute("chatroom", chatroom);
            if (chatroom == 0) {
                return "redirect:"+"/generalchat";
            } else {
                return "redirect:"+"/privatechatroom";
            }

        } catch (UserServiceException e) {
            logger.error(e);
            model.addAttribute("msg", "Ошибка при получении доступа к таблице пользователей");
            return "error";
        } catch (UserNotFoundException e) {
            logger.error(e);
            model.addAttribute("msg", "Отправка сообщения не удалась, не найден пользователь");
            return "error";
        } catch (MessageServiceException e) {
            logger.error(e);
            model.addAttribute("msg", "Ошибка при отправке сообщения");
            return "error";
        }
    }

    /**Получаем msgid - id сообщения в БД
     * отправляем запрос на удаление сообщения по id*/
    @RequestMapping(value = "/delmessage", method = RequestMethod.POST)
    public String delMessage(Model model,
                              @RequestParam(name = "msgid") int msgid,
                             @RequestParam(name = "chatroom") int chatroom) {
        try {
            messageService.deleteMessage(msgid);
            model.addAttribute("chatroom", chatroom);
            return (chatroom==0)?"redirect:/generalchat":"redirect:/privatchatroom";
        } catch (MessageServiceException e) {
            logger.error(e);
            model.addAttribute("msg", "Ошибка удаления сообщения");
            return "error";
        }
    }
}
