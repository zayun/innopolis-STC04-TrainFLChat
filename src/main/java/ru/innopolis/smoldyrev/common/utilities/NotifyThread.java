package ru.innopolis.smoldyrev.common.utilities;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.innopolis.smoldyrev.common.exceptions.NotifyServiceException;
import ru.innopolis.smoldyrev.models.pojo.Notifyer;
import ru.innopolis.smoldyrev.service.NotifyService;

import java.sql.Timestamp;
import java.util.List;

/**
 * Поток отправки почты
 */
public class NotifyThread implements Runnable {

    private static Logger logger = Logger.getLogger(NotifyThread.class);

    @Autowired
    private NotifyService notifyService;

    private String notifyType;

    public NotifyThread(String notifyType) {
        this.notifyType = notifyType;
    }

    @Override
    public void run() {
        try {
            List<Notifyer> notifyers = notifyService.getAllByNotType(notifyType);

            for (Notifyer notifyer :
                    notifyers) {
                logger.debug("mail send to " + notifyer.getUser().getPerson().getEmail());
                MailMaker.sendEmail(notifyer.getUser().getPerson().getEmail(),
                        "admin " + notifyer.getUser().getLogin() + "is logged in",
                        "admin with login " + notifyer.getUser().getLogin() +
                                " is logged in programm in " + new Timestamp(System.currentTimeMillis()));
            }

//            thread.start(); //пока не надо, не мучаем мэйл
        } catch (NotifyServiceException e) {
            logger.error(e);
        }
    }
}
