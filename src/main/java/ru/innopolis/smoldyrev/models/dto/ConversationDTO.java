package ru.innopolis.smoldyrev.models.dto;

import ru.innopolis.smoldyrev.models.pojo.Conversation;
import ru.innopolis.smoldyrev.models.pojo.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by smoldyrev on 18.03.17.
 */
@Entity
@Table(name = "r_conversation", schema = "main", catalog = "LFLChat")
public class ConversationDTO {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "chatroom")
    private int chatrooom;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;
    @Column(name = "grade_converse")
    private int gradeConverse;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "r_converse_members", schema = "main", joinColumns = {
            @JoinColumn(name = "converse", nullable = false)
    }, inverseJoinColumns = {@JoinColumn(name = "user_id", nullable = false)})
    private Set<UserDTO> users = new HashSet<>();

    public ConversationDTO() {
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

    public int getGradeConverse() {
        return gradeConverse;
    }

    public void setGradeConverse(int gradeConverse) {
        this.gradeConverse = gradeConverse;
    }

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

    public Conversation transformToConversation() {

        Conversation conversation =
                new Conversation(id,
                chatrooom,
                startTime,
                endTime,
                gradeConverse);
        Set<User> users = new HashSet<>();

        for (UserDTO u:
             this.users) {
            users.add(u.transformToUser());
        }

        conversation.setUsers(users);
        return conversation;
    }
}
