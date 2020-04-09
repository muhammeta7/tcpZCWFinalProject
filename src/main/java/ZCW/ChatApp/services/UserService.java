package ZCW.ChatApp.services;

import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.Message;
import ZCW.ChatApp.models.User;
import ZCW.ChatApp.repositories.ChannelRepository;
import ZCW.ChatApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepo;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private MessageService messageService;

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

    public User getUser(Long id){
        return userRepo.getOne(id);
    }

    public Optional<User> findUserByUsername(String username){ return userRepo.findByUserName(username); }

    public Optional<User> findUserByFirstName(String firstName) {return userRepo.findByFirstName(firstName);}

    public Optional<User> findUserByLastName(String lastName) { return userRepo.findByLastName(lastName);}

//    public List<Message> getUserMessages(){
//        return null;
//    }

    // TODO Get All User Messages user message Service ADD ENDPOINT TO CONTROLLER
    // TODO GET ALL User Channels
    // TODO GET ALL Messages By Channel

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

    public User joinChannelById(Long userId, Long channelId){
        User original = userRepo.getOne(userId);
        Channel channel = channelService.getChannel(channelId);
        if(!channel.getPrivate()){
            original.getChannels().add(channel);
        }
        return userRepo.save(original);
    }

    public User leaveChannelById(Long userId, Long channelId){
        User original = userRepo.getOne(userId);
        Channel channel = channelService.getChannel(channelId);
        original.getChannels().remove(channel);
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
