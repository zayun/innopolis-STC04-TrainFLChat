package ru.innopolis.smoldyrev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.innopolis.smoldyrev.common.utilities.ErrorForwarder;
import ru.innopolis.smoldyrev.common.exceptions.NotifyServiceException;
import ru.innopolis.smoldyrev.common.exceptions.UserNotFoundException;
import ru.innopolis.smoldyrev.common.exceptions.UserServiceException;
import ru.innopolis.smoldyrev.models.pojo.Notifyer;
import org.apache.log4j.Logger;
import ru.innopolis.smoldyrev.service.NotifyService;
import ru.innopolis.smoldyrev.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by smoldyrev on 27.02.17.
 * изменение данных о получении оповещений
 */
public class EditNotifyListServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(EditNotifyListServlet.class);

    @Autowired
    private UserService userService;

    @Autowired
    private NotifyService notifyService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        goToPage(Integer.parseInt(req.getParameter("userID")), req, resp);

    }

    /**Изменение оповещений у пользователя
     * получаем editType в зависимости от значения
     * добавляем, удаляем или изменяем запись оповещения*/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Notifyer notifyer = new Notifyer();

        if (req.getParameter("editType") != null) {
            if ("add".equals(req.getParameter("editType"))) {

                try {
                    notifyer.setNotType(req.getParameter("notType"));
                    notifyer.setUser(userService.getUserById(Integer.parseInt(req.getParameter("userID"))));
                    notifyService.create(notifyer);
                } catch (UserServiceException e) {
                    logger.error(e);
                    ErrorForwarder.forwardToErrorPage(req,resp,
                            "Ошибка при получении доступа к таблице пользователей");
                } catch (UserNotFoundException e) {
                    logger.error(e);
                    ErrorForwarder.forwardToErrorPage(req,resp,
                            "Не найден пользователь");
                } catch (NotifyServiceException e) {
                    logger.error(e);
                    ErrorForwarder.forwardToErrorPage(req,resp,
                            "Ошибка при получении списка оповещений");
                }

            } else if ("del".equals(req.getParameter("editType"))) {
                try {
                    notifyer = notifyService.getNotifyById(Integer.parseInt(req.getParameter("notId")));
                    notifyService.delete(Integer.parseInt(req.getParameter("notId")));
                } catch (NotifyServiceException e) {
                    logger.error(e);
                    ErrorForwarder.forwardToErrorPage(req,resp,
                            "Ошибка при получении списка оповещений");
                }

            } else if ("edit".equals(req.getParameter("editType"))) {
                try {
                    notifyer = notifyService.getNotifyById(Integer.parseInt(req.getParameter("notId")));
                    notifyer.setNotType(req.getParameter("notType"));
                    notifyService.update(notifyer);
                } catch (NotifyServiceException e) {
                    logger.error(e);
                    ErrorForwarder.forwardToErrorPage(req,resp,
                            "Ошибка при получении списка оповещений");
                }
            }
        }

        goToPage(notifyer.getUser().getUserID(), req, resp);

    }

    public void goToPage(int userId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            List<Notifyer> notifyerList = notifyService.getAllByUser(userId);
            req.setAttribute("notifyers", notifyerList);
            req.setAttribute("userID", userId);
            req.getRequestDispatcher("/admin/notifyerlist.jsp").forward(req, resp);
        } catch (NotifyServiceException e) {
            logger.error(e);
            ErrorForwarder.forwardToErrorPage(req,resp,
                    "Ошибка при получении списка оповещений");
        }

    }
}

