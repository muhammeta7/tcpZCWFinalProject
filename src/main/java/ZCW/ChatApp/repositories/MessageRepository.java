package ZCW.ChatApp.repositories;

import ZCW.ChatApp.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
