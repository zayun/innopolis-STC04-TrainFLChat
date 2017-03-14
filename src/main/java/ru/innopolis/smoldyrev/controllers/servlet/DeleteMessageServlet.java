package ru.innopolis.smoldyrev.controllers.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.innopolis.smoldyrev.common.utilities.ErrorForwarder;
import ru.innopolis.smoldyrev.common.exceptions.MessageServiceException;
import org.apache.log4j.Logger;
import ru.innopolis.smoldyrev.service.MessageService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by smoldyrev on 26.02.17.
 * Сервлет удаления сообщения по id
 */
public class DeleteMessageServlet extends HttpServlet{

    private static Logger logger = Logger.getLogger(DeleteMessageServlet.class);

    @Autowired(required = true)
    private MessageService messageService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    /**Получаем msgid - id сообщения в БД
     * отправляем запрос на удаление сообщения по id*/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            messageService.deleteMessage(Integer.parseInt(req.getParameter("msgid")));
            req.getRequestDispatcher("/rooms/generalchat").forward(req, resp);
        } catch (MessageServiceException e) {
            logger.error(e);
            ErrorForwarder.forwardToErrorPage(req,resp,
                    "Ошибка удаления сообщения");
        }
    }
}
