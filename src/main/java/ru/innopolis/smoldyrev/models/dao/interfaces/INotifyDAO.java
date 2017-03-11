package ru.innopolis.smoldyrev.models.dao.interfaces;

import ru.innopolis.smoldyrev.common.exceptions.NotifyDaoException;
import ru.innopolis.smoldyrev.models.pojo.Notifyer;

import java.util.List;

/**
 * Created by smoldyrev on 09.03.17.
 */
public interface INotifyDAO {

    List<Notifyer> getAll() throws NotifyDaoException;

    Notifyer update(Notifyer entity) throws NotifyDaoException;

    boolean delete(Integer id) throws NotifyDaoException;

    boolean create(Notifyer entity) throws NotifyDaoException;

    List<Notifyer> getAllByNotType(String notyType) throws NotifyDaoException;

    List<Notifyer> getAllByUser(int userId) throws NotifyDaoException;

    Notifyer getEntityById(Integer id) throws NotifyDaoException;

}
