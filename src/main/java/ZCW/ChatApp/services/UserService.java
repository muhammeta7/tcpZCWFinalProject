package ZCW.ChatApp.services;

import ZCW.ChatApp.models.User;
import ZCW.ChatApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if(userRepo.findByUserName(user.getUserName()) == null){
            return userRepo.save(user);
        }
        throw new Exception("Username is taken. Try something else.");
    }

    // GET
    //=============================================================================
    public List<User> findAll(){
        return userRepo.findAll();
    }

    public User findOne(Long id){
        return userRepo.getOne(id);
    }

    // UPDATE
    //=============================================================================

    // DELETE
    //=============================================================================



}
