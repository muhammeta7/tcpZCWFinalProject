package ZCW.ChatApp.controllers;

import ZCW.ChatApp.models.Message;
import ZCW.ChatApp.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/messages")
@CrossOrigin
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // POST
    //=============================================================================
    @PostMapping("/channel/{channelId}/sender/{userId}")
    public ResponseEntity<Message> create(@RequestBody Message message, @PathVariable Long userId, @PathVariable Long channelId){
        Message newMessage = messageService.create(message, userId, channelId);
        try{
            return ResponseEntity.created(new URI("/channel/" + channelId + "/sender/" + userId + "/" + message.getId())).body(message);
        } catch(URISyntaxException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET
    //=============================================================================
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return new ResponseEntity<>(messageService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Message>> showAll(){
        return new ResponseEntity<>((messageService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/sender/{userId}")
    public ResponseEntity<List<Message>> findBySender(@PathVariable Long userId){
        return new ResponseEntity<>(messageService.findMessagesByUserId(userId), HttpStatus.OK);
    }

    // PUT
    //=============================================================================
    @PutMapping("/{id}/edit")
    public ResponseEntity<?> updateMessage(@RequestParam String newContent, @PathVariable Long id){
        Optional<Message> existingMessage = messageService.findById(id);
        return existingMessage
                .map(m -> {
                    m.setContent(newContent);
                    messageService.save(m);
                    try{
                        return ResponseEntity
                                .ok()
                                .location(new URI("/" + m.getId()))
                                .body(m);
                    } catch(URISyntaxException e){
                        return ResponseEntity.status(HttpStatus.MULTI_STATUS.INTERNAL_SERVER_ERROR).build();
                    }
                }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    //=============================================================================
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteMessage(@PathVariable Long id) {
        return new ResponseEntity<>(messageService.delete(id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Boolean> deleteAllMessages() {
        return new ResponseEntity<>(messageService.deleteAll(), HttpStatus.ACCEPTED);
    }

}
