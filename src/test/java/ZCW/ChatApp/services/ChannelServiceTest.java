package ZCW.ChatApp.services;

import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.Message;
import ZCW.ChatApp.models.DAOUser;
import ZCW.ChatApp.repositories.ChannelRepository;
import ZCW.ChatApp.repositories.UserDaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ChannelServiceTest {

    @Autowired
    private ChannelService channelService;

    @MockBean
    private ChannelRepository channelRepository;

    @MockBean
    private UserDaoRepository userDAORepository;

    @Test
    @DisplayName("Test findById Success")
    public void findByIdSuccessTest(){
        Channel mockChannel = new Channel("Labs", new HashSet<>(), true, false);
        doReturn(Optional.of(mockChannel)).when(channelRepository).findById(1L);

        Optional<Channel> returnChannel = channelService.findById(1L);

        Assertions.assertTrue(returnChannel.isPresent(), "No Channel was found.");
        Assertions.assertSame(returnChannel.get(), mockChannel, "Models don't match.");
    }

    @Test
    @DisplayName("Test findById Fail")
    public void findByIdFailTest(){
        doReturn(Optional.empty()).when(channelRepository).findById(1L);

        Optional<Channel> returnChannel = channelService.findById(1L);

        Assertions.assertFalse(returnChannel.isPresent(), "Channel found when it should not be.");
    }

    @Test
    @DisplayName("Test findAll")
    public void findAllChannelsTest(){
        Channel mockChannel1 = new Channel("Labs", new HashSet<>(), true, false);
        Channel mockChannel2 = new Channel("Announcements", new HashSet<>(), true, false);
        doReturn(Arrays.asList(mockChannel1, mockChannel2)).when(channelRepository).findAll();

        List<Channel> returnList = channelService.findAll();

        Assertions.assertEquals(2, returnList.size(), "findAll should return 2 channels.");
    }

    @Test
    @DisplayName("get existing channel test")
    public void getChannelTest(){
        Channel mockChannel = new Channel();
        doReturn(mockChannel).when(channelRepository).getOne(mockChannel.getId());

        Channel returnChannel = channelService.getChannel(mockChannel.getId());

        Assertions.assertNotNull(returnChannel);
    }

    @Test
    @DisplayName("Test saveChannel")
    public void saveChannelTest(){
        Channel mockChannel = new Channel();
        doReturn(mockChannel).when(channelRepository).save(mockChannel);

        Channel returnChannel = channelService.saveChannel(mockChannel);

        Assertions.assertEquals(mockChannel, returnChannel, "Should be equal");
    }

    @Test
    @DisplayName("Test create Channel")
    public void createChannelTest(){
        DAOUser mockUser = new DAOUser("FirstName", "LastName", "UserName", "password", true);
        HashSet<DAOUser> users = new HashSet<>(Collections.singleton(mockUser));
        Channel mockChannel = new Channel("Test", users, true, false);
        doReturn(mockChannel).when(channelRepository).save(any());
        doReturn(mockUser).when(userDAORepository).getOne(any());

        Channel returnChannel = channelService.create(mockChannel, 1L);

        Assertions.assertEquals(returnChannel, mockChannel, "They should be equal");
    }

    @Test
    @DisplayName("Test create DM")
    public void createDMTest(){
        DAOUser mockUser = new DAOUser(1L, "Moe", "Aydin", "muhammet7", "password", true);
        DAOUser dmMockUser = new DAOUser(2L, "Chris", "Farmer", "farmerc", "password", true);
        Channel mockChannel = new Channel(1L, "Moe and Chris", new HashSet<>(Arrays.asList(mockUser, dmMockUser)), true, true);
        doReturn(mockChannel).when(channelRepository).save(any());
        doReturn(Optional.of(mockUser)).when(userDAORepository).findByUserName("muhammet7");
        doReturn(Optional.of(dmMockUser)).when(userDAORepository).findByUserName("farmerc");

        Channel returnChannel = channelService.createDM(new Channel(),"muhammet7", "farmerc");

        Assertions.assertEquals(returnChannel, mockChannel);
    }

    @Test
    public void updateChannelNameTest(){
        Channel mockChannel = new Channel("Labs", new HashSet<>(), true, false);
        given(channelRepository.findById(mockChannel.getId())).willReturn(Optional.of(mockChannel));

        Optional<Channel> returnChannel = channelService.changeChannelName(mockChannel.getId(), "NewName");
        String expected = "NewName";
        String actual = returnChannel.get().getChannelName();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getAllPublicChannelsTest(){
        Channel mockChannel1 = new Channel("Test", new HashSet<>(), false, false);
        Channel mockChannel2 = new Channel("Test", new HashSet<>(), false, false);
        List<Channel> expectedChannels = Arrays.asList(mockChannel1, mockChannel2);
        given(channelRepository.findAll().stream().filter(channel -> !channel.getIsPrivate()).collect(Collectors.toList())).willReturn(expectedChannels);

        List<Channel> actualChannels = channelService.getAllPublicChannels();

        Assertions.assertEquals(expectedChannels.get(0), actualChannels.get(0));
        Assertions.assertEquals(expectedChannels.get(1), actualChannels.get(1));
    }

    @Test
    public void findByChannelNameTest(){
        Channel mockChannel1 = new Channel("Test", new HashSet<>(), false, false);
        given(channelRepository.findChannelByChannelName("Test")).willReturn(Optional.of(mockChannel1));

        Optional<Channel> returnChannel = channelService.findByChannelName("Test");

        Assertions.assertEquals(mockChannel1, returnChannel.get());
    }

    @Test
    public void updateChannelPrivacyTest(){
        Channel mockChannel = new Channel("Labs", new HashSet<>(), true, false);
        given(channelRepository.findById(mockChannel.getId())).willReturn(Optional.of(mockChannel));

        Optional<Channel> returnChannel = channelService.changeChannelPrivacy(mockChannel.getId());

        Assertions.assertFalse(returnChannel.get().getIsPrivate());
    }

    @Test
    @DisplayName("Test delete")
    public void deleteChannelTest(){
        Channel mockChannel = new Channel("Lab", new HashSet<>(), true, false);
        doReturn(Optional.of(mockChannel)).when(channelRepository).findById(any());

        Boolean actual = channelService.delete(mockChannel.getId());

        Assertions.assertTrue(actual);
        verify(channelRepository, times(1)).deleteById(mockChannel.getId());
    }

    @Test
    @DisplayName("Test deleteAll")
    public void deleteAllTest(){
        Channel mockChannel1 = new Channel("Lab", new HashSet<>(), true, false);
        Channel mockChannel2 = new Channel("General", new HashSet<>(), false, false);
        doReturn(Arrays.asList(mockChannel1, mockChannel2)).when(channelRepository).findAll();

        List<Channel> returnChannels = channelService.findAll();
        Integer expected = returnChannels.size();
        Boolean actual = channelService.deleteAll();

        Assertions.assertTrue(actual);
        Assertions.assertEquals(2, expected);
        verify(channelRepository, times(1)).deleteAll();
    }
    @Test
    @DisplayName("test findAllMessages")
    public void findAllMessagesTest(){
        Channel mockChannel = new Channel("test", new HashSet<>(), false, false);
        Message mockMessage1 = new Message(new DAOUser(), "test", new Date(), mockChannel);
        Message mockMessage2 = new Message(new DAOUser(), "test", new Date(), mockChannel);
        List<Message> messages = Arrays.asList(mockMessage1, mockMessage2);
        mockChannel.setMessages(messages);
        doReturn(Optional.of(mockChannel)).when(channelRepository).findById(any());

        List<Message> actual = channelService.findAllMessages(1L);

        Assertions.assertEquals(actual.size(),2);
    }
}
