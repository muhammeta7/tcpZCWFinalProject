package ZCW.ChatApp.repositories;

import ZCW.ChatApp.models.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {



}
