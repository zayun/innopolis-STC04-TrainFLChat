package ru.innopolis.smoldyrev.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.smoldyrev.common.exceptions.*;
import ru.innopolis.smoldyrev.common.utilities.ErrorForwarder;
import ru.innopolis.smoldyrev.models.pojo.LangOwner;
import ru.innopolis.smoldyrev.models.pojo.User;
import ru.innopolis.smoldyrev.service.LanguageService;
import ru.innopolis.smoldyrev.service.PersonService;
import ru.innopolis.smoldyrev.service.UserService;

import java.sql.Date;
import java.util.List;

/**
 * Created by smoldyrev on 07.03.17.
 */
@Controller
@SessionAttributes({"sessionUserId", "sessionUserType", "sessionLogin"})
public class PrivateOfficeController {

    private static Logger logger = Logger.getLogger(PrivateOfficeController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PersonService personService;

    @Autowired
    private LanguageService languageService;

    /**
     * Открытие формы личного кабинета
     * загружаем данные пользоавтеля с id из sessionId
     * если id не совпадают редирект на ошибку
     * <p>
     * загружаем данные user, person, langOwner
     */
    @RequestMapping(value = "/privateoffice", method = RequestMethod.GET)
    public String showPrivateOfficePage(Model model,
                                       @ModelAttribute("sessionUserId") String sessionUserId,
                                       @RequestParam(name = "userId") String userId) {

        if (!userId.equals(sessionUserId)) {
            try {
                throw new InvalidRoleException();
            } catch (InvalidRoleException e) {
                logger.error(e);
                model.addAttribute("msg", "Вам не положенобыть здесь");
                return "error";
            }
        }

        try {
            User user = userService.getUserById(Integer.parseInt(userId));
            List<LangOwner> languages = languageService.getLanguagesOnPerson(user.getPerson().getId());

            model.addAttribute("user", user);
            model.addAttribute("languages", languages);

            return "/rooms/privateoffice";
        } catch (UserServiceException e) {
            logger.error(e);
            model.addAttribute("msg", "Ошибка при получении доступа к таблице пользователей");
            return "error";
        } catch (UserNotFoundException e) {
            logger.error(e);
            model.addAttribute("msg", "Не найден пользователь");
            return "error";
        } catch (LanguageServiceException e) {
            logger.error(e);
            model.addAttribute("msg", "Проблемы с получением языков");
            return "error";
        }
    }


    /**
     * Закрытие личного кабинета с сохранением внесенных изменения
     * если все прошло удачно - редирект на основную форму
     * если ошибка при update - редирект на error.jsp
     */
    @RequestMapping(value = "/privateoffice", method = RequestMethod.POST)
    public String saveNewInfo(Model model,
                              @ModelAttribute("sessionUserId") String sessionUserId,
                              @RequestParam(name = "id") int id,
                              @RequestParam(name = "login") String login,
                              @RequestParam(name = "password") String password,
                              @RequestParam(name = "firstName") String firstName,
                              @RequestParam(name = "lastName") String lastName,
                              @RequestParam(name = "birthday") Date birthday,
                              @RequestParam(name = "email") String email,
                              @RequestParam(name = "phoneNumber") String phoneNumber,
                              @RequestParam(name = "male") Boolean isMale) {

        try {
            User user = userService.getUserById(id);
            user.setLogin(login);
            user.setPassword(password);
            user.getPerson().setFirstName(firstName);
            user.getPerson().setLastName(lastName);
            user.getPerson().setBirthday(birthday);
            user.getPerson().setEmail(email);
            user.getPerson().setPhoneNumber(phoneNumber);
            user.getPerson().setMale(isMale);

            personService.update(user.getPerson());
            userService.update(user);

            return "redirect:/generalchat";
        } catch (UserServiceException e) {
            logger.error(e);
            model.addAttribute("msg", "Ошибка при получении доступа к таблице пользователей");
            return "error";
        } catch (UserNotFoundException e) {
            logger.error(e);
            model.addAttribute("msg", "Не найден пользователь");
            return "error";
        } catch (PersonServiceException e) {
            logger.error(e);
            model.addAttribute("msg", "Не получена запись физического лица");
            return "error";
        }
    }
}
