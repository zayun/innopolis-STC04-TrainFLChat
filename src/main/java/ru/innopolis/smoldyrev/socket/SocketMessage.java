package ru.innopolis.smoldyrev.socket;


import java.sql.Timestamp;

/**
 * Created by smoldyrev on 22.03.17.
 */
public class SocketMessage {

    private int fromUser;
    private int toUser;
    private String bodyText;
    private int chatRoom;

    public SocketMessage() {
    }

    public SocketMessage(int fromUser, int toUser, String bodyText, int chatRoom) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.bodyText = bodyText;
        this.chatRoom = chatRoom;
    }

    public int getFromUser() {
        return fromUser;
    }

    public void setFromUser(int fromUser) {
        this.fromUser = fromUser;
    }

    public int getToUser() {
        return toUser;
    }

    public void setToUser(int toUser) {
        this.toUser = toUser;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public int getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(int chatRoom) {
        this.chatRoom = chatRoom;
    }
}
