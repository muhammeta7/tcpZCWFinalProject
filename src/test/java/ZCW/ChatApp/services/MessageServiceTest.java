package ZCW.ChatApp.services;

import ZCW.ChatApp.models.Message;
import ZCW.ChatApp.models.User;
import ZCW.ChatApp.repositories.MessageRepository;
import ZCW.ChatApp.repositories.UserRepository;
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

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@AutoConfigureMockMvc

public class MessageServiceTest {

//    @Autowired
//    private MessageService service;
//
//    @MockBean
//    private MessageRepository repo;
//
//    @Test
//
//    @DisplayName("Test Create Message")
//
//    public void create() {
//
//        // Set Up mock object and repo
//        Message expectedMessage = new Message("Sam", "Testing", new Date());
//        doReturn(Optional.of(expectedMessage)).when(repo).save(any());
//
//        // Execute Call
//        Optional<Message> returnMessage = service.findById(1L);
//
//        // Check Assertions
//        Assertions.assertTrue(returnMessage.isPresent(), "Testing");
//        Assertions.assertSame(returnMessage.get(), expectedMessage, "Testing");
//    }
//
//    @Test
//    public void findById() {
//        // Set up mock repo
//        doReturn(Optional.empty()).when(repo).findById(1l);
//        // Execute Call
//        Optional<Message> returnUser = service.findById(1L);
//        // Check assertions
//        Assertions.assertFalse(returnUser.isPresent(), "User found it shouldn't be.");
//    }
//
//
//    @Test
//    public void findAll() {
//        // Setup mock objects and repo
//        Message message1 = new Message("Sam", "Testing", new Date());
//        Message message2 = new Message("Jeff", "Testing2", new Date());
//        doReturn(Arrays.asList(message1, message2)).when(repo).findAll();
//        // Execute service call
//        Iterable<Message> returnList = service.findAll();
//        // Check Assertions
//        Assertions.assertEquals(2, returnList.size(), "findAll shoudl return 2 users");
//    }
//
//    @Test
//    public void findBySender() {
//        // Setup mock objects and repo
//        Message message1 = new Message("Sam", "Testing", new Date());
//        Message message2 = new Message("Jeff", "Testing2", new Date());
//        Message message3 = new Message("Tim", "Testing3", new Date());
//
//        //Execute Call
//        List<Message> returnList = service.findBySender();
//
//        //Check Assertions
//        Assertions.assertEquals(2, returnList.size(), "findAll shoudl return 2 users");
//    }
//
//    @Test
//    public void delete() {
//        // Setup mock objects and repo
//        Message message1 = new Message( "Sam", "Testing", new Date());
//    }
//
//    @Test
//    public void deleteAll() {
//        // Setup mock objects and repo
//    }
//}
//=======
//    @DisplayName("Test findbyId Success")
//    public void findByIdSuccessTest(){
//        // Set Up mock object and repo
//        Message mockMessage = new Message("moe", "testing time", new Date());
//        doReturn(Optional.of(mockMessage)).when(repo).findById(1L);
//        // Execute Call
//        Optional<Message> returnMessage = service.findById(1L);
//        // Check Assertions
//        Assertions.assertTrue(returnMessage.isPresent(), "No Message was found");
//        Assertions.assertSame(returnMessage.get(), mockMessage, "Models don't match");
//    }
//
//    @Test
//    @DisplayName("Test findById Fail")
//    public void findByIdFailTest(){
//        doReturn(Optional.empty()).when(repo).findById(1L);
//
//        Optional<Message> returnMessage = service.findById(1L);
//
//        Assertions.assertFalse(returnMessage.isPresent(), "Message was not found");
//    }
//
//    @Test
//    @DisplayName("Test findAll")
//    public void findAllTest(){
//        Message mockMessage1 = new Message("moe", "testing time", new Date());
//        Message mockMessage2 = new Message("joe", "testing time", new Date());
//        doReturn(Arrays.asList(mockMessage1, mockMessage2)).when(repo).findAll();
//
//        List<Message> returnList = service.findAll();
//
//        Assertions.assertEquals(2, returnList.size(), "findAll should return 2 messages");
//    }
//
//    @Test
//    @DisplayName("Test create Message")
//    public void createMessageTest() {
//        Message mockMessage = new Message("moe", "testing time", new Date());
//        doReturn(mockMessage).when(repo).save(any());
//
//        Message returnMessage = service.create(mockMessage);
//
//        Assertions.assertNotNull(returnMessage, "The Message should not be null");
//    }
//

}

