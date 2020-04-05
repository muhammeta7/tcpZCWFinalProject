package ZCW.ChatApp.services;

import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.User;
import ZCW.ChatApp.repositories.UserRepository;
import jdk.nashorn.internal.runtime.options.Option;
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

    public User save(User user){
        return userRepo.save(user);
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

    public Optional<User> findUserByUsername(String username){ return userRepo.findByUserName(username); }

    public Optional<User> findUserByFirstName(String firstName) {return userRepo.findByFirstName(firstName);}

    public Optional<User> findUserByLastName(String lastName) { return userRepo.findByLastName(lastName);}


    // TODO Get Messages & getChannels

    // UPDATE
    //=============================================================================

    public User updateConnection(Long id){
        User original = userRepo.getOne(id);
        if (original.isConnected()) {
            original.setConnected(true);
            save(original);
        } else {
            original.setConnected(false);
            save(original);
        }
        return original;
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
