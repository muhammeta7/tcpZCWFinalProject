package ZCW.ChatApp.repositories;

import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

        Optional<User> findByUserName(String userName);

        List<User> findAllByChannels(Channel channel);

//        List<Message> findAllById()

}
