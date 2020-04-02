package ZCW.ChatApp.controllers;

import ZCW.ChatApp.models.User;
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
    public ResponseEntity<User> createUser(@RequestBody User user) throws Exception {
        User newUser = userService.create(user);
        try{
            return ResponseEntity
                    .created(new URI("/create/" + newUser.getId()))
                    .body(user);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    // GET
    //=============================================================================
    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id){
        return userService.findById(id)
                .map(emp -> ResponseEntity
                        .ok()
                        .body(emp))
                .orElse(ResponseEntity
                        .notFound()
                        .build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username){
        return userService.findUserByUsername(username)
                .map(emp -> ResponseEntity
                        .ok()
                        .body(emp))
                .orElse(ResponseEntity
                        .notFound()
                        .build());
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> findAllUsers(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    // PUT
    //=============================================================================
    @PutMapping("/{id}/connect")
    public ResponseEntity<User> connect(@PathVariable Long id){
        return new ResponseEntity<>(userService.connectUser(id), HttpStatus.OK);
    }

    @PutMapping("/{id}/disconnect")
    public ResponseEntity<User> disconnect(@PathVariable Long id){
        return new ResponseEntity<>(userService.disconnectUser(id), HttpStatus.OK);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable Long id){
        Optional<User> existingUser = userService.findById(id);
        return existingUser
                .map(u -> {
                    u.setFirstName(user.getFirstName());
                    u.setLastName(user.getLastName());
                    u.setPassword(user.getPassword());
                    u.setUserName(user.getUserName());

                    try{
                        return ResponseEntity
                                .ok()
                                .location(new URI("/updateUser/" + u.getId()))
                                .body(u);
                    } catch(URISyntaxException e){
                        return ResponseEntity.status(HttpStatus.MULTI_STATUS.INTERNAL_SERVER_ERROR).build();
                    }
                }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    //=============================================================================
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Boolean> deleteAllUsers() {
        return new ResponseEntity<>(userService.deleteAll(), HttpStatus.NOT_FOUND);
    }

}
