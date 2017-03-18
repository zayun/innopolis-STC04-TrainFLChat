package ru.innopolis.smoldyrev.models.dto;

import ru.innopolis.smoldyrev.models.pojo.Message;
import ru.innopolis.smoldyrev.models.pojo.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by smoldyrev on 18.03.17.
 */
@Entity
@Table(name = "r_messages", schema = "main", catalog = "LFLChat")
public class MessageDTO {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "date_time")
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "from_user")
    private UserDTO fromUser;
    @ManyToOne
    @JoinColumn(name = "to_user")
    private UserDTO toUser;
    @Column(name = "body_text")
    private String bodyText;
    @Column(name = "is_read")
    private boolean viewed;
    @Column(name = "chatroom")
    private int chatRoom;

    public MessageDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public UserDTO getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserDTO fromUser) {
        this.fromUser = fromUser;
    }

    public UserDTO getToUser() {
        return toUser;
    }

    public void setToUser(UserDTO toUser) {
        this.toUser = toUser;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public int getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(int chatRoom) {
        this.chatRoom = chatRoom;
    }

    public Message transformToMessage() {
        return new Message(id,date,fromUser.transformToUser(),toUser.transformToUser(),bodyText,viewed,chatRoom);
    }
}
