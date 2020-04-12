package ZCW.ChatApp.controllers;

import ZCW.ChatApp.models.Message;
import ZCW.ChatApp.models.User;
import ZCW.ChatApp.services.MessageService;
import ZCW.ChatApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // POST
    //=============================================================================
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userService.create(user);
        try{
            return ResponseEntity
                    .created(new URI("/create/" + newUser.getId()))
                    .body(user);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET TODO Write Failing tests findAll Users, FindByChannel
    //=============================================================================
    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id){
        return userService.findById(id)
                .map(u -> ResponseEntity
                        .ok()
                        .body(u))
                .orElse(ResponseEntity
                        .notFound()
                        .build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username){
        return userService.findUserByUsername(username)
                .map(u -> ResponseEntity
                        .ok()
                        .body(u))
                .orElse(ResponseEntity
                        .notFound()
                        .build());
    }

    @GetMapping
    public ResponseEntity<List<User>> findAllUsers(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/channel/{channelId}")
    public ResponseEntity<List<User>> findByChannel(@PathVariable Long channelId){
        return new ResponseEntity<>(userService.findUsersByChannel(channelId), HttpStatus.OK);
    }

    // PUT TODO change last 2 methods to optionals and write Fail tests
    //=============================================================================

    @PutMapping("/{id}/connect")
    public ResponseEntity<User> connect(@PathVariable Long id){
        return new ResponseEntity<>(userService.updateConnection(id), HttpStatus.OK);
    }

    @PutMapping("/{id}/disconnect")
    public ResponseEntity<User> disconnect(@PathVariable Long id){
        return new ResponseEntity<>(userService.updateConnection(id), HttpStatus.OK);
    }

    @PutMapping("/update/username/{id}")
    public ResponseEntity<?> updateUserName(@PathVariable Long id, @RequestParam String username){
        Optional<User> updatedUser = userService.updateUserName(id, username);

        return updatedUser
                .map(u -> {
                    try{
                        return ResponseEntity
                                .ok()
                                .location(new URI("/update/username/" + u.getId()))
                                .body(u);
                    }catch(URISyntaxException e){
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/password/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestParam String password){
        Optional<User> updatedUser = userService.updatePassword(id, password);

        return updatedUser
                .map(u -> {
                    try{
                        return ResponseEntity
                                .ok()
                                .location(new URI("/update/password/" + u.getId()))
                                .body(u);
                    }catch(URISyntaxException e){
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/join")
    public ResponseEntity<?> joinChannel(@PathVariable Long id, @RequestParam Long channelId) throws Exception {
        Optional<User> updatedUser = userService.joinChannelById(id, channelId);
        return updatedUser
                .map(u -> {
                    try{
                        return ResponseEntity
                                .ok()
                                .location(new URI(u.getId() + "/join"))
                                .body(u);
                    }catch(URISyntaxException e){
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                }).orElse(ResponseEntity.notFound().build());

    }

    @PutMapping("/{id}/leave")
    public ResponseEntity<?> leaveChannel(@PathVariable Long id, @RequestParam Long channelId){
        Optional<User> updatedUser = userService.leaveChannelById(id, channelId);
        return updatedUser
                .map(u -> {
                    try{
                        return ResponseEntity
                                .ok()
                                .location(new URI(u.getId() + "/leave"))
                                .body(u);
                    }catch(URISyntaxException e){
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    //=============================================================================
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id) {
        return (userService.deleteUser(id)) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAllUsers() {
        return (!userService.deleteAll()) ? ResponseEntity.notFound().build() : ResponseEntity.ok().build();
    }

}
