package ZCW.ChatApp.services;

import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.Message;
import ZCW.ChatApp.models.User;
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

    // TODO Test
    public Message create(Message message, Long userId, Long channelId) {
        message.setTimestamp(new Date());
        User user = userService.getUser(userId);
        Channel channel = channelService.getChannel(channelId);
        message.setSender(user);
        message.setChannel(channel);
        List<Message> messages = user.getMessages();
        List<Message> messages1 = channel.getMessages();
        messages1.add(message);
        messages.add(message);
        channelService.saveChannel(channel);
        userService.save(user);
        return save(message);
    }

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
        if (findById(id).isPresent()) {
            messageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Boolean deleteAll() {
        if (findAll().isEmpty()){
            return false;
        }
        messageRepository.deleteAll();
        return true;
    }


}
