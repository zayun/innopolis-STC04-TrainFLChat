package ru.innopolis.smoldyrev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.smoldyrev.common.exceptions.NotifyDaoException;
import ru.innopolis.smoldyrev.common.exceptions.NotifyServiceException;
import ru.innopolis.smoldyrev.models.dao.NotifyDAO;
import ru.innopolis.smoldyrev.models.pojo.Notifyer;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by smoldyrev on 27.02.17.
 * Сервис работы с данными обоповещениях пользователей
 */
@Service
public class NotifyService {

    private static Logger logger = Logger.getLogger(NotifyService.class);

    @Autowired
    private NotifyDAO notifyDAO;

    public void create (Notifyer notifyer) throws NotifyServiceException {
        try {
            notifyDAO.create(notifyer);
        } catch (NotifyDaoException e) {
            logger.error(e);
            throw new NotifyServiceException();
        }
    }


    public void update (Notifyer notifyer) throws NotifyServiceException {
        try {
            notifyDAO.update(notifyer);
        } catch (NotifyDaoException e) {
            logger.error(e);
            throw new NotifyServiceException();
        }
    }

    public void delete (int id) throws NotifyServiceException {
        try {
            notifyDAO.delete(id);
        } catch (NotifyDaoException e) {
            logger.error(e);
            throw new NotifyServiceException();
        }
    }

    public Notifyer getNotifyById(int id) throws NotifyServiceException {
        try {
            return notifyDAO.getEntityById(id);
        } catch (NotifyDaoException e) {
            logger.error(e);
            throw new NotifyServiceException();
        }

    }

    public List<Notifyer> getAllByNotType(String notyType) throws NotifyServiceException {
        try {
            return notifyDAO.getAllByNotType(notyType);
        } catch (NotifyDaoException e) {
            logger.error(e);
            throw new NotifyServiceException();
        }
    }

    public List<Notifyer> getAllByUser(int userId) throws NotifyServiceException {
        try {
            return notifyDAO.getAllByUser(userId);
        } catch (NotifyDaoException e) {
            logger.error(e);
            throw new NotifyServiceException();
        }
    }

}
