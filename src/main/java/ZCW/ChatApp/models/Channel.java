package ZCW.ChatApp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String channelName;
    private Boolean isPrivate;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "users_channels",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "channel")
    private List<Message> messages;


    public Channel (){}

    public Channel(String channelName, HashSet<User> users, Boolean isPrivate) {
        this.channelName = channelName;
        this.users = users;
        this.isPrivate = isPrivate;
        //this.users = new HashSet<>();
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






