package ZCW.ChatApp.repositories;
import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
    
    Optional<Channel> findChannelByChannelName(String channelName);

}
