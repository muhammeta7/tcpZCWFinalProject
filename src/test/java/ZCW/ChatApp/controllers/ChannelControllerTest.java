import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.Message;
import ZCW.ChatApp.models.User;
import ZCW.ChatApp.services.ChannelService;
import ZCW.ChatApp.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ChannelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChannelService channelService;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("POST /channel")
    public void createChannelTest() throws Exception{
        HashSet<User> users = new HashSet<>();
        User mockUser = new User(1L, "First Name", "Last Name", "User Name", "Password", true);
        doReturn(mockUser).when(userService).getUser(any());
        mockMvc.perform(MockMvcRequestBuilders
                .post("/channels/create/user/1")
                .content(asJsonString(new Channel(1L,"General", users, true)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("GET /channels/1")
    public void findChannelByIdTest() throws Exception{
        Long givenId = 1L;
        HashSet<User> users = new HashSet<>();
        Channel getChannel = new Channel(1L, "General", users, true);
        given(channelService.findById(givenId)).willReturn(Optional.of(getChannel));

        mockMvc.perform(
                get("/channels/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(getChannel))
            )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.channelName", is("General")))
                .andExpect(jsonPath("$.private", is(true)));
    }

    @Test
    @DisplayName("GET /channels/chat/{id}")
    public void findAllMessagesTest() throws Exception {
        HashSet<User> users = new HashSet<>();
        Channel mockChannel = new Channel(1L, "General", users, true);
        Message mockMessage1 = new Message(new User(), "Hello", new Date(), mockChannel);
        Message mockMessage2 = new Message(new User(), "Hi", new Date(), mockChannel);
        List<Message> messages = Arrays.asList(mockMessage1, mockMessage2);
        mockChannel.setMessages(messages);
        given(channelService.findAllMessages(1L)).willReturn(messages);

        mockMvc.perform(
                get("/channels/chat/1")
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(asJsonString(messages))
            )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$[0].content", is("Hello")))

                .andExpect(jsonPath("$[1].content", is("Hi")));
    }


    @Test
    @DisplayName("GET /channels")
    public void findAllChannelsTest() throws Exception {
        HashSet<User> users = new HashSet<>();
        Channel channel1 = new Channel(1L, "General", users, true);
        Channel channel2 = new Channel(2L, "Announcements", users, false);

        List<Channel> channelList = new ArrayList<>(Arrays.asList(channel1, channel2));
        given(channelService.findAll()).willReturn(channelList);

        mockMvc.perform(get("/channels"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].channelName", is("General")))
                .andExpect(jsonPath("$[0].private", is(true)))

                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].channelName", is("Announcements")))
                .andExpect(jsonPath("$[1].private", is(false)));
    }


    @Test
    @DisplayName("DELETE /channels/1")
    public void deleteChannelTest() throws Exception {
        mockMvc.perform(delete("/channels/1"))
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("DELETE /channels/deleteAll")
    public void deleteAllChannelTest() throws Exception {
        mockMvc.perform(delete("/channels/deleteAll"))
                .andExpect(status().isAccepted());
    }


    @Test
    @DisplayName("PUT /channels/{id}/changeName - Success")
    void updateChannelNameSuccessTest() throws Exception {
        Channel channel = new Channel(1L, "General", new HashSet<>(), true);
        Channel channel1 = new Channel(1L, "Updated", new HashSet<>(), true);
        String newName = "Updated";
        given(channelService.changeChannelName(channel.getId(), newName)).willReturn(Optional.of(channel1));

        mockMvc.perform(put("/channels/{id}/changeName", channel.getId())
                .header(HttpHeaders.IF_MATCH, 1)
                .param("channelName", newName))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(jsonPath("$.channelName", is("Updated")));
    }

    public static String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}

