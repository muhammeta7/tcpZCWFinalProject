package ZCW.ChatApp.repositories;

import ZCW.ChatApp.models.Message;
import ZCW.ChatApp.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

   List<Message> findMessageBySender(User sender, Pageable pageable);
   // TODO Add find message by Date

}
