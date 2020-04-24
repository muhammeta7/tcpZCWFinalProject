package ZCW.ChatApp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Channel can not be empty!")
    @Size(min=3, max=30)
    private String channelName;
    private Boolean isPrivate = true;
    private Boolean isDm;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "users_channels",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<DAOUser> users;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "channel")
    private List<Message> messages;

    public Channel (){}

    public Channel(String channelName, HashSet<DAOUser> users, Boolean isPrivate, Boolean isDm) {
        this.channelName = channelName;
        this.users = users;
        this.isPrivate = isPrivate;
        this.isDm = isDm;
        this.messages = new ArrayList<>();
    }

    public Channel(Long id, String channelName, HashSet<DAOUser> users, Boolean isPrivate, Boolean isDm) {
        this.id = id;
        this.channelName = channelName;
        this.users = users;
        this.isPrivate = isPrivate;
        this.isDm = isDm;
        this.messages = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Set<DAOUser> getUsers() {
        return users;
    }

    public void setUsers(Set<DAOUser> users) {
        this.users = users;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Boolean getIsDm() {
        return isDm;
    }

    public void setIsDm(Boolean isDm) {
        this.isDm = isDm;
    }

    public ArrayList<Message> getMessages() {
        return new ArrayList<>(messages);
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}






