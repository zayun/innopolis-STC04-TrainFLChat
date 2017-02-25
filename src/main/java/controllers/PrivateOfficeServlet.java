package controllers;

import models.dao.LanguageDAO;
import models.dao.UserDAO;
import models.pojo.LangOwner;
import models.pojo.Language;
import models.pojo.Person;
import models.pojo.User;
import org.apache.log4j.Logger;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smoldyrev on 24.02.17.
 */
public class PrivateOfficeServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(GeneralChatServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        UserDAO userDAO = new UserDAO();
        LanguageDAO languageDAO = new LanguageDAO();

        User user = userDAO.getEntityById(id);

        List<LangOwner> languages = languageDAO.getLanguagesOnPerson(user.getPerson().getId());

        req.setAttribute("id", user.getUserID());
        req.setAttribute("login", user.getLogin());
        req.setAttribute("password", user.getPassword());
        req.setAttribute("usertype", user.getUserType());

        req.setAttribute("firstName", user.getPerson().getFirstName());
        req.setAttribute("lastName", user.getPerson().getLastName());
        req.setAttribute("birthday", user.getPerson().getBirthDay());
        req.setAttribute("email", user.getPerson().getEmail());
        req.setAttribute("phoneNumber", user.getPerson().getPhoneNumber());
        req.setAttribute("male", user.getPerson().isMale());

        logger.debug("langSize"+languages.size());

        req.setAttribute("languages", languages);

        req.getRequestDispatcher("/privateoffice.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
