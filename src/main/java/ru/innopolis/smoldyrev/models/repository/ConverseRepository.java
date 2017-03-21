package ru.innopolis.smoldyrev.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.smoldyrev.models.dto.ConversationDTO;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by smoldyrev on 21.03.17.
 */
@Repository
@Transactional
public interface ConverseRepository extends JpaRepository<ConversationDTO, Integer> {

    ConversationDTO findByChatroomAndStartTimeBeforeAndEndTimeAfter(
            @Param("chatroom") int chatroom,
            @Param("startTime") Timestamp startTime,
            @Param("endTime") Timestamp endTime);

    List<ConversationDTO> findByEndTimeAfter(@Param("endTime") Timestamp datetime);

    ConversationDTO findByChatroom(@Param("chatroom") int chatroom);

}
