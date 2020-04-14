package ZCW.ChatApp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User sender;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "channel_id")
    private Channel channel;
    @JsonBackReference
    @NotEmpty(message = "Message Content can not be empty!")
    @Size(min=3, max=100)
    private String content;
    private Date timestamp;

    public Message (){}

    public Message(User sender, String msgContent, Date timestamp, Channel channel) {
        this.sender = sender;
        this.content = msgContent;
        this.timestamp = timestamp;
        this.channel = channel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return this.sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String msgContent) {
        this.content = msgContent;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
