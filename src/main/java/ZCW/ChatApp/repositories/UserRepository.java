package ZCW.ChatApp.repositories;

import ZCW.ChatApp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


}
