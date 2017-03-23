package ru.innopolis.smoldyrev.models.pojo;

/**
 * Created by smoldyrev on 27.02.17.
 * Класс сущности, хранимой в таблице r_Notifyer
 * реализованы геттеры и сеттеры всех полей
 */
public class Notifyer {

    private int id;
    private User user;
    private String notType;
    private int version;

    public Notifyer(int id, User user, String notType) {
        this.id = id;
        this.user = user;
        this.notType = notType;
    }

    public Notifyer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNotType() {
        return notType;
    }

    public void setNotType(String notType) {
        this.notType = notType;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
