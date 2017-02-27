package models.pojo;

/**
 * Created by smoldyrev on 27.02.17.
 */
public class Notifyer {

    private int id;
    private User user;
    private String notType;

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
}
