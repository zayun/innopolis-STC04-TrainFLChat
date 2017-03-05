package ru.innopolis.smoldyrev.models.pojo;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Created by smoldyrev on 16.02.17.
 * Класс сущности, хранимой в таблице r_Messages
 * реализованы геттеры и сеттеры всех полей
 */

public class Message {

    private int id;
    private LocalDateTime date;
    private User fromUser;
    private User toUser;
    private String bodyText;
    private boolean viewed;
    private int chatRoom;

    /**Дефолтный конструктор
     * требуется для JAXB
     * */
    public Message() {
    }

    public Message(int id, LocalDateTime date, User fromUser, User toUser,
                   String bodyText, boolean viewed, int chatRoom) {
        this.id = id;
        this.date = date;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.bodyText = bodyText;
        this.viewed = viewed;
        this.chatRoom = chatRoom;
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

    public String getStrDate() {
       return date.format(DateTimeFormatter.ofPattern("HH-mm-ss"));//.toLocalTime().toString();
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
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
}
