package controllers;

import models.dao.NotifyDAO;
import models.dao.UserDAO;
import models.pojo.Notifyer;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by smoldyrev on 27.02.17.
 */
public class EditNotifyListServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(EditNotifyListServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        goToPage(Integer.parseInt(req.getParameter("userID")), req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NotifyDAO notifyDAO = new NotifyDAO();
        UserDAO userDAO = new UserDAO();
        Notifyer notifyer = new Notifyer();
        if (req.getParameter("editType") != null) {
            if ("add".equals(req.getParameter("editType"))) {
                notifyer.setNotType(req.getParameter("notType"));
                notifyer.setUser(userDAO.getEntityById(Integer.parseInt(req.getParameter("userID"))));
                notifyDAO.create(notifyer);
            } else if ("del".equals(req.getParameter("editType"))) {
                notifyer = notifyDAO.getEntityById(Integer.parseInt(req.getParameter("notId")));
                notifyDAO.delete(notifyer.getId());
            } else if ("edit".equals(req.getParameter("editType"))) {
                notifyer = notifyDAO.getEntityById(Integer.parseInt(req.getParameter("notId")));
                notifyer.setNotType(req.getParameter("notType"));
                notifyDAO.update(notifyer);
            }
        }


        goToPage(notifyer.getUser().getUserID(), req, resp);

    }

    public void goToPage(int userId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        NotifyDAO notifyDAO = new NotifyDAO();

        List<Notifyer> notifyerList = notifyDAO.getAllByUser(userId);
        req.setAttribute("notifyers", notifyerList);
        req.setAttribute("userID", userId);
        req.getRequestDispatcher("/admin/notifyerlist.jsp").forward(req, resp);
    }
}

