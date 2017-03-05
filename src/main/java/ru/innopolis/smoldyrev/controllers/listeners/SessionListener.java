package ru.innopolis.smoldyrev.controllers.listeners;

import org.apache.log4j.Logger;
import ru.innopolis.smoldyrev.service.NotifyService;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by smoldyrev on 24.02.17.
 * Слушатель сессии
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
        }

        logger.debug(event.getSession().getAttribute("sessionId"));
    }


    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {

    }
}
