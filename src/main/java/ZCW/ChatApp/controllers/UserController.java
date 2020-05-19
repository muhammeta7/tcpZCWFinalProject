package ZCW.ChatApp.controllers;


import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.DAOUser;
import ZCW.ChatApp.services.UserService;
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
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    // GET
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
    public ResponseEntity<List<DAOUser>> findAllUsers(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/channel/{channelId}")
    public ResponseEntity<List<DAOUser>> findByChannel(@PathVariable Long channelId){
        return new ResponseEntity<>(userService.findUsersByChannel(channelId), HttpStatus.OK);
    }

    @GetMapping("/channels/{username}")
    public ResponseEntity<Set<Channel>> getAllUserChannels(@PathVariable String username) {
        return new ResponseEntity<>(userService.findAllChannelsByUser(username), HttpStatus.OK);
    }

    @GetMapping("/dms/{userName}")
    public ResponseEntity<Set<Channel>> getAllUserDms(@PathVariable String userName){
        System.out.println(userName);
        return new ResponseEntity<>(userService.findAllDmsByUser(userName), HttpStatus.OK);
    }

    // PUT
    //=============================================================================

    @PutMapping("/{id}/connect")
    public ResponseEntity<DAOUser> connect(@PathVariable Long id){
        return new ResponseEntity<>(userService.updateConnection(id), HttpStatus.OK);
    }

    @PutMapping("/{id}/disconnect")
    public ResponseEntity<DAOUser> disconnect(@PathVariable Long id){
        return new ResponseEntity<>(userService.updateConnection(id), HttpStatus.OK);
    }

    @PutMapping("/update/username/{id}")
    public ResponseEntity<?> updateUserName(@PathVariable Long id, @RequestParam String username){
        Optional<DAOUser> updatedUser = userService.updateUserName(id, username);

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
        Optional<DAOUser> updatedUser = userService.updatePassword(id, password);

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

    @PutMapping("/{userName}/{channelName}/invite/{inviteUserName}")
    public ResponseEntity<?> inviteToChannel(@PathVariable String userName, @PathVariable String channelName, @PathVariable String inviteUserName) throws Exception {
        Optional<DAOUser> inviteUser = userService.inviteToChannel(userName, channelName, inviteUserName);
        return inviteUser
                .map(u -> {
                    try {
                        return ResponseEntity
                                .ok()
                                .location(new URI("/" + userName + "/" + channelName + "/invite/" + u.getUserName()))
                                .body(u);
                    } catch (URISyntaxException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/join")
    public ResponseEntity<?> joinChannel(@PathVariable Long id, @RequestParam Long channelId) throws Exception {
        Optional<DAOUser> updatedUser = userService.joinChannelById(id, channelId);
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
        Optional<DAOUser> updatedUser = userService.leaveChannelById(id, channelId);
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
