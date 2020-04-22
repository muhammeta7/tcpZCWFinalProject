package ZCW.ChatApp.services;

import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.DAOUser;
import ZCW.ChatApp.repositories.UserDaoRepository;
import org.junit.Assert;
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
public class DAOUserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserDaoRepository repo;

    @MockBean
    private ChannelService channelService;

    // POST
    //===================================================================================================================================

    @Test
    public void createUserSuccessTest() throws IllegalArgumentException {
        DAOUser mockUser = new DAOUser("Moe", "Aydin", "password", "muhammeta7", false);
        doReturn(mockUser).when(repo).save(any());

        DAOUser returnUser = userService.create(mockUser);

        Assertions.assertNotNull(returnUser, "The User should not be null");
    }

    @Test
    public void createUserNameFailsTest() {
        DAOUser mockUser = new DAOUser("Moe", "Aydin", "muhammeta7", "password", false);
        DAOUser mockUser2 = new DAOUser("Jack", "Black", "muhammeta7", "password2", false);
        doReturn(Optional.of(mockUser)).when(repo).findByUserName(any());
        doReturn(Optional.of(mockUser)).when(repo).save(any());
        doReturn(Optional.of(mockUser2)).when(repo).findByUserName(any());

        Assertions.assertThrows(IllegalArgumentException.class , () -> userService.create(mockUser2));
    }

    // GET
    //===================================================================================================================================
    @Test
    public void findByIdSuccessTest(){
        DAOUser mockUser = new DAOUser("Moe", "Aydin", "muhammeta7", "password", false);
        doReturn(Optional.of(mockUser)).when(repo).findById(mockUser.getId());

        Optional<DAOUser> returnUser = userService.findById(mockUser.getId());

        Assertions.assertTrue(returnUser.isPresent(), "No User was found");
        Assertions.assertSame(returnUser.get(), mockUser, "Models don't match");
    }

    @Test
    public void findByIdFailTest(){
        doReturn(Optional.empty()).when(repo).findById(1L);

        Optional<DAOUser> returnUser = userService.findById(1L);

        Assertions.assertFalse(returnUser.isPresent(), "User found it shouldn't be.");
    }

    @Test
    public void findAllUsersTest(){
        DAOUser mockUser1 = new DAOUser("Moe", "Aydin", "muhammeta7", "password", false);
        DAOUser mockUser2 = new DAOUser("Jack", "Black", "jack7", "password2", false);
        doReturn(Arrays.asList(mockUser1, mockUser2)).when(repo).findAll();

        List<DAOUser> returnList = userService.findAll();

        Assertions.assertEquals(2, returnList.size(), "findAll should return 2 users");
    }

    @Test
    @DisplayName("Test get user")
    public void getUserTest(){
        DAOUser mockUser = new DAOUser("Moe", "Aydin", "muhammeta7", "password", false);
        doReturn(mockUser).when(repo).getOne(mockUser.getId());

        DAOUser returnUser = userService.getUser(mockUser.getId());

        Assertions.assertNotNull(returnUser);
    }

    @Test
    public void findUsersByUserNameTest(){
        DAOUser mockUser = new DAOUser("Moe", "Aydin", "muhammeta7", "password", false);
        doReturn(Optional.of(mockUser)).when(repo).findByUserName(mockUser.getUserName());

        Optional<DAOUser> returnUser = userService.findUserByUsername(mockUser.getUserName());

        Assertions.assertTrue(returnUser.isPresent(), "No user was found");
        Assertions.assertSame(returnUser.get(), mockUser, "Models don't match");
    }

    @Test
    public void findUsersByChannelTest(){
        DAOUser mockUser = new DAOUser("Moe", "Aydin", "muhammeta7", "password", false);
        DAOUser mockUser1 = new DAOUser("Joe", "Aydin", "password", "something", false);
        HashSet<DAOUser> users = new HashSet<>();
        users.add(mockUser1);
        users.add(mockUser);
        Channel mockChannel = new Channel("Labs", users, false, false);
        doReturn(Arrays.asList(mockUser, mockUser1)).when(repo).findAllByChannels(mockChannel);

        Mockito.when(channelService.getChannel(mockChannel.getId())).thenReturn(mockChannel);
        Integer expected = 2;
        Integer actual = userService.findUsersByChannel(mockChannel.getId()).size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void saveTest() {
        DAOUser mockUser = new DAOUser("Moe", "Aydin", "muhammeta7", "password", false);
        doReturn(mockUser).when(repo).save(any());

        DAOUser returnUser = userService.save(mockUser);

        Assertions.assertNotNull(returnUser, "Saved user should not be null");
    }

    @Test
    public void findAllChannelsByUserTest(){
        DAOUser mockUser = new DAOUser("Moe", "Aydin", "muhammeta7", "password", true);
        Channel mockChannel1 = new Channel("Labs", new HashSet<>(Collections.singletonList(mockUser)), true, false);
        Channel mockChannel2 = new Channel("Labs", new HashSet<>(Collections.singletonList(mockUser)), true, false);
        HashSet<Channel> channels = new HashSet<>(Arrays.asList(mockChannel1, mockChannel2));
        mockUser.setChannels(channels);
        given(repo.findByUserName("muhammeta7")).willReturn(Optional.of(mockUser));

        HashSet<Channel> returnChannels = userService.findAllChannelsByUser("muhammeta7");

        Assert.assertEquals(returnChannels.size(), 2);
    }

    // PUT
    //===================================================================================================================================

    @Test
    public void updateUsernameSuccessTest() throws IllegalArgumentException{
        DAOUser mockUser = new DAOUser("Moe", "Aydin", "password", "muhammeta7", false);
        given(repo.findById(mockUser.getId())).willReturn(Optional.of(mockUser));
        given(repo.save(mockUser)).willReturn(any());

        Optional<DAOUser> returnUser = userService.updateUserName(mockUser.getId(), "newUserName");
        String expected = "newUserName";
        String actual = returnUser.get().getUserName();

        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void updateUserNameFailsTest() {
        DAOUser mockUser = new DAOUser("Moe", "Aydin", "muhammeta7", "password", false);
        DAOUser mockUser2 = new DAOUser("Jack", "Black", "jack", "password2", false);
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
        DAOUser mockUser = new DAOUser("Moe", "Aydin", "password", "muhammeta7", false);
        given(repo.findById(mockUser.getId())).willReturn(Optional.of(mockUser));
        given(repo.save(mockUser)).willReturn(any());

        Optional<DAOUser> returnUser = userService.updatePassword(mockUser.getId(), "somethingElse");
        String expected = "somethingElse";
        String actual = returnUser.get().getPassword();

        Assertions.assertEquals(expected,actual);
    }


    @Test
    public void updateConnectionTest(){
        DAOUser mockUser = new DAOUser("Moe", "Aydin", "password", "muhammeta7", false);
        doReturn(mockUser).when(repo).save(mockUser);
        doReturn(mockUser).when(repo).getOne(1L);

        Boolean actual = userService.updateConnection(1L).getConnected();

        Assertions.assertTrue(actual);
    }

    @Test
    public void updateConnectionFalse(){
        DAOUser mockUser = new DAOUser("Moe", "Aydin", "password", "muhammeta7", false);
        doReturn(mockUser).when(repo).save(mockUser);
        doReturn(mockUser).when(repo).getOne(1L);

        userService.updateConnection(1L);
        Boolean actual = userService.updateConnection(1L).getConnected();

        Assertions.assertFalse(actual);
    }

    @Test
    public void inviteToChannelTest() throws Exception {
        DAOUser mockUser = new DAOUser("Moe", "Aydin", "password", "muhammeta7", true);
        DAOUser invitedUser = new DAOUser("Chris", "Farmer", "password", "farmerc", true);
        Channel mockChannel = new Channel("Labs", new HashSet<>(), false, false);
        mockChannel.setUsers(new HashSet<>(Collections.singletonList(mockUser)));
        doReturn(Optional.of(mockUser)).when(repo).findByUserName("muhammeta7");
        doReturn(Optional.of(invitedUser)).when(repo).findByUserName("farmerc");
        doReturn(Optional.of(mockChannel)).when(channelService).findByChannelName(any());

        Optional<DAOUser> returnUser = userService.inviteToChannel("muhammeta7", "Labs", "farmerc");

        Assertions.assertTrue(mockChannel.getUsers().contains(invitedUser));
        Assertions.assertEquals(returnUser.get(), invitedUser);
    }

    @Test
    public void joinChannelByIdTest() throws Exception {
        DAOUser mockUser = new DAOUser("Moe", "Aydin", "password", "muhammeta7", false);
        Channel mockChannel = new Channel("Labs", new HashSet<>(), false, false);
        doReturn(Optional.of(mockUser)).when(repo).findById(any());
        doReturn(Optional.of(mockChannel)).when(channelService).findById(any());

        userService.joinChannelById(mockUser.getId(), mockChannel.getId());
        Integer expected = 1;
        Integer actual = mockUser.getChannels().size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void joinChannelByIdFailTest() {
        DAOUser mockUser = new DAOUser("Moe", "Aydin", "password", "muhammeta7", false);
        Channel mockChannel = new Channel("Labs", new HashSet<>(), true, false);
        doReturn(Optional.of(mockUser)).when(repo).findById(any());
        doReturn(Optional.of(mockChannel)).when(channelService).findById(any());

        Long channelId = mockChannel.getId();
        Long userId = mockUser.getId();

        Assertions.assertThrows(Exception.class, () -> userService.joinChannelById(userId, channelId));
    }

    @Test
    public void leaveChannelByIdTest() throws Exception {
        DAOUser mockUser = new DAOUser("Moe", "Aydin", "password", "muhammeta7", false);
        Channel mockChannel = new Channel("Labs", new HashSet<>(), false, false);
        Channel mockChannel1 = new Channel("Labs", new HashSet<>(), false, false);

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

    // DELETE
    //===================================================================================================================================

    @Test
    public void deleteUserTest(){
        DAOUser mockUser = new DAOUser("Moe", "Aydin", "password", "muhammeta7", false);
        doReturn(Optional.of(mockUser)).when(repo).findById(any());

        Boolean actual = userService.deleteUser(mockUser.getId());

        Assertions.assertTrue(actual);
        verify(repo, times(1)).deleteById(mockUser.getId());
    }

    @Test
    public void deleteUserThatExistsTest(){
        Boolean actual = userService.deleteUser(1L);
        Assertions.assertFalse(actual);
        verify(repo, times(0)).deleteById(1L);
    }

    @Test
    public void deleteAllTrueTest(){
        DAOUser mockUser1 = new DAOUser("Moe", "Aydin", "muhammeta7", "password", false);
        DAOUser mockUser2 = new DAOUser("Jack", "Black", "jack7", "password2", false);
        doReturn(Arrays.asList(mockUser1, mockUser2)).when(repo).findAll();

        List<DAOUser> returnUsers = userService.findAll();
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
