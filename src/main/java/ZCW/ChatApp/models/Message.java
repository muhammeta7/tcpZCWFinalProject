package ZCW.ChatApp.models;

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
    private DAOUser sender;
    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;
    @NotEmpty(message = "Message Content can not be empty!")
    @Size(min=3, max=250)
    private String content;
    private Date timestamp;

    public Message (){}

    public Message(DAOUser sender, String msgContent, Date timestamp, Channel channel) {
        this.sender = sender;
        this.content = msgContent;
        this.timestamp = timestamp;
        this.channel = channel;
    }

    public Message(Long id, DAOUser sender, String msgContent, Date timestamp, Channel channel){
        this.id = id;
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

    public DAOUser getSender() {
        return this.sender;
    }

    public void setSender(DAOUser sender) {
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
