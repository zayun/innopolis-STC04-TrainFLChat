package ru.innopolis.smoldyrev.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.innopolis.smoldyrev.common.exceptions.*;
import ru.innopolis.smoldyrev.models.pojo.LangOwner;
import ru.innopolis.smoldyrev.models.pojo.User;
import ru.innopolis.smoldyrev.service.interfaces.ILanguageService;
import ru.innopolis.smoldyrev.service.interfaces.IPersonService;
import ru.innopolis.smoldyrev.service.interfaces.IUserService;

import java.sql.Date;
import java.util.List;

/**
 * Created by smoldyrev on 07.03.17.
 */
@Controller
@SessionAttributes({"sessionUserId", "sessionUserType", "sessionLogin"})
public class PrivateOfficeController {

    private static Logger logger = Logger.getLogger(PrivateOfficeController.class);

    private IUserService userService;

    private IPersonService personService;

    private ILanguageService languageService;

    @Autowired
    private void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Autowired
    private void setPersonService(IPersonService personService) {
        this.personService = personService;
    }

    @Autowired
    private void setLanguageService(ILanguageService languageService) {
        this.languageService = languageService;
    }

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
                                       @RequestParam(name = "userId") String userId) throws InvalidRoleException, UserServiceException, UserNotFoundException, LanguageServiceException {

        if (!userId.equals(sessionUserId)) {
                throw new InvalidRoleException("У вас нет прав на посещение этой страницы!");
        }

            User user = userService.getUserById(Integer.parseInt(userId));
            List<LangOwner> languages = languageService.getLanguagesOnPerson(user.getPerson().getId());

            model.addAttribute("user", user);
            model.addAttribute("languages", languages);

            return "/rooms/privateoffice";
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
                              @RequestParam(name = "male") Boolean isMale) throws PersonServiceException, UserServiceException, UserNotFoundException {

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
    }

    @ExceptionHandler({UserServiceException.class,
            PersonServiceException.class,
            LanguageServiceException.class,
            InvalidRoleException.class,
            UserNotFoundException.class})
    public ModelAndView handleServiceException(Exception e) {
        logger.error(e);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("msg",e.getMessage());
        return modelAndView;
    }
}