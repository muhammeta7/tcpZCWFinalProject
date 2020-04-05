package ZCW.ChatApp.models;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Date;

public class MessageModelTest {

    private Message message;
    private Date date;

    @Before
    public void setup(){
        this.message = new Message();
        this.date = new Date();
    }

    @Test
    public void getMessageTest(){
        Long expected = 1L;
        message.setId(1L);
        assertEquals(expected, message.getId());
    }

    @Test
    public void getSenderTest(){
        User user = new User();
        user.setUserName("John");
        String expected = "John";
        assertEquals(expected, user.getUserName());
    }

    @Test
    public void getContentTest(){
        String expected = "We TDD out here son";
        message.setContent(expected);
        assertEquals(expected, message.getContent());
    }



}
