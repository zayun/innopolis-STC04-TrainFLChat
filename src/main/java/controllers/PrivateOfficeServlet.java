package controllers;

import exceptions.InvalidRoleException;
import models.pojo.LangOwner;
import models.pojo.User;
import org.apache.log4j.Logger;
import service.LanguageService;
import service.PersonService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

/**
 * Created by smoldyrev on 24.02.17.
 * Личный кабинет, данные о пользователе
 * doGet заполнение формы из БД
 * doPost запись в БД
 */
public class PrivateOfficeServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(PrivateOfficeServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String  id = (req.getParameter("id")==null)?"":req.getParameter("id");
        String sessionId = (req.getSession().getAttribute("sessionId")==null)?"":
                req.getSession().getAttribute("sessionId").toString();

        if (!id.equals(sessionId)) {
            try {
                throw new InvalidRoleException();
            } catch (InvalidRoleException e) {
                req.setAttribute("msg", "Вам не плоложено здесь быть");
                req.getRequestDispatcher("/error.jsp").forward(req, resp);
            }
        }

        User user = UserService.getUserById(Integer.parseInt(id));

        List<LangOwner> languages = LanguageService.getLanguagesOnPerson(user.getPerson().getId());

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

        req.setAttribute("languages", languages);

        req.getRequestDispatcher("/rooms/privateoffice.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int id = (int) req.getSession().getAttribute("sessionId");
        User user = UserService.getUserById(id);

        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("password"));
        user.getPerson().setFirstName(req.getParameter("firstName"));
        user.getPerson().setLastName(req.getParameter("lastName"));
        user.getPerson().setBirthDay(Date.valueOf(req.getParameter("birthday")));
        user.getPerson().setEmail(req.getParameter("email"));
        user.getPerson().setPhoneNumber(req.getParameter("phoneNumber"));
        user.getPerson().setMale(new Boolean(req.getParameter("isMale")));

        if ((PersonService.update(user.getPerson())!=null)&&
                UserService.update(user)!=null) {
            logger.trace("update "+user.getUserID()+" is ok");
            req.getRequestDispatcher("/rooms/generalchat").forward(req, resp);
        } else {
            logger.trace("update "+user.getUserID()+" is false");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}
