
package ZCW.ChatApp.controllers;

import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.Message;
import ZCW.ChatApp.models.DAOUser;
import ZCW.ChatApp.services.ChannelService;
import ZCW.ChatApp.services.MessageService;
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
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @MockBean
    private ChannelService channelService;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("POST /messages/channel/{channelId}/sender/{userId}")
    public void createMessageTest() throws Exception {
        DAOUser mockUser = new DAOUser(1L, "Bob", "Dole", "Lame", "password", true);
        Channel mockChannel = new Channel(1L, "General", new HashSet<>(Collections.singleton(mockUser)), true);
        Message postMessage = new Message(1L, mockUser, "Hello", new Date(), mockChannel);
        Message mockMessage = new Message(1L, mockUser, "Hello", new Date(), mockChannel);
        mockUser.setMessages(Collections.singletonList(mockMessage));
        mockChannel.setMessages(Collections.singletonList(mockMessage));

        given(messageService.create(postMessage, 1L, 1L)).willReturn(mockMessage);
        given(messageService.save(any())).willReturn(mockMessage);
        given(channelService.saveChannel(any())).willReturn(mockChannel);
        given(userService.save(any())).willReturn(mockUser);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/messages/channel/1/sender/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(mockMessage))
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header().string(HttpHeaders.LOCATION, "/channel/1/sender/1/1"))

                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.content", is("Hello")))
                .andExpect(jsonPath("$.channel.id", is(1)))
                .andExpect(jsonPath("$.sender.id", is(1)));
    }

    @Test
    @DisplayName("GET /messages/{id}")
    public void getMessageByIdTest() throws Exception {
        Message mockMessage = new Message(1L, new DAOUser(), "Hello there", new Date(), new Channel());
        given(messageService.findById(1L)).willReturn(Optional.of(mockMessage));
        mockMvc.perform(get("/messages/{id}", 1L))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                        .andExpect(jsonPath("$.id", is(1)))
                        .andExpect(jsonPath("$.content", is("Hello there")));
    }

    @Test
    @DisplayName("GET /messages")
    public void getAllMessagesTest() throws Exception {
        Message mockMessage1 = new Message(1L, new DAOUser(), "Hello there", new Date(), new Channel());
        Message mockMessage2 = new Message(2L, new DAOUser(), "General Kenobi", new Date(), new Channel());
        given(messageService.findAll()).willReturn(Arrays.asList(mockMessage1, mockMessage2));
        mockMvc.perform(get("/messages"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                        .andExpect(jsonPath("$[0].id", is(1)))
                        .andExpect(jsonPath("$[0].content", is("Hello there")))

                        .andExpect(jsonPath("$[1].id", is(2)))
                        .andExpect(jsonPath("$[1].content", is("General Kenobi")));
    }

    @Test
    @DisplayName("GET /messages/sender/{userId}")
    public void getAllMessagesByUser() throws Exception {
        DAOUser mockUser = new DAOUser(1L, "ObiWan", "Kenobi", "JediRule", "Yoda12345", true);
        Message mockMessage1 = new Message(1L, mockUser, "Hello there", new Date(), new Channel());
        Message mockMessage2 = new Message(2L, mockUser, "I hate flying", new Date(), new Channel());
        List<Message> messages = Arrays.asList(mockMessage1, mockMessage2);
        mockUser.setMessages(messages);
        given(messageService.findMessagesByUserId(any())).willReturn(messages);
        mockMvc.perform(get("/messages/sender/{userId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].content", is("Hello there")))

                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].content", is("I hate flying")));
    }

    @Test
    @DisplayName("DELETE /messages/delete/1")
    public void deleteMessageTest() throws Exception {
        given(messageService.delete(1L)).willReturn(true);
        mockMvc.perform(delete("/messages/delete/{id}", 1L))
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("DELETE /messages/deleteAll")
    public void deleteAllMessagesTest() throws Exception {
        given(messageService.deleteAll()).willReturn(true);
        mockMvc.perform(delete("/messages/deleteAll"))
                .andExpect(status().isAccepted());
    }

    public static String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
