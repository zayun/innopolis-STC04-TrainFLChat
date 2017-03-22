package ru.innopolis.smoldyrev.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.smoldyrev.models.pojo.LangOwner;
import ru.innopolis.smoldyrev.models.pojo.Language;
import ru.innopolis.smoldyrev.models.pojo.User;
import ru.innopolis.smoldyrev.service.LanguageService;
import ru.innopolis.smoldyrev.service.interfaces.ILanguageService;
import ru.innopolis.smoldyrev.service.interfaces.IUserService;

/**
 * Created by smoldyrev on 11.03.17.
 */
@Controller
public class LanguageController {

    private static Logger logger = Logger.getLogger(LanguageController.class);

    private IUserService userService;

    private ILanguageService languageService;

    @Autowired
    private void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setLanguageService(ILanguageService languageService) {
        this.languageService = languageService;
    }


    /**
     * Добавляем язык для пользователя
     * @param userId - пользователь
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @RequestMapping(value = "/addlanguage", method = RequestMethod.POST)
    public String addLanguage(Model model,
                              @ModelAttribute("userId") Integer userId,
                            @RequestParam(name = "langId") String langId) throws Exception {
        User user = userService.getUserById(userId);
        Integer personId = user.getPerson().getId();
        languageService.addLangToPerson(personId, langId);

        model.addAttribute("user", user);
        model.addAttribute("languages", user.getPerson().getLanguages());

        return "redirect:" + "/privateoffice";
    }
}
