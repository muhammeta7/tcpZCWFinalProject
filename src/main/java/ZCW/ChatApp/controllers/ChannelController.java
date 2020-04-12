package ZCW.ChatApp.controllers;
import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.Message;
import ZCW.ChatApp.models.User;
import ZCW.ChatApp.services.ChannelService;
import ZCW.ChatApp.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/channels")
@CrossOrigin
public class ChannelController {

    private ChannelService channelService;

    @Autowired
    private MessageService messageService;

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

    // GET
    //=============================================================================
    @GetMapping("/{id}")
    public ResponseEntity<?> findChannelById(@PathVariable Long id){
        return channelService.findById(id).map(channel ->
                ResponseEntity.ok().body(channel)).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/chat")
    public ResponseEntity<List<Message>> findAllMessages(@PathVariable Long id){
        return new ResponseEntity<>(MessageController.getMessageService().findByChannel(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Channel>> findAllChannels(){
        return new ResponseEntity<>(channelService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{channelName}/users")
    public ResponseEntity<Set<User>> findAllUsersForChannel(@RequestBody Channel channel){
        return new ResponseEntity<>(channel.getUsers(), HttpStatus.OK);
    }

    // PUT
    //=============================================================================
    @PutMapping("/{id}")
    public ResponseEntity<?> updateChannel(@RequestBody Channel newChannel, @PathVariable Long id){
        Optional<Channel> existingChannel = channelService.findById(id);
        return existingChannel.map(channel -> {
            channel.setChannelName(newChannel.getChannelName());
            channel.setPrivate(newChannel.getPrivate());
            channel.setUsers(newChannel.getUsers());
            channelService.saveChannel(channel);
            try{
                return ResponseEntity
                        .ok()
                        .location(new URI("/"+ channel.getId()))
                        .body(channel);
            } catch (URISyntaxException e) {
                return ResponseEntity.status(HttpStatus.MULTI_STATUS.INTERNAL_SERVER_ERROR).build();
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    //=============================================================================
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteChannel(@PathVariable Long id){
        return new ResponseEntity<>(channelService.delete(id), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Boolean> deleteAllChannel(){
        return new ResponseEntity<>(channelService.deleteAll(), HttpStatus.NOT_FOUND);
    }
  
}
