package ZCW.ChatApp.services;

import ZCW.ChatApp.models.User;
import ZCW.ChatApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // POST
    //=============================================================================
    public User create(User user) throws Exception{
        if(!userRepo.findByUserName(user.getUserName()).isPresent()){
            return userRepo.save(user);
        }
        throw new Exception("Username is taken. Try something else.");
    }

    // GET
    //=============================================================================
    public List<User> findAll(){
        return userRepo.findAll();
    }

    public Optional<User> findById(Long id){
        return userRepo.findById(id);
    }

    public Optional<User> findUserByUsername(String username){
        return userRepo.findByUserName(username);
    }

    // UPDATE
    //=============================================================================
    public User connectUser(Long id){
        User original = userRepo.getOne(id);
        original.setConnected(true);
        return userRepo.save(original);
    }

    public User disconnectUser(Long id){
        User original = userRepo.getOne(id);
        original.setConnected(false);
        return userRepo.save(original);
    }

    // DELETE
    //=============================================================================
    public Boolean deleteUser(Long id){
        userRepo.deleteById(id);
        return true;
    }

    public Boolean deleteAll(){
        userRepo.deleteAll();
        return true;
    }

}
