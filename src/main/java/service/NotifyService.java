package service;

import exceptions.NotifyDaoException;
import exceptions.NotifyServiceException;
import models.dao.NotifyDAO;
import models.dao.PersonDAO;
import models.pojo.Notifyer;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by smoldyrev on 27.02.17.
 * Сервис работы с данными обоповещениях пользователей
 */
public class NotifyService {

    private static Logger logger = Logger.getLogger(NotifyService.class);

    private static NotifyDAO notifyDAO = new NotifyDAO();

    public static void create (Notifyer notifyer) throws NotifyServiceException {
        try {
            notifyDAO.create(notifyer);
        } catch (NotifyDaoException e) {
            logger.error(e);
            throw new NotifyServiceException();
        }
    }


    public static void update (Notifyer notifyer) throws NotifyServiceException {
        try {
            notifyDAO.update(notifyer);
        } catch (NotifyDaoException e) {
            logger.error(e);
            throw new NotifyServiceException();
        }
    }

    public static void delete (int id) throws NotifyServiceException {
        try {
            notifyDAO.delete(id);
        } catch (NotifyDaoException e) {
            logger.error(e);
            throw new NotifyServiceException();
        }
    }

    public static Notifyer getNotifyById(int id) throws NotifyServiceException {
        try {
            return notifyDAO.getEntityById(id);
        } catch (NotifyDaoException e) {
            logger.error(e);
            throw new NotifyServiceException();
        }

    }

    public static List<Notifyer> getAllByNotType(String notyType) throws NotifyServiceException {
        try {
            return notifyDAO.getAllByNotType(notyType);
        } catch (NotifyDaoException e) {
            logger.error(e);
            throw new NotifyServiceException();
        }
    }

    public static List<Notifyer> getAllByUser(int userId) throws NotifyServiceException {
        try {
            return notifyDAO.getAllByUser(userId);
        } catch (NotifyDaoException e) {
            logger.error(e);
            throw new NotifyServiceException();
        }
    }

}
