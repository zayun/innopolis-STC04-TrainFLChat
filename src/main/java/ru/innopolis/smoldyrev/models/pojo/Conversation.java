package ru.innopolis.smoldyrev.models.pojo;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by smoldyrev on 16.02.17.
 * Класс сущности, хранимой в таблице r_Conversation
 * реализованы геттеры и сеттеры всех полей
 */
public class Conversation {
    private int id;
    private int chatrooom;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int gradeConverse;

    public Conversation(int id, int chatrooom, LocalDateTime startTime, LocalDateTime endTime, int gradeConverse) {
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
