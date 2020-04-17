package ZCW.ChatApp.repositories;

import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.DAOUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDaoRepository extends JpaRepository<DAOUser, Long> {

        Optional<DAOUser> findByUserName(String userName);

        List<DAOUser> findAllByChannels(Channel channel);

//        List<Message> findAllById()

}
