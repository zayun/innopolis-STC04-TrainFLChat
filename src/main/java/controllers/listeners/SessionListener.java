package controllers.listeners;

import common.urilities.MailMaker;
import models.dao.NotifyDAO;
import models.pojo.Notifyer;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by smoldyrev on 24.02.17.
 */
//начало работы сессии
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {

    private static Logger logger = Logger.getLogger(SessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        logger.trace("session:" + se.getSession().getId());

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {

        if ("sessionId".equals(event.getName()) && event.getValue() != null) {
            logger.trace("User with id: " + event.getValue() + "is authorized. Start session.");
        }
        if ("sessionUserType".equals(event.getName()) && "admin".equals(event.getValue())) {
            logger.trace("admin is logged");

            NotifyDAO notifyDAO = new NotifyDAO();
            List<Notifyer> notifyers = notifyDAO.getAllByNotType("admLog");

            logger.debug(event.getSession().getAttribute("sessionId"));

            /*запустим в отдельном потоке, чтобы не повесить систему при ожидании МэйлСервера*/
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {

                    for (Notifyer notifyer :
                            notifyers) {
                        logger.debug("mail send to "+notifyer.getUser().getPerson().getEmail());
                        MailMaker.sendEmail(notifyer.getUser().getPerson().getEmail(),
                                "admin " + notifyer.getUser().getLogin() + "is logged in",
                                "admin with login " + notifyer.getUser().getLogin() +
                        " is logged in programm in "+new Timestamp(System.currentTimeMillis()));
                    }
                }
            });

            thread.start();
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {

    }
}
