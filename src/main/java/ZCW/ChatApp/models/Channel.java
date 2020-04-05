package ZCW.ChatApp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String channelName;
    private Boolean isPrivate;
    @JsonIgnoreProperties("channel")
    @ManyToMany
    private Set<User> users;
//    @OneToMany
//    private Set<Message> messages;

    public Channel (){}

    public Channel(String channelName, HashSet<User> users, Boolean isPrivate) {
        this.channelName = channelName;
        this.users = users;
        this.isPrivate = isPrivate;
        this.users = new HashSet<>();
       // this.messages = new HashSet<>();
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

//    public HashSet<Message> getMessages() {
//        return new HashSet<>(messages);
//    }
//
//    public void setMessages(HashSet<Message> messages) {
//        this.messages = messages;
//    }
}






