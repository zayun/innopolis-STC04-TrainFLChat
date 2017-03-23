package ru.innopolis.smoldyrev.models.entity;

import javax.persistence.*;

/**
 * Created by smoldyrev on 16.02.17.
 * Класс сущности, хранимой в таблице d_Users
 * реализованы геттеры и сеттеры всех полей
 * реализован UserDetails для SpringSecurity
 */
@Entity
@Table(name = "d_users", schema = "main", catalog = "LFLChat")
public class UserEntity {

    private Integer userID;
    private String userType;
    private String login;
    private String password;
    private boolean blocked;
    private PersonEntity person;
    private int version;

    public UserEntity() {
    }

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    @Column(name = "usertype")
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "pwd")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "blocked")
    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    public PersonEntity getPerson() {
        return person;
    }

    public void setPerson(PersonEntity person) {
        this.person = person;
    }

    @Version
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
