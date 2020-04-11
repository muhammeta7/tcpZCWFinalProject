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

    // TODO TEST
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

    public User joinChannelById(Long userId, Long channelId){
        User original = userRepo.getOne(userId);
        Channel channel = channelService.getChannel(channelId);
        if(!channel.getPrivate()){
            original.getChannels().add(channel);
            channel.getUsers().add(original);
            channelService.saveChannel(channel);
        }
        return userRepo.save(original);
    }

    public User leaveChannelById(Long userId, Long channelId){
        User original = userRepo.getOne(userId);
        Channel channel = channelService.getChannel(channelId);
        original.getChannels().remove(channel);
        channel.getUsers().remove(original);
        channelService.saveChannel(channel);
        return userRepo.save(original);
    }

    // Update username, password for user


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
        userRepo.deleteAll();
        return true;
    }

}
