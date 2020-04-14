package ZCW.ChatApp.controllers;

import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.Message;
import ZCW.ChatApp.models.User;
import ZCW.ChatApp.services.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.HttpHeaders;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.times;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

//    @Test
//    @DisplayName("GET /messages/1 - Success")
//    public void findMessageByIdTest() throws Exception{
//        Channel channel = new Channel();
//        Date date = new Date();
//        Message mockMessage = new Message(new User(), "testing", date, channel);
//        given(messageService.findById(mockMessage.getId())).willReturn(Optional.of(mockMessage));
//
//        mockMvc.perform(get("/messages/{id}", mockMessage.getId()))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//
//                .andExpect(jsonPath("$.content", is("testing")));
//    }

    static String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
