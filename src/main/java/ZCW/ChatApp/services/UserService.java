package ZCW.ChatApp.services;

import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.DAOUser;
import ZCW.ChatApp.repositories.UserDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserDaoRepository userRepo;

    @Autowired
    private ChannelService channelService;

    @Autowired
    public UserService(UserDaoRepository userRepo) {
        this.userRepo = userRepo;
    }

    public DAOUser save(DAOUser user){
        return userRepo.save(user);
    }

    // POST
    //=============================================================================
    public DAOUser create(DAOUser user) throws IllegalArgumentException{
        if(!userRepo.findByUserName(user.getUserName()).isPresent()){
            return userRepo.save(user);
        }
        throw new IllegalArgumentException("Username is taken. Try something else.");
    }

    // GET
    //=============================================================================

    public List<DAOUser> findAll(){
        return userRepo.findAll();
    }

    public Optional<DAOUser> findById(Long id){
        return userRepo.findById(id);
    }

    public DAOUser getUser(Long id){
        return userRepo.getOne(id);
    }

    public Optional<DAOUser> findUserByUsername(String username){ return userRepo.findByUserName(username); }

    public List<DAOUser> findUsersByChannel(Long id){
        return userRepo.findAllByChannels(channelService.getChannel(id));
    }

    public HashSet<Channel> findAllChannelsByUser(String username){
        Optional<DAOUser> user = userRepo.findByUserName(username);
        return user.get().getChannels().stream().filter(c -> !c.getIsDm()).collect(Collectors.toCollection(HashSet::new));
    }

    public HashSet<Channel> findAllDmsByUser(String username){
        Optional<DAOUser> user = userRepo.findByUserName(username);
        return user.get().getChannels().stream().filter(Channel::getIsDm).collect(Collectors.toCollection(HashSet::new));
    }

    // UPDATE
    //=============================================================================
    public DAOUser updateConnection(Long id){
        DAOUser original = userRepo.getOne(id);
        if (original.getConnected()) {
            original.setConnected(false);
        } else {
            original.setConnected(true);
        }
        return userRepo.save(original);
    }

    public Optional<DAOUser> inviteToChannel(String userName, String channelName, String invitedUserName) throws Exception {
        Optional<DAOUser> user = userRepo.findByUserName(userName);
        Optional<DAOUser> invitedUser = userRepo.findByUserName(invitedUserName);
        Optional<Channel> channel = channelService.findByChannelName(channelName);
        if (channel.get().getUsers().contains(user.get())) {
            invitedUser.get().getChannels().add(channel.get());
            channel.get().getUsers().add(invitedUser.get());
            channelService.saveChannel(channel.get());
            userRepo.save(invitedUser.get());
        } else {
            throw new Exception("You do not belong to this channel in the first place!");
        }
        return invitedUser;
    }

    public Optional<DAOUser> joinChannelById(Long userId, Long channelId) throws Exception {
        Optional<DAOUser> original = userRepo.findById(userId);
        Optional<Channel> channel = channelService.findById(channelId);
        if(!channel.get().getIsPrivate()){
            original.get().getChannels().add(channel.get());
            channel.get().getUsers().add(original.get());
            channelService.saveChannel(channel.get());
            userRepo.save(original.get());
        } else {
            throw new Exception("Sorry this channel is private or doesn't exist");
        }
        return original;
    }

    public Optional<DAOUser> leaveChannelById(Long userId, Long channelId){
        Optional<DAOUser> original = userRepo.findById(userId);
        Optional<Channel> channel = channelService.findById(channelId);
        if(original.get().getChannels().contains(channel.get())){
            original.get().getChannels().remove(channel.get());
            channel.get().getUsers().remove(original.get());
            channelService.saveChannel(channel.get());
            userRepo.save(original.get());
        }
        return original;
    }


    public Optional<DAOUser> updateUserName(Long id, String username){
        Optional<DAOUser> original = userRepo.findById(id);
        if(!userRepo.findByUserName(username).isPresent()){
            original.get().setUserName(username);
            userRepo.save(original.get());
        } else {
            throw new IllegalArgumentException("Username is taken. Try something else.");
        }
        return original;
    }

    public Optional<DAOUser> updatePassword(Long id, String password){
        Optional<DAOUser> original = userRepo.findById(id);
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
        return false;
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
