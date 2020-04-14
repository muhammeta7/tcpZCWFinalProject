
package ZCW.ChatApp.controllers;

import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.Message;
import ZCW.ChatApp.models.User;
import ZCW.ChatApp.repositories.ChannelRepository;
import ZCW.ChatApp.repositories.MessageRepository;
import ZCW.ChatApp.repositories.UserRepository;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
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
    @DisplayName("POST /messages/{id}/channel/{channelId}")
    public void createMessageTest() throws Exception {
        User mockUser = new User(1L, "Bob", "Dole", "Lame", "password", true);
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



    public static String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
