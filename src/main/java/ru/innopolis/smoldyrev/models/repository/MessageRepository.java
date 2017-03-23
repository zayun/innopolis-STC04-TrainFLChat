package ru.innopolis.smoldyrev.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.smoldyrev.models.entity.MessageEntity;

import java.util.List;

/**
 * Created by smoldyrev on 21.03.17.
 */

@Repository
@Transactional
public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {
    List<MessageEntity> findByChatRoomOrderByDateDesc(@Param("chatroom") int chatroom);
}
