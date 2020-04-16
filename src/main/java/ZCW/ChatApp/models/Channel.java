package ZCW.ChatApp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    @Size(min=3, max=15)
    private String channelName;
    private Boolean isPrivate = true;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "users_channels",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "channel")
    private List<Message> messages;

    public Channel (){}

    public Channel(String channelName, HashSet<User> users, Boolean isPrivate) {
        this.channelName = channelName;
        this.users = users;
        this.isPrivate = isPrivate;
        this.messages = new ArrayList<>();
    }

    public Channel(Long id, String channelName, HashSet<User> users, Boolean isPrivate) {
        this.id = id;
        this.channelName = channelName;
        this.users = users;
        this.isPrivate = isPrivate;
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public ArrayList<Message> getMessages() {
        return new ArrayList<>(messages);
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}






