package ZCW.ChatApp.services;


import ZCW.ChatApp.models.Message;
import ZCW.ChatApp.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    public Message create(Message message){
        message.setTimestamp(new Date());
        return messageRepository.save(message);
    }
    public Iterable<Message> findAll(){
        return messageRepository.findAll();
    }


    public List<Message> findBySender(String sender, Pageable pageable){
        return messageRepository.findMessageBySender(sender, pageable);
    }

    public Message findById(Long id){
        return messageRepository.getOne(id);
    }

    public Boolean delete(Long id){
        messageRepository.deleteById(id);
        return true;
    }

    public Boolean deleteAll(){
        messageRepository.deleteAll();
        return true;
    }

}