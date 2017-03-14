package ru.innopolis.smoldyrev.service.interfaces;

import ru.innopolis.smoldyrev.common.exceptions.NotifyServiceException;
import ru.innopolis.smoldyrev.models.pojo.Notifyer;

import java.util.List;

/**
 * Created by smoldyrev on 09.03.17.
 */
public interface INotifyService {

    void create (Notifyer notifyer) throws NotifyServiceException ;


    void update (Notifyer notifyer) throws NotifyServiceException;

    void delete (int id) throws NotifyServiceException;

    Notifyer getNotifyById(int id) throws NotifyServiceException;

    List<Notifyer> getAllByNotType(String notyType) throws NotifyServiceException;

    List<Notifyer> getAllByUser(int userId) throws NotifyServiceException;

}
