package ZCW.ChatApp.services;

import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.Message;
import ZCW.ChatApp.models.DAOUser;
import ZCW.ChatApp.repositories.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChannelService {

    private ChannelRepository channelRepository;

    @Autowired
    private UserService userService;

    @Autowired
    public ChannelService(ChannelRepository channelRepository){
        this.channelRepository = channelRepository;
    }

    // POST
    //=============================================================================
    public Channel create(Channel channel, Long userId){
        if (channelRepository.findChannelByChannelName(channel.getChannelName()).isPresent()){
            throw new IllegalArgumentException("Channel name is taken.Try something else.");
        }
        HashSet<DAOUser> channelCreator = new HashSet<>();
        DAOUser user = userService.getUser(userId);
        channelCreator.add(user);
        Set<Channel> userChannels = user.getChannels();
        userChannels.add(channel);
        channel.setUsers(channelCreator);
        userService.save(user);
        return channelRepository.save(channel);
    }

    // GET
    //=============================================================================
    public Optional<Channel> findById(Long id){
        return channelRepository.findById(id);
    }

    public List<Channel> findAll(){
        return channelRepository.findAll();
    }

    public Channel getChannel(Long id){
        return channelRepository.getOne(id);
    }

    public Channel saveChannel(Channel channel){
        return channelRepository.save(channel);
    }

    public List<Message> findAllMessages(Long id){
        List<Message> messages = channelRepository.findById(id).get().getMessages();
        Comparator<Message> compareMessage = Comparator.comparing(Message::getTimestamp);
        Collections.sort(messages, compareMessage);
        return messages;
    }

    public List<Channel> getAllPublicChannels(){
        return channelRepository.findAll().stream().filter(channel ->
                !channel.getPrivate()).collect(Collectors.toList());
    }

    public Optional<Channel> findByChannelName(String channelName){
        return channelRepository.findChannelByChannelName(channelName);
    }

    // UPDATE
    //=============================================================================
    public Optional<Channel> changeChannelName(Long id,String name ){
        Optional<Channel> original = channelRepository.findById(id);
        original.get().setChannelName(name);
        channelRepository.save(original.get());
        return original;
    }

    public Optional<Channel> changeChannelPrivate(Long id, Boolean value){
        Optional<Channel> original = channelRepository.findById(id);
        original.get().setPrivate(value);
        channelRepository.save(original.get());
        return original;
    }

    // DELETE
    //=============================================================================
    public Boolean delete(Long id){
        if (findById(id).isPresent()){
            channelRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Boolean deleteAll(){
        if (findAll().isEmpty()){
            return false;
        }
        channelRepository.deleteAll();
        return true;
    }
}
