package ru.innopolis.smoldyrev.models.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by smoldyrev on 18.03.17.
 */
@Entity
@Table(name = "r_messages", schema = "main", catalog = "LFLChat")
public class MessageEntity {


    private int id;
    private Timestamp date;
    private UserEntity fromUser;
    private UserEntity toUser;
    private String bodyText;
    private boolean viewed;
    private int chatRoom;
    private Integer version;

    public MessageEntity() {
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "date_time")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @ManyToOne
    @JoinColumn(name = "from_user")
    public UserEntity getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserEntity fromUser) {
        this.fromUser = fromUser;
    }

    @ManyToOne
    @JoinColumn(name = "to_user")
    public UserEntity getToUser() {
        return toUser;
    }

    public void setToUser(UserEntity toUser) {
        this.toUser = toUser;
    }

    @Column(name = "body_text")
    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    @Column(name = "is_read")
    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    @Column(name = "chatroom")
    public int getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(int chatRoom) {
        this.chatRoom = chatRoom;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
