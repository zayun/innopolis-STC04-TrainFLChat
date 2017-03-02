package common.utilities;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by smoldyrev on 27.02.17.
 * Класс для отправки почты
 */
public class MailMaker {

    private static Logger logger = Logger.getLogger(MailMaker.class);

    private static final String USER_NAME = "e.smoldyrev.stc@innopolis.ru";

    private static final String PASSWORD = "";

    /**Отправка почты
     * @param sendTo адрес получателя
     * @param subject тема письма
     * @param text текст письма
    * */
    public static void sendEmail(String sendTo, String subject, String text) {

        logger.debug("mail send: " + sendTo);
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "mail.innopolis.ru");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USER_NAME, PASSWORD);
                    }
                });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress("e.smoldyrev.stc@innopolis.ru"));

            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(sendTo));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);
            logger.trace("Mail send to "+sendTo);

        } catch (MessagingException e) {
            logger.error(e);
        }

    }
}
