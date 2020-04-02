package ZCW.ChatApp.repositories;

import ZCW.ChatApp.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sun.jvm.hotspot.debugger.Page;

import java.awt.print.Pageable;
import java.util.List;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

   List<Message> findMessageBySender(String sender, Pageable pageable);

   Message findMessageByTimestamp(Long id);
  
}
