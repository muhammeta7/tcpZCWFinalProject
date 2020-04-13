package ZCW.ChatApp.services;

import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.Message;
import ZCW.ChatApp.models.User;
import ZCW.ChatApp.repositories.ChannelRepository;
import ZCW.ChatApp.repositories.MessageRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ChannelServiceTest {

    @Autowired
    private ChannelService channelService;

    @Autowired
    private MessageService messageService;

    @MockBean
    private ChannelRepository channelRepository;

    @MockBean
    private MessageRepository messageRepository;

    @Test
    @DisplayName("Test findById Success")
    public void findByIdSuccessTest(){
        Channel mockChannel = new Channel("Labs", new HashSet<>(), true);
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
        Channel mockChannel1 = new Channel("Labs", new HashSet<>(), true);
        Channel mockChannel2 = new Channel("Announcements", new HashSet<>(), true);
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
        Channel mockChannel = new Channel();
        doReturn(mockChannel).when(channelRepository).save(mockChannel);

        Channel returnChannel = channelService.create(mockChannel);

        Assertions.assertEquals(returnChannel, mockChannel, "They should be equal");
    }

    @Test
    @DisplayName("Test delete")
    public void deleteChannelTest(){
        Channel mockChannel = new Channel("Lab", new HashSet<>(), true);
        doReturn(Optional.of(mockChannel)).when(channelRepository).findById(any());

        Boolean actual = channelService.delete(mockChannel.getId());

        Assertions.assertTrue(actual);
        verify(channelRepository, times(1)).deleteById(mockChannel.getId());
    }

    @Test
    @DisplayName("Test deleteAll")
    public void deleteAllTest(){
        Channel mockChannel1 = new Channel("Lab", new HashSet<>(), true);
        Channel mockChannel2 = new Channel("General", new HashSet<>(), false);
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
        Channel mockChannel = new Channel("test", new HashSet<>(), false);
        Message mockMessage1 = new Message(new User(), "test", new Date(), mockChannel);
        Message mockMessage2 = new Message(new User(), "test", new Date(), mockChannel);
        doReturn(mockChannel).when(channelRepository).save(mockChannel);
        doReturn(mockMessage1).when(messageRepository).save(mockMessage1);
        doReturn(mockMessage2).when(messageRepository).save(mockMessage2);
        doReturn(Arrays.asList(mockMessage1, mockMessage2)).when(messageRepository).findByChannelId(mockChannel.getId());

        List<Message> actual = messageService.findByChannel(mockChannel.getId());

        Assertions.assertEquals(actual.size(),2);
    }
}
