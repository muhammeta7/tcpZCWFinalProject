package ZCW.ChatApp.models;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

public class MessageModelTest {

    private Message message;

    @Before
    public void setup(){
        this.message = new Message();
    }

    @Test
    public void constructorTest(){
        Message message = new Message(new DAOUser(), "content", new Date(), new Channel());
        assertEquals("content", message.getContent());
    }

    @Test
    public void getMessageTest(){
        Long expected = 1L;
        message.setId(1L);
        assertEquals(expected, message.getId());
    }

    @Test
    public void getSenderTest(){
        DAOUser user = new DAOUser();
        user.setUserName("John");
        message.setSender(user);
        assertEquals("John", message.getSender().getUserName() );
    }

    @Test
    public void getContentTest(){
        String expected = "We TDD out here son";
        message.setContent(expected);
        assertEquals(expected, message.getContent());
    }

    @Test
    public void getTimeStampTest() throws InterruptedException {
        message.setTimestamp(new Date());
        Message newerMessage = new Message();
        TimeUnit.SECONDS.sleep(1);
        newerMessage.setTimestamp(new Date());
        assertTrue(message.getTimestamp().getSeconds() < newerMessage.getTimestamp().getSeconds());
    }

    @Test
    public void getChannelTest(){
        HashSet<DAOUser> users = new HashSet<>();
        users.add(new DAOUser());
        Channel channel = new Channel("test", users, false, false);
        message.setChannel(channel);
        String expected = "test";
        String actual = message.getChannel().getChannelName();
        assertEquals(expected, actual);
    }

}
