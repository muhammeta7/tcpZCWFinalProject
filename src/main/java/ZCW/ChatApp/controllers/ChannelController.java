package ZCW.ChatApp.controllers;
import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.Message;
import ZCW.ChatApp.models.DAOUser;
import ZCW.ChatApp.services.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/channels")
@CrossOrigin
public class ChannelController {

    private ChannelService channelService;

    @Autowired
    public ChannelController(ChannelService channelService){
        this.channelService = channelService;
    }

    // POST
    //=============================================================================
    @PostMapping("/create/user/{userId}")
    public ResponseEntity<Channel> createChannel(@RequestBody Channel channel, @PathVariable Long userId){
        return new ResponseEntity<>(channelService.create(channel, userId), HttpStatus.CREATED);
    }

    // GET
    //=============================================================================
    @GetMapping("/{id}")
    public ResponseEntity<?> findChannelById(@PathVariable Long id){
        return channelService.findById(id).map(channel ->
                ResponseEntity.ok().body(channel)).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/chat/{id}")
    public ResponseEntity<List<Message>> findAllMessages(@PathVariable Long id){
        return new ResponseEntity<>(channelService.findAllMessages(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Channel>> findAllChannels(){
        return new ResponseEntity<>(channelService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{channelName}/users")
    public ResponseEntity<Set<DAOUser>> findAllUsersForChannel(@RequestBody Channel channel){
        return new ResponseEntity<>(channel.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/public")
    public ResponseEntity<List<Channel>> findAllPublicChannels(){
        return new ResponseEntity<>(channelService.getAllPublicChannels(), HttpStatus.OK);
    }

    // PUT
    //=============================================================================

    @PutMapping("/{id}/changeName")
    public ResponseEntity<?> updateChannelName(@PathVariable Long id, @RequestParam String channelName){
        Optional<Channel> updatedChannel = channelService.changeChannelName(id, channelName);
        return updatedChannel
                .map(c -> {
                    try{
                        return ResponseEntity
                                .ok()
                                .location(new URI(c.getId() +"/changeName" ))
                                .body(c);
                    }catch(URISyntaxException e){
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                }).orElse(ResponseEntity.notFound().build());

    }

    @PutMapping("/{id}/changePrivacy")
    public ResponseEntity<?> updateChannelPrivacy(@PathVariable Long id) {
        Optional<Channel> updatedChannel = channelService.changeChannelPrivacy(id);

        return updatedChannel
                .map(c -> {
                    try {
                        return ResponseEntity
                                .ok()
                                .location(new URI(c.getId() + "/changePrivacy"))
                                .body(c);
                    } catch (URISyntaxException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    //=============================================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteChannel(@PathVariable Long id){
        return new ResponseEntity<>(channelService.delete(id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Boolean> deleteAllChannel(){
        return new ResponseEntity<>(channelService.deleteAll(), HttpStatus.ACCEPTED);
    }
  
}
