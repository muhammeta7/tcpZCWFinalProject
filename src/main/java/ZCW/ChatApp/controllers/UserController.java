package ZCW.ChatApp.controllers;

import ZCW.ChatApp.models.User;
import ZCW.ChatApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

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

    // PUT
    //=============================================================================

    // DELETE
    //=============================================================================

}
