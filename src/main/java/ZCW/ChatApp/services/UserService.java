package ZCW.ChatApp.services;

import ZCW.ChatApp.models.Channel;
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
    private ChannelService channelService;

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User save(User user){
        return userRepo.save(user);
    }

    // POST
    //=============================================================================
    public User create(User user) throws IllegalArgumentException{
        if(!userRepo.findByUserName(user.getUserName()).isPresent()){
            return userRepo.save(user);
        }
        throw new IllegalArgumentException("Username is taken. Try something else.");
    }

    // GET
    //=============================================================================

    public List<User> findAll(){
        return userRepo.findAll();
    }

    public Optional<User> findById(Long id){
        return userRepo.findById(id);
    }

    public User getUser(Long id){
        return userRepo.getOne(id);
    }

    public Optional<User> findUserByUsername(String username){ return userRepo.findByUserName(username); }

    public List<User> findUsersByChannel(Long id){
        return userRepo.findAllByChannels(channelService.getChannel(id));
    }

    // UPDATE
    //=============================================================================
    public User updateConnection(Long id){
        User original = userRepo.getOne(id);
        if (original.isConnected()) {
            original.setConnected(false);
        } else {
            original.setConnected(true);
        }
        return userRepo.save(original);
    }

    public Optional<User> joinChannelById(Long userId, Long channelId) throws Exception {
        Optional<User> original = userRepo.findById(userId);
        Optional<Channel> channel = channelService.findById(channelId);
        if(!channel.get().getPrivate()){
            original.get().getChannels().add(channel.get());
            channel.get().getUsers().add(original.get());
            channelService.saveChannel(channel.get());
            userRepo.save(original.get());
        } else {
            throw new Exception("Sorry this channel is private or doesn't exist");
        }
        return original;
    }

    public Optional<User> leaveChannelById(Long userId, Long channelId){
        Optional<User> original = userRepo.findById(userId);
        Optional<Channel> channel = channelService.findById(channelId);
        if(original.get().getChannels().contains(channel.get())){
            original.get().getChannels().remove(channel.get());
            channel.get().getUsers().remove(original.get());
            channelService.saveChannel(channel.get());
            userRepo.save(original.get());
        }
        return original;
    }


    public Optional<User> updateUserName(Long id, String username){
        Optional<User> original = userRepo.findById(id);
        if(!userRepo.findByUserName(username).isPresent()){
            original.get().setUserName(username);
            userRepo.save(original.get());
        } else {
            throw new IllegalArgumentException("Username is taken. Try something else.");
        }
        return original;
    }

    public Optional<User> updatePassword(Long id, String password){
        Optional<User> original = userRepo.findById(id);
        original.get().setPassword(password);
        userRepo.save(original.get());
        return original;
    }

    // DELETE
    //=============================================================================
    public Boolean deleteUser(Long id){
        if(findById(id).isPresent()){
            userRepo.deleteById(id);
            return true;
        }
        else return false;
    }

    public Boolean deleteAll(){
        if(findAll().isEmpty()){
            return false;
        } else {
            userRepo.deleteAll();
            return true;
        }
    }

}
