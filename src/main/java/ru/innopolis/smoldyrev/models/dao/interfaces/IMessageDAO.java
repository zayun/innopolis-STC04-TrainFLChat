package ru.innopolis.smoldyrev.models.dao.interfaces;

import ru.innopolis.smoldyrev.common.exceptions.MessageDaoException;
import ru.innopolis.smoldyrev.models.entity.MessageEntity;
import ru.innopolis.smoldyrev.models.pojo.Message;

import java.util.List;

/**
 * Created by smoldyrev on 09.03.17.
 */
public interface IMessageDAO {

    List<MessageEntity> getAll() throws MessageDaoException;

    List<MessageEntity> getAllInRoom(int chatRoom) throws MessageDaoException;

    boolean delete(Integer id) throws MessageDaoException;

    boolean create(Message entity) throws MessageDaoException;


}
