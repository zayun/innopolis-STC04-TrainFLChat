package ru.innopolis.smoldyrev.models.pojo;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.innopolis.smoldyrev.service.interfaces.GrantedAuthorityImpl;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by smoldyrev on 16.02.17.
 * Класс сущности, хранимой в таблице d_Users
 * реализованы геттеры и сеттеры всех полей
 * реализован UserDetails для SpringSecurity
 */
public class User implements UserDetails{

    private Integer userID;
    private String userType;
    private String login;
    private String password;
    private Person person;
    private boolean blocked;
    private int version;

    /**Конструктор
     * @param userID
     * @param userType
     * @param login
     * @param password
     * @param person
     * @param blocked*/
    public User(Integer userID, String userType, String login, String password, Person person, boolean blocked) {
        this.userID = userID;
        this.userType = userType;
        this.login = login;
        this.password = password;
        this.person = person;
        this.blocked = blocked;
    }

    public User() {
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
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

    public void setPassword(String passwd) {
        this.password = passwd;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthorityImpl> gr = new ArrayList<>();
        gr.add(new GrantedAuthorityImpl(userType));
        return gr;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !blocked;
    }
}
