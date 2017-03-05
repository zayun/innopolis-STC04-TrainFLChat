package ru.innopolis.smoldyrev.models.pojo;


/**
 * Created by smoldyrev on 16.02.17.
 * Класс сущности, хранимой в таблице d_Users
 * реализованы геттеры и сеттеры всех полей
 */
public class User {

    private Integer userID;
    private String userType;
    private String login;
    private String password;
    private Person person;
    private boolean blocked;

    /**Дефолтный конструктор
     * требуется для JAXB
     *
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

    public String getPassword() {
        return password;
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
}
