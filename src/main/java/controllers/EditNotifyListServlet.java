package controllers;

import models.pojo.Notifyer;
import org.apache.log4j.Logger;
import service.NotifyService;
import service.UserService;

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        goToPage(Integer.parseInt(req.getParameter("userID")), req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Notifyer notifyer = new Notifyer();

        if (req.getParameter("editType") != null) {
            if ("add".equals(req.getParameter("editType"))) {
                notifyer.setNotType(req.getParameter("notType"));
                notifyer.setUser(UserService.getUserById(Integer.parseInt(req.getParameter("userID"))));
                NotifyService.create(notifyer);
            } else if ("del".equals(req.getParameter("editType"))) {
                notifyer = NotifyService.getNotifyById(Integer.parseInt(req.getParameter("notId")));
                NotifyService.delete(Integer.parseInt(req.getParameter("notId")));
            } else if ("edit".equals(req.getParameter("editType"))) {
                notifyer = NotifyService.getNotifyById(Integer.parseInt(req.getParameter("notId")));
                notifyer.setNotType(req.getParameter("notType"));
                NotifyService.update(notifyer);
            }
        }


        goToPage(notifyer.getUser().getUserID(), req, resp);

    }

    public void goToPage(int userId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Notifyer> notifyerList = NotifyService.getAllByUser(userId);
        req.setAttribute("notifyers", notifyerList);
        req.setAttribute("userID", userId);
        req.getRequestDispatcher("/admin/notifyerlist.jsp").forward(req, resp);
    }
}

