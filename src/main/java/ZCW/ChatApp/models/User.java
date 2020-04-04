package ZCW.ChatApp.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private Boolean connected = false;
    @JsonIgnore
    @OneToMany
    @Column(name="message_id")
    private List<Message> messages;
    @JsonIgnore
    @ManyToMany
    @Column(name="channel_id")
    private Set<Channel> channels;

    public User (){};

    public User(String firstName, String lastName, String userName, String password, Boolean isConnected) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.connected = isConnected;
        this.messages = new ArrayList<>();
        this.channels = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Set<Channel> getChannels() {
        return channels;
    }

    public void setChannels(HashSet<Channel> channels) {
        this.channels = channels;
    }
}
