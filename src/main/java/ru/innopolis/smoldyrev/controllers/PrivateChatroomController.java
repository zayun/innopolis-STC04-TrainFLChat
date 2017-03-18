package ru.innopolis.smoldyrev.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.innopolis.smoldyrev.common.exceptions.ChatroomIsBusyException;
import ru.innopolis.smoldyrev.common.exceptions.MessageServiceException;
import ru.innopolis.smoldyrev.common.exceptions.PersonServiceException;
import ru.innopolis.smoldyrev.models.dao.interfaces.IConverseDAO;
import ru.innopolis.smoldyrev.models.pojo.*;
import ru.innopolis.smoldyrev.service.interfaces.IConverseService;
import ru.innopolis.smoldyrev.service.interfaces.IMessageService;
import ru.innopolis.smoldyrev.service.interfaces.IUserService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by smoldyrev on 07.03.17.
 */
@Controller
public class PrivateChatroomController {

    private static Logger logger = Logger.getLogger(PrivateChatroomController.class);

    private IMessageService messageService;

    private IUserService userService;

    private IConverseService converseService;


    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Autowired
    private void setMessageService(IMessageService messageService) {
        this.messageService = messageService;
    }

    @Autowired
    public void setConverseService(IConverseService converseService) {
        this.converseService = converseService;
    }

    /**
     * Открываем чат комнату
     * загружаем все сообщения chatroom
     * открываем форму
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @RequestMapping(value = "/privatechatroom", method = RequestMethod.GET)
    public String showGenPage(Model model,
                              @RequestParam(name = "chatroom") int chatroom) throws Exception {

        User user = (User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (converseService.checkConverseMember(chatroom, user.getUserID())) {

            List<Message> messages = null;
            messages = messageService.getAllInRoom(chatroom);

            model.addAttribute("messages", messages);
            model.addAttribute("chatroom", chatroom);

            return "/rooms/privatechatroom";
        } else {
            throw new ChatroomIsBusyException("Room #" + chatroom + " is busy\nYou're not a member!");
        }


    }

    /**
     * Создаем беседу
     * открываем форму
     * @param chatroom -номер комнаты
     * @param converse - номер беседы
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @RequestMapping(value = "/createconverse", method = RequestMethod.GET)
    public String openConverse(Model model,
                               @RequestParam(name = "chatroom") int chatroom,
                               @RequestParam(name = "converse") int converse) throws Exception {

        converse = converseService.createConversation(chatroom, LocalDateTime.now());
        model.addAttribute("chatroom", chatroom);
        model.addAttribute("converse", converse);
        model.addAttribute("start_date", LocalDateTime.now());

        model.addAttribute("users", userService.getAllInConverse(converse));

        return "/rooms/createconverse";

    }


    /**
     * Открываем форму просмотра бесед
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @RequestMapping(value = "/checkconversation", method = RequestMethod.GET)
    public String checkConversation(Model model) throws Exception {

        model.addAttribute("conversations", converseService.getActiveConversation(LocalDateTime.now()));

        return "/rooms/checkconversation";
    }

    /**
     * Добавляем члена беседы
     * @param userId - добавляемый юзер
     * @param chatroom -номер комнаты
     * @param converse - номер беседы
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @RequestMapping(value = "/addconversemember", method = RequestMethod.POST)
    public String addConverseMember(Model model,
                                    @RequestParam("userId") int userId,
                                    @RequestParam("converse") int converse,
                                    @RequestParam("chatroom") int chatroom) throws Exception {


        model.addAttribute("userId", userId);
        model.addAttribute("converse", converse);
        model.addAttribute("chatroom", chatroom);


        if (converseService.addConverseMember(userId, converse)) {
            return "redirect:/createconverse";
        }

        return "redirect:/createconverse";
    }
}

