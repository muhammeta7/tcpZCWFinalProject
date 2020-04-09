package ZCW.ChatApp.services;

import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.User;
import ZCW.ChatApp.repositories.ChannelRepository;
import ZCW.ChatApp.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository repo;

    @MockBean
    private ChannelService channelService;
    @MockBean
    private ChannelRepository channelRepo;

    @MockBean
    private MessageService messageService;

    @Test
    @DisplayName("Test findbyId Success")
    public void findByIdSuccessTest(){
        // Set Up mock object and repo
        User mockUser = new User("Moe", "Aydin", "muhammeta7", "password", false);
        doReturn(Optional.of(mockUser)).when(repo).findById(mockUser.getId());

        // Execute Call
        Optional<User> returnUser = userService.findById(mockUser.getId());

        // Check Assertions
        Assertions.assertTrue(returnUser.isPresent(), "No User was found");
        Assertions.assertSame(returnUser.get(), mockUser, "Models don't match");
    }

    @Test
    @DisplayName("Test findById Fail")
    public void findByIdFailTest(){
        // Set up mock repo
        doReturn(Optional.empty()).when(repo).findById(1L);
        // Execute Call
        Optional<User> returnUser = userService.findById(1L);
        // Check assertions
        Assertions.assertFalse(returnUser.isPresent(), "User found it shouldn't be.");
    }

    @Test
    @DisplayName("Test findAll")
    public void findAllUsersTest(){
        // Setup mock objects and repo
        User mockUser1 = new User("Moe", "Aydin", "muhammeta7", "password", false);
        User mockUser2 = new User("Jack", "Black", "jack7", "password2", false);
        doReturn(Arrays.asList(mockUser1, mockUser2)).when(repo).findAll();
        // Execute service call
        List<User> returnList = userService.findAll();
        // Check Assertions
        Assertions.assertEquals(2, returnList.size(), "findAll should return 2 users");
    }

    @Test
    @DisplayName("Test get user")
    public void getUserTest(){
        User mockUser = new User("Moe", "Aydin", "muhammeta7", "password", false);
        doReturn(mockUser).when(repo).getOne(mockUser.getId());

        User returnUser = userService.getUser(mockUser.getId());

        Assertions.assertNotNull(returnUser);
    }

    @Test
    @DisplayName("Test findAllByUsername")
    public void findUsersByUserNameTest(){
        User mockUser = new User("Moe", "Aydin", "muhammeta7", "password", false);
        doReturn(Optional.of(mockUser)).when(repo).findByUserName(mockUser.getUserName());

        Optional<User> returnUser = userService.findUserByUsername(mockUser.getUserName());

        Assertions.assertTrue(returnUser.isPresent(), "No user was found");
        Assertions.assertSame(returnUser.get(), mockUser, "Models don't match");
    }

    @Test
    @DisplayName("Test save User")
    public void saveTest() {
        User mockUser = new User("Moe", "Aydin", "muhammeta7", "password", false);
        doReturn(mockUser).when(repo).save(any());

        User returnUser = userService.save(mockUser);

        Assertions.assertNotNull(returnUser, "Saved user should not be null");
    }


    // TODO Fix test
    @Test
    @DisplayName("Test create User Successful")
    public void createUserTest() throws Exception {
        User mockUser = new User("Moe", "Aydin", "password", "muhammeta7", false);
        doReturn(mockUser).when(repo).save(any());

        // Execute service call
        User returnUser = userService.create(mockUser);
        // Check Assertions
        Assertions.assertNotNull(returnUser, "The User should not be null");
    }


    // TODO Fix test
//    @Test
//    DisplayName("Test create User fails")
//    public void createUserNameFailsTest() throws Exception {
//        User mockUser = new User("Moe", "Aydin", "muhammeta7", "password", false);
//        User mockUser2 = new User("Jack", "Black", "muhammeta7", "password2", false);
//        doReturn(mockUser).when(repo).save(any());
//        doReturn(mockUser2).when(repo).save(any());
//
//        User valid = service.create(mockUser);
//        Assertions.assertNotNull(valid);
//
//        Assertions.assertThrows(Exception.class , () -> service.create(mockUser2));
//    }

    // TODO FIX TEST TO FIND ALL USERS IN CHANNEL
//    @Test
//    @DisplayName("Find Users By Channels")
//    public void findUsersByChannelTest(){
//        User mockUser = new User("Moe", "Aydin", "password", "muhammeta7", false);
//        User mockUser1 = new User("Moe", "Aydin", "password", "something", false);
//        HashSet<User> mockUsers = new HashSet<>();
//        mockUsers.add(mockUser);
//        mockUsers.add(mockUser1);
//        Channel mockChannel = new Channel("Labs", mockUsers, false);
//        doReturn(mockChannel).when(channelRepo).getOne(mockChannel.getId()).;
//        doReturn(Arrays.asList(mockUser, mockUser1)).when(repo).findAll();
//
//        Integer expected = 2;
//        Integer actual = userService.findUsersByChannel(mockChannel.getId()).size();
//
//        Assertions.assertEquals(expected, actual);
//    }

    @Test
    @DisplayName("Test connection")
    public void updateConnectionTest(){
        User mockUser = new User("Moe", "Aydin", "password", "muhammeta7", false);
        doReturn(mockUser).when(repo).save(mockUser);
        doReturn(mockUser).when(repo).getOne(1L);

        Boolean actual = userService.updateConnection(1L).isConnected();

        Assertions.assertTrue(actual);
    }

    @Test
    @DisplayName("Test update connection fail")
    public void updateConnectionFalse(){
        User mockUser = new User("Moe", "Aydin", "password", "muhammeta7", false);
        doReturn(mockUser).when(repo).save(mockUser);
        doReturn(mockUser).when(repo).getOne(1L);

        userService.updateConnection(1L);
        Boolean actual = userService.updateConnection(1L).isConnected();

        Assertions.assertFalse(actual);
    }

    @Test
    @DisplayName("Test join Channel By Id")
    public void joinChannelByIdTest(){
        User mockUser = new User("Moe", "Aydin", "password", "muhammeta7", false);
        Channel mockChannel = new Channel("Labs", new HashSet<>(), false);
        doReturn(mockUser).when(repo).getOne(1L);
        doReturn(mockChannel).when(channelService).getChannel(1L);

        userService.joinChannelById(1L, 1L);
        Integer expected = 1;
        Integer actual = mockUser.getChannels().size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test leave Channel By Id")
    public void leaveChannelByIdTest(){
        User mockUser = new User("Moe", "Aydin", "password", "muhammeta7", false);
        Channel mockChannel = new Channel("Labs", new HashSet<>(), false);
        doReturn(mockUser).when(repo).getOne(1L);
        doReturn(mockChannel).when(channelService).getChannel(1L);

        userService.leaveChannelById(1L, 1L);
        Integer expected = 0;
        Integer actual = mockUser.getChannels().size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test delete")
    public void deleteUserTest(){
        User mockUser = new User("Moe", "Aydin", "password", "muhammeta7", false);
        doReturn(mockUser).when(repo).getOne(1L);

        Boolean actual = userService.deleteUser(1L);

        Assertions.assertTrue(actual);
    }

    @Test
    @DisplayName("Test delete all")
    public void deleteAllTest(){
        User mockUser1 = new User("Moe", "Aydin", "muhammeta7", "password", false);
        User mockUser2 = new User("Jack", "Black", "jack7", "password2", false);
        doReturn(Arrays.asList(mockUser1, mockUser2)).when(repo).findAll();

        List<User> returnUsers = userService.findAll();
        Integer expected = returnUsers.size();
        Boolean actual = userService.deleteAll();

        Assertions.assertTrue(actual);
        Assertions.assertEquals(2, expected);
    }

}
