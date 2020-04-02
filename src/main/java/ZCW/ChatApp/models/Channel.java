package ZCW.ChatApp.models;

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

    @ManyToMany(mappedBy = "channels")
    private Set<User> users;
    private Boolean isPrivate;

    @OneToMany
    private List<Message> messages;

    public Channel (){};

    public Channel(String channelName, Set<User> users, Boolean isPrivate) {
        this.channelName = channelName;
        this.users = new HashSet<>();
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

    public Boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

}






