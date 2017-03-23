package ru.innopolis.smoldyrev.models.entity;



import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by smoldyrev on 18.03.17.
 */
@Entity
@Table(name = "r_conversation", schema = "main", catalog = "LFLChat")
public class ConversationEntity {


    private int id;
    private int chatroom;
    private Timestamp startTime;
    private Timestamp endTime;
    private int gradeConverse;
    private Set<UserEntity> users = new HashSet<>();
    private int version;


    public ConversationEntity() {
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "chatroom")
    public int getChatroom() {
        return chatroom;
    }

    public void setChatroom(int chatroom) {
        this.chatroom = chatroom;
    }

    @Column(name = "start_time")
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Column(name = "end_time")
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Column(name = "grade_converse")
    public int getGradeConverse() {
        return gradeConverse;
    }

    public void setGradeConverse(int gradeConverse) {
        this.gradeConverse = gradeConverse;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "r_converse_members", schema = "main", joinColumns = {
            @JoinColumn(name = "converse", nullable = false)
    }, inverseJoinColumns = {@JoinColumn(name = "user_id", nullable = false)})
    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    public void addUser(UserEntity user) {
        this.users.add(user);
    }

    public void removeUser(UserEntity user) {
        this.users.remove(user);
    }

    @Version
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
