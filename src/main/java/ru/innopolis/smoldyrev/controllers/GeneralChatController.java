package ru.innopolis.smoldyrev.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.innopolis.smoldyrev.common.exceptions.MessageServiceException;
import ru.innopolis.smoldyrev.common.exceptions.UserNotFoundException;
import ru.innopolis.smoldyrev.common.exceptions.UserServiceException;
import ru.innopolis.smoldyrev.models.pojo.Message;
import ru.innopolis.smoldyrev.models.pojo.User;
import ru.innopolis.smoldyrev.service.interfaces.IMessageService;
import ru.innopolis.smoldyrev.service.interfaces.IUserService;

import java.util.List;

@Controller
public class GeneralChatController {


    private static Logger logger = Logger.getLogger(GeneralChatController.class);

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
     * Открываем основную форму программы
     * загружаем все сообщения chatroom#0
     * загружаем всех пользователей
     * открываем форму
     */
    @RequestMapping(value = "/generalchat", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    public String showGenPage(Model model,
                              @RequestParam(name = "toUserId", required = false) String toUserId) throws Exception {

        if (!("guest".equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal()))) {
            User user = (User)
                    SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            List<Message> messages = messageService.getAllInRoom(0);
            List<User> users = userService.getAll();

            model.addAttribute("messages", messages);
            model.addAttribute("users", users);
            model.addAttribute("toUserId", toUserId);

            model.addAttribute("userFrom", user.getUserID());//1403
            model.addAttribute("chatRoom", 0);

            return "rooms/generalchat";
        } else {
            return "login";
        }
    }

    @ExceptionHandler({UserServiceException.class,
            MessageServiceException.class, Exception.class})
    public ModelAndView handleServiceException(Exception e) {
        logger.error(e);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("msg",e.getMessage());
        return modelAndView;
    }
}
