package ru.innopolis.smoldyrev.models.pojo;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by smoldyrev on 16.02.17.
 * Класс сущности, хранимой в таблице r_Conversation
 * реализованы геттеры и сеттеры всех полей
 */
public class Conversation {

    private int id;
    private int chatrooom;
    private Timestamp startTime;
    private Timestamp endTime;
    private int gradeConverse;

    Set<User> users;

    public Conversation(int id, int chatrooom, Timestamp startTime, Timestamp endTime, int gradeConverse) {
        this.id = id;
        this.chatrooom = chatrooom;
        this.startTime = startTime;
        this.endTime = endTime;
        this.gradeConverse = gradeConverse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChatrooom() {
        return chatrooom;
    }

    public void setChatrooom(int chatrooom) {
        this.chatrooom = chatrooom;
    }

    public int getGradeConverse() {
        return gradeConverse;
    }

    public void setGradeConverse(int gradeConverse) {
        this.gradeConverse = gradeConverse;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
