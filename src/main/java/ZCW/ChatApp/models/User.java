package ZCW.ChatApp.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "First name can not be empty!")
    @Size(min=3, max=15)
    private String firstName;
    @NotEmpty(message = "Last name can not be empty!")
    @Size(min=3, max=15)
    private String lastName;
    @NotEmpty(message = "Username can not be empty!")
    @Size(min=3, max=15)
    private String userName;
    @NotEmpty(message = "Password can not be empty!")
    @Size(min=5, max=15)
    private String password;
    private Boolean connected = false;
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sender")
    private List<Message> messages;
    @JsonIgnore
    @ManyToMany(mappedBy = "users")
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

    public User(Long id, String firstName, String lastName, String userName, String password, Boolean isConnected) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(password, user.password) &&
                Objects.equals(connected, user.connected);
    }

}
