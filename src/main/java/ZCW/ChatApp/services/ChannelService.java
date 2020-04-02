package ZCW.ChatApp.services;

import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.User;
import ZCW.ChatApp.repositories.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChannelService {

    private ChannelRepository channelRepository;

    @Autowired
    public ChannelService(ChannelRepository channelRepository){
        this.channelRepository = channelRepository;
    }

    // POST
    //=============================================================================
    public Channel create(String name, User... users){
        Set<User> set = new HashSet<>(Arrays.asList(users));
        Channel channel = new Channel(name, set, true);
        return channelRepository.save(channel);
    }

    // GET
    //=============================================================================
    public Optional<Channel> findById(Long id, User user){
        User[] holder = new User[1];
        channelRepository.findById(id).ifPresent(channel -> {
            if (channel.getUsers().contains(user))
                holder[0] = user;
        });
        return (holder[0].equals(user)) ? channelRepository.findById(id) : Optional.empty();
    }

    public Optional<Channel> findById(Long id){
        return channelRepository.findById(id);
    }

    public List<Channel> findAll(User user){
        List<Channel> channels = channelRepository.findAll();
        channels.removeIf(channel -> !channel.getUsers().contains(user));
        return channels;
    }

    public List<Channel> findAll(){
        return channelRepository.findAll();
    }

    // UPDATE
    //=============================================================================


    // DELETE
    //=============================================================================
    public Boolean delete(Long id){
        if (channelRepository.findById(id).isPresent()){
            channelRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Boolean deleteAll(){
        channelRepository.deleteAll();
        return true;
    }
}
