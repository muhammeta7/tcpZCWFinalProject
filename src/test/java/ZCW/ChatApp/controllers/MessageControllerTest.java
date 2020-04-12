package ZCW.ChatApp.controllers;

import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.Message;
import ZCW.ChatApp.models.User;
import ZCW.ChatApp.services.MessageService;
import ZCW.ChatApp.services.UserService;
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

import java.util.Date;

import static ZCW.ChatApp.controllers.UserControllerTest.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @Test
    @DisplayName("POST /message - Success")
    public void createMessageTest() throws Exception {

        Message postMessage = new Message(new User(), "testing time", new Date(), new Channel());
        Message mockMessage = new Message(new User(), "testing time", new Date(), new Channel());
        given(messageService.create(postMessage)).willReturn(mockMessage);
        mockMvc.perform(
                post("/message/create/sender/{userId}/channel/{channelId}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postMessage))
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header().string(HttpHeaders.LOCATION, "/create/1"))

                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Moe")))
                .andExpect(jsonPath("$.lastName", is("Aydin")))
                .andExpect(jsonPath("$.userName", is("muhammeta7")))
                .andExpect(jsonPath("$.connected", is(false)))
                .andExpect(jsonPath("$.password", is("password")));
    }
}
