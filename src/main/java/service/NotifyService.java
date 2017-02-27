package service;

import models.dao.NotifyDAO;
import models.pojo.Notifyer;

import java.util.List;

/**
 * Created by smoldyrev on 27.02.17.
 * Сервис работы с данными обоповещениях пользователей
 */
public class NotifyService {

    private static NotifyDAO notifyDAO = new NotifyDAO();

    public static void create (Notifyer notifyer) {
        notifyDAO.create(notifyer);
    }


    public static void update (Notifyer notifyer) {
        notifyDAO.update(notifyer);
    }

    public static void delete (int id) {
        notifyDAO.delete(id);
    }

    public static Notifyer getNotifyById(int id) {
        return notifyDAO.getEntityById(id);

    }

    public static List<Notifyer> getAllByNotType(String notyType) {
        return notifyDAO.getAllByNotType(notyType);
    }

    public static List<Notifyer> getAllByUser(int userId) {
        return notifyDAO.getAllByUser(userId);
    }

}
