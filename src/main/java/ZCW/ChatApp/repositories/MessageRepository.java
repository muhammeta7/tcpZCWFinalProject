package ZCW.ChatApp.repositories;

import ZCW.ChatApp.models.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Message findMessageByTimestamp(Long id);
    List<Message> findMessageBySender(String sender, Pageable pageable);


}
