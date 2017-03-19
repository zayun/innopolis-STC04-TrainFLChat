package ru.innopolis.smoldyrev.models.dto;


import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by smoldyrev on 18.03.17.
 */
@Entity
@Table(name = "r_conversation", schema = "main", catalog = "LFLChat")
public class ConversationDTO {


    private int id;
    private int chatrooom;
    private Timestamp startTime;
    private Timestamp endTime;
    private int gradeConverse;
    private Set<UserDTO> users = new HashSet<>();
    private Integer version;

    public ConversationDTO() {
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
    public int getChatrooom() {
        return chatrooom;
    }

    public void setChatrooom(int chatrooom) {
        this.chatrooom = chatrooom;
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
    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    public void addUser(UserDTO user) {
        this.users.add(user);
    }

    public void removeUser(UserDTO user) {
        this.users.remove(user);
    }

    @Version
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
