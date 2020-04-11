package ZCW.ChatApp.services;

import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.User;
import ZCW.ChatApp.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.mockito.BDDMockito.given;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

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

    @Test
    public void findByIdSuccessTest(){
        User mockUser = new User("Moe", "Aydin", "muhammeta7", "password", false);
        doReturn(Optional.of(mockUser)).when(repo).findById(mockUser.getId());

        Optional<User> returnUser = userService.findById(mockUser.getId());

        Assertions.assertTrue(returnUser.isPresent(), "No User was found");
        Assertions.assertSame(returnUser.get(), mockUser, "Models don't match");
    }

    @Test
    public void findByIdFailTest(){
        doReturn(Optional.empty()).when(repo).findById(1L);

        Optional<User> returnUser = userService.findById(1L);

        Assertions.assertFalse(returnUser.isPresent(), "User found it shouldn't be.");
    }

    @Test
    public void findAllUsersTest(){
        User mockUser1 = new User("Moe", "Aydin", "muhammeta7", "password", false);
        User mockUser2 = new User("Jack", "Black", "jack7", "password2", false);
        doReturn(Arrays.asList(mockUser1, mockUser2)).when(repo).findAll();

        List<User> returnList = userService.findAll();

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
    public void findUsersByUserNameTest(){
        User mockUser = new User("Moe", "Aydin", "muhammeta7", "password", false);
        doReturn(Optional.of(mockUser)).when(repo).findByUserName(mockUser.getUserName());

        Optional<User> returnUser = userService.findUserByUsername(mockUser.getUserName());

        Assertions.assertTrue(returnUser.isPresent(), "No user was found");
        Assertions.assertSame(returnUser.get(), mockUser, "Models don't match");
    }

    @Test
    public void findUsersByChannelTest(){
        User mockUser = new User("Moe", "Aydin", "muhammeta7", "password", false);
        User mockUser1 = new User("Joe", "Aydin", "password", "something", false);
        HashSet<User> users = new HashSet<>();
        users.add(mockUser1);
        users.add(mockUser);
        Channel mockChannel = new Channel("Labs", users, false);
        doReturn(Arrays.asList(mockUser, mockUser1)).when(repo).findAllByChannels(mockChannel);

        Mockito.when(channelService.getChannel(mockChannel.getId())).thenReturn(mockChannel);
        Integer expected = 2;
        Integer actual = userService.findUsersByChannel(mockChannel.getId()).size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void saveTest() {
        User mockUser = new User("Moe", "Aydin", "muhammeta7", "password", false);
        doReturn(mockUser).when(repo).save(any());

        User returnUser = userService.save(mockUser);

        Assertions.assertNotNull(returnUser, "Saved user should not be null");
    }

    @Test
    public void createUserSuccessTest() throws IllegalArgumentException {
        User mockUser = new User("Moe", "Aydin", "password", "muhammeta7", false);
        doReturn(mockUser).when(repo).save(any());

        User returnUser = userService.create(mockUser);

        Assertions.assertNotNull(returnUser, "The User should not be null");
    }

    @Test
    public void createUserNameFailsTest() {
        User mockUser = new User("Moe", "Aydin", "muhammeta7", "password", false);
        User mockUser2 = new User("Jack", "Black", "muhammeta7", "password2", false);
        doReturn(Optional.of(mockUser)).when(repo).findByUserName(any());
        doReturn(Optional.of(mockUser)).when(repo).save(any());
        doReturn(Optional.of(mockUser2)).when(repo).findByUserName(any());

        Assertions.assertThrows(IllegalArgumentException.class , () -> userService.create(mockUser2));
    }

    @Test
    public void updateUsernameSuccessTest() throws IllegalArgumentException{
        User mockUser = new User("Moe", "Aydin", "password", "muhammeta7", false);
        given(repo.findById(mockUser.getId())).willReturn(Optional.of(mockUser));
        given(repo.save(mockUser)).willReturn(any());

        Optional<User> returnUser = userService.updateUserName(mockUser.getId(), "newUserName");
        String expected = "newUserName";
        String actual = returnUser.get().getUserName();

        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void updateUserNameFailsTest() {
        User mockUser = new User("Moe", "Aydin", "muhammeta7", "password", false);
        User mockUser2 = new User("Jack", "Black", "jack", "password2", false);
        doReturn(Optional.of(mockUser)).when(repo).findByUserName(any());
        doReturn(Optional.of(mockUser)).when(repo).save(any());
        doReturn(Optional.of(mockUser2)).when(repo).findByUserName(any());
        doReturn(Optional.of(mockUser2)).when(repo).save(any());

        Long id = mockUser2.getId();
        String fail = "muhammeta7";

        Assertions.assertThrows(IllegalArgumentException.class , () -> userService.updateUserName(id,fail));
    }

    @Test
    public void updatePasswordTest(){
        User mockUser = new User("Moe", "Aydin", "password", "muhammeta7", false);
        given(repo.findById(mockUser.getId())).willReturn(Optional.of(mockUser));
        given(repo.save(mockUser)).willReturn(any());

        Optional<User> returnUser = userService.updatePassword(mockUser.getId(), "somethingElse");
        String expected = "somethingElse";
        String actual = returnUser.get().getPassword();

        Assertions.assertEquals(expected,actual);
    }


    @Test
    public void updateConnectionTest(){
        User mockUser = new User("Moe", "Aydin", "password", "muhammeta7", false);
        doReturn(mockUser).when(repo).save(mockUser);
        doReturn(mockUser).when(repo).getOne(1L);

        Boolean actual = userService.updateConnection(1L).isConnected();

        Assertions.assertTrue(actual);
    }

    @Test
    public void updateConnectionFalse(){
        User mockUser = new User("Moe", "Aydin", "password", "muhammeta7", false);
        doReturn(mockUser).when(repo).save(mockUser);
        doReturn(mockUser).when(repo).getOne(1L);

        userService.updateConnection(1L);
        Boolean actual = userService.updateConnection(1L).isConnected();

        Assertions.assertFalse(actual);
    }

    @Test
    public void joinChannelByIdTest() throws Exception {
        User mockUser = new User("Moe", "Aydin", "password", "muhammeta7", false);
        Channel mockChannel = new Channel("Labs", new HashSet<>(), false);
        doReturn(Optional.of(mockUser)).when(repo).findById(any());
        doReturn(Optional.of(mockChannel)).when(channelService).findById(any());

        userService.joinChannelById(mockUser.getId(), mockChannel.getId());
        Integer expected = 1;
        Integer actual = mockUser.getChannels().size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void joinChannelByIdFailTest() {
        User mockUser = new User("Moe", "Aydin", "password", "muhammeta7", false);
        Channel mockChannel = new Channel("Labs", new HashSet<>(), true);
        doReturn(Optional.of(mockUser)).when(repo).findById(any());
        doReturn(Optional.of(mockChannel)).when(channelService).findById(any());

        Long channelId = mockChannel.getId();
        Long userId = mockUser.getId();

        Assertions.assertThrows(Exception.class, () -> userService.joinChannelById(userId, channelId));
    }

    @Test
    public void leaveChannelByIdTest() throws Exception {
        User mockUser = new User("Moe", "Aydin", "password", "muhammeta7", false);
        Channel mockChannel = new Channel("Labs", new HashSet<>(), false);
        Channel mockChannel1 = new Channel("Labs", new HashSet<>(), false);

        doReturn(Optional.of(mockUser)).when(repo).findById(any());
        doReturn(Optional.of(mockChannel1)).when(channelService).findById(any());
        doReturn(Optional.of(mockChannel)).when(channelService).findById(any());

        userService.joinChannelById(mockUser.getId(), mockChannel.getId());
        userService.joinChannelById(mockUser.getId(), mockChannel1.getId());

        mockUser.getChannels().add(mockChannel);
        mockUser.getChannels().add(mockChannel1);

        userService.leaveChannelById(mockUser.getId(), mockChannel.getId());
        Integer expected = 1;
        Integer actual = mockUser.getChannels().size();

        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(mockUser.getChannels().contains(mockChannel));
    }

    @Test
    public void deleteUserTest(){
        User mockUser = new User("Moe", "Aydin", "password", "muhammeta7", false);
        doReturn(Optional.of(mockUser)).when(repo).findById(any());

        Boolean actual = userService.deleteUser(mockUser.getId());

        Assertions.assertTrue(actual);
        verify(repo, times(1)).deleteById(mockUser.getId());
    }

    @Test
    public void deleteUserThatExistsTest(){
        Boolean actual = userService.deleteUser(1L);
        Assertions.assertFalse(actual);
        verify(repo, times(0)).deleteById(1l);
    }

    @Test
    public void deleteAllTrueTest(){
        User mockUser1 = new User("Moe", "Aydin", "muhammeta7", "password", false);
        User mockUser2 = new User("Jack", "Black", "jack7", "password2", false);
        doReturn(Arrays.asList(mockUser1, mockUser2)).when(repo).findAll();

        List<User> returnUsers = userService.findAll();
        Integer expected = returnUsers.size();
        Boolean actual = userService.deleteAll();

        Assertions.assertTrue(actual);
        Assertions.assertEquals(2, expected);
        verify(repo, times(1)).deleteAll();
    }

    @Test
    public void deleteAllFalseTest(){
        Boolean actual = userService.deleteAll();

        Assertions.assertFalse(actual);
        verify(repo, times(0)).deleteAll();
    }

}
