package ZCW.ChatApp.models;

import java.util.List;
import java.util.Set;

public class Channel {
    private long id;
    private String channelName;
    private Set<User> users;
    private boolean isPrivate;
    private List<Message> messages;

    public Channel (){};

    public Channel(long id, String channelName, Set<User> users, boolean isPrivate, List<Message> messages) {
        this.id = id;
        this.channelName = channelName;
        this.users = users;
        this.isPrivate = isPrivate;
        this.messages = messages;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public boolean isPrivate() {
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






