package ru.innopolis.smoldyrev.models.dto;

import ru.innopolis.smoldyrev.models.pojo.User;

import javax.persistence.*;

/**
 * Created by smoldyrev on 16.02.17.
 * Класс сущности, хранимой в таблице d_Users
 * реализованы геттеры и сеттеры всех полей
 * реализован UserDetails для SpringSecurity
 */
@Entity
@Table(name = "d_users", schema = "main", catalog = "LFLChat")
public class UserDTO {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Integer userID;
    @Column(name = "usertype")
    private String userType;
    @Column(name = "login")
    private String login;
    @Column(name = "pwd")
    private String password;

    @Column(name = "blocked")
    private boolean blocked;

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private PersonDTO person;


    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }

    public UserDTO() {
    }


    public User transformToUser() {
        return new User(
                userID,
                userType,
                login,
                password,
                person.transformToPerson(),
                blocked);
    }
}
