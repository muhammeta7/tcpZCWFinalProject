package ZCW.ChatApp.repositories;

import ZCW.ChatApp.models.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

}
