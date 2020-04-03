package ZCW.ChatApp.controllers;

import ZCW.ChatApp.models.Message;
import ZCW.ChatApp.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // POST
    //=============================================================================
    @PostMapping("/create")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message){
        return new ResponseEntity<>(messageService.create(message), HttpStatus.OK);
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

    @GetMapping("/sender/{username}")
    public ResponseEntity<List<Message>> findBySender(@PathVariable String username, Pageable pageable){
        return new ResponseEntity<>(messageService.findBySender(username, pageable), HttpStatus.OK);
    }

    // TODO possibly get message by Date

    // PUT
    //=============================================================================
    @PutMapping("/updateMessage/{id}")
    public ResponseEntity<?> updateMessage(@RequestBody Message message, @PathVariable Long id){
        Optional<Message> existingMessage = messageService.findById(id);
        return existingMessage
                .map(m -> {
                    m.setContent(message.getContent());
                    try{
                        return ResponseEntity
                                .ok()
                                .location(new URI("/updateMessage/" + m.getId()))
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
        return new ResponseEntity<>(messageService.delete(id), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Boolean> deleteAllUsers() {
        return new ResponseEntity<>(messageService.deletAll(), HttpStatus.NOT_FOUND);
    }

}
