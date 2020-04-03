package ZCW.ChatApp.services;

import ZCW.ChatApp.models.Message;
import ZCW.ChatApp.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

//@Service
public class MessageService {

//    private MessageRepository messageRepository;
//
//    @Autowired
//    public MessageService(MessageRepository messageRepository) {
//        this.messageRepository = messageRepository;
//    }
//
//    public void save(Message message) {
//        messageRepository.save(message);
//    }
//
//    // POST
//    //=============================================================================
//
//    public Message create(Message message) {
//        message.setTimestamp(new Date());
//        return messageRepository.save(message);
//    }
//
//    // GET
//    //=============================================================================
//
//    public Optional<Message> findById(Long id) {
//        return messageRepository.findById(id);
//    }
//
//<<<<<<< HEAD
//    public Iterable<Message> findAll() {
//=======
//    public List<Message> findAll() {
//>>>>>>> fc8584aa9855487f12d12d4c21aa458ed229257b
//        return messageRepository.findAll();
//    }
//
//    public List<Message> findBySender(String sender, Pageable pageable) {
//        return messageRepository.findMessageBySender(sender, pageable);
//    }
//<<<<<<< HEAD
//
//    public Message findByTimeStamp(Long id) {
//        return messageRepository.findMessageByTimestamp(id);
//    }
//=======
//
////    public Message findByTimeStamp(Long id) {
////        return messageRepository.findMessageByTimestamp(id);
////    }
//>>>>>>> fc8584aa9855487f12d12d4c21aa458ed229257b
//
//    // DELETE
//    //=============================================================================
//
//    public Boolean delete(Long id) {
//        messageRepository.deleteById(id);
//        return true;
//    }
//
//    public Boolean deleteAll() {
//        messageRepository.deleteAll();
//        return true;
//    }
//<<<<<<< HEAD
//}
//
//
//=======
//
}

