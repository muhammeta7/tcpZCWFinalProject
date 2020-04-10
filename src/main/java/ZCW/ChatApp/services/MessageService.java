package ZCW.ChatApp.services;

import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.Message;
import ZCW.ChatApp.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class MessageService {

    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message save(Message message) { return messageRepository.save(message); }

    // POST
    //=============================================================================

    // TODO Fix this to set channel ids, and sender ids
    public Message create(Message message) {
        message.setTimestamp(new Date());
        return messageRepository.save(message);
    }

    // TODO Fix this
//    public Message postInChannel(Message message, Long channelId){
//        message.setTimestamp(new Date());
//        Channel channel = channelService.getChannel(channelId);
//        channel.getMessages().add(message);
//        channelService.saveChannel(channel);
//        return messageRepository.save(message);
//    }

    // GET
    //=============================================================================

    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }

    public Message getMessage(Long id){
        return messageRepository.getOne(id);
    }

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public List<Message> findByChannel(Long channelId){
        return messageRepository.findByChannelId(channelId);
    }

    // TODO Test
    public List<Message> findMessagesByUserId(Long userId){
        return messageRepository.findMessagesBySender_Id(userId);
    }


    // UPDATE
    //=============================================================================


    // DELETE
    //=============================================================================

    public Boolean delete(Long id) {
        messageRepository.deleteById(id);
        return true;
    }

    public Boolean deleteAll() {
        messageRepository.deleteAll();
        return true;
    }


}
