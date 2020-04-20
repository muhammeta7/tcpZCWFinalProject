package ZCW.ChatApp.controllers;

import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.Message;
import ZCW.ChatApp.models.DAOUser;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class ChannelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChannelService channelService;

    @MockBean
    private UserService userService;

    @WithMockUser(username = "muhammeta7")
    @Test
    @DisplayName("POST /channel")
    public void createChannelTest() throws Exception{
        HashSet<DAOUser> users = new HashSet<>();
        DAOUser mockUser = new DAOUser(1L, "First Name", "Last Name", "User Name", "Password", true);
        doReturn(mockUser).when(userService).getUser(any());
        mockMvc.perform(MockMvcRequestBuilders
                .post("/channels/create/user/1")
                .content(asJsonString(new Channel(1L,"General", users, true)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());
    }
    @WithMockUser(username = "muhammeta7")
    @Test
    @DisplayName("GET /channels/1")
    public void findChannelByIdTest() throws Exception{
        Long givenId = 1L;
        HashSet<DAOUser> users = new HashSet<>();
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
    @WithMockUser(username = "muhammeta7")
    @Test
    @DisplayName("GET /channels/chat/{id}")
    public void findAllMessagesTest() throws Exception {
        HashSet<DAOUser> users = new HashSet<>();
        Channel mockChannel = new Channel(1L, "General", users, true);
        Message mockMessage1 = new Message(new DAOUser(), "Hello", new Date(), mockChannel);
        Message mockMessage2 = new Message(new DAOUser(), "Hi", new Date(), mockChannel);
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

    @WithMockUser(username = "muhammeta7")
    @Test
    @DisplayName("GET /channels")
    public void findAllChannelsTest() throws Exception {
        HashSet<DAOUser> users = new HashSet<>();
        Channel channel1 = new Channel(1L, "General", users, true);
        Channel channel2 = new Channel(2L, "Announcements", users, false);

        List<Channel> channelList = new ArrayList<>(Arrays.asList(channel1, channel2));
        given(channelService.findAll()).willReturn(channelList);

        mockMvc.perform(get("/channels"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].channelName", is("General")))
                .andExpect(jsonPath("$[0].private", is(true)))

                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].channelName", is("Announcements")))
                .andExpect(jsonPath("$[1].private", is(false)));
    }

    @WithMockUser(username = "muhammeta7")
    @Test
    @DisplayName("GET /{channelName}/users")
    public void findAllUsersForChannelTest() throws Exception {
        DAOUser mockUser = new DAOUser(1L, "Bob", "Dole", "Lame", "password", true);
        DAOUser mockUser2 =new DAOUser(2L, "Chris", "Farmer", "farmerc", "password", true);
        HashSet<DAOUser> users = new HashSet<>(Arrays.asList(mockUser, mockUser2));
        Channel mockChannel = new Channel(1L, "Test", users, true);
        given(channelService.findByChannelName("Test")).willReturn(Optional.of(mockChannel));

        mockMvc.perform(get("/channels/Test/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @WithMockUser(username = "muhammeta7")
    @Test
    @DisplayName("GET /public")
    public void findAllPublicChannelsTest() throws Exception {
        HashSet<DAOUser> users = new HashSet<>();
        Channel mockPublicChannel1 = new Channel(1L, "Public Test 1", users, false);
        Channel mockPublicChannel2 = new Channel(2L, "Public Test 2", users, false);
        given(channelService.getAllPublicChannels()).willReturn(Arrays.asList(mockPublicChannel1, mockPublicChannel2));

        mockMvc.perform(get("/channels/public"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].channelName", is("Public Test 1")))
                .andExpect(jsonPath("$[0].private", is(false)))

                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].channelName", is("Public Test 2")))
                .andExpect(jsonPath("$[1].private", is(false)));
    }

    @WithMockUser(username = "muhammeta7")
    @Test
    @DisplayName("DELETE /channels/1")
    public void deleteChannelTest() throws Exception {
        mockMvc.perform(delete("/channels/1"))
                .andExpect(status().isAccepted());
    }
    @WithMockUser(username = "muhammeta7")
    @Test
    @DisplayName("DELETE /channels/deleteAll")
    public void deleteAllChannelTest() throws Exception {
        mockMvc.perform(delete("/channels/deleteAll"))
                .andExpect(status().isAccepted());
    }

    @WithMockUser(username = "muhammeta7")
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

    @WithMockUser(username = "muhammeta7")
    @Test
    @DisplayName("PUT /channels/{id}/changePrivacy")
    public void updateChannelPrivacyTest() throws Exception {
        Channel channel = new Channel(1L, "General", new HashSet<>(), true);
        Channel updatedChannel = new Channel(1L, "General", new HashSet<>(), false);
        given(channelService.changeChannelPrivacy(channel.getId())).willReturn(Optional.of(updatedChannel));

        mockMvc.perform(put("/channels/{id}/changePrivacy", updatedChannel.getId())
                .header(HttpHeaders.IF_MATCH, 1))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(jsonPath("$.private", is(false)));
    }

    public static String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
