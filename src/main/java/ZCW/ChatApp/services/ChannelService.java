package ZCW.ChatApp.services;

import ZCW.ChatApp.models.Channel;
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
    public Channel create(Channel channel){
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

    public Channel saveChannel(Channel channel){
        return channelRepository.save(channel);
    }


    // UPDATE
    //=============================================================================

    // DELETE
    //=============================================================================
    public Boolean delete(Long id){
        channelRepository.deleteById(id);
        return true;
    }

    public Boolean deleteAll(){
        channelRepository.deleteAll();
        return true;
    }
}
