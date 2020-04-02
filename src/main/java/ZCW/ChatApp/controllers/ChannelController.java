package ZCW.ChatApp.controllers;


import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.User;
import ZCW.ChatApp.services.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/channels")
public class ChannelController {

    private ChannelService channelService;

    @Autowired
    public ChannelController(ChannelService channelService){
        this.channelService = channelService;
    }

    // POST
    //=============================================================================
    @PostMapping("/create")
    public ResponseEntity<Channel> createChannel(@RequestBody Channel channel){
        return new ResponseEntity<>(channelService.create(channel), HttpStatus.OK);
    }



}
