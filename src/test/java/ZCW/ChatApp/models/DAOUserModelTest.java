package ZCW.ChatApp.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DAOUserModelTest {
    private DAOUser user;

    @Before
    public void setup(){
        this.user = new DAOUser();
    }

    @Test
    public void getIdTest() {
        // given
        long expected  = 0l;
        user.setId(expected);

        // when
        long actual = user.getId();

        // then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getFirstNameTest() {
        // given
        String expected = "Sandy";
        user.setFirstName(expected);

        // when
        String actual = user.getFirstName();

        // then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getLastNameTest() {
        // given
        String expected = "Wick";
        user.setLastName(expected);

        // when
        String actual = user.getLastName();

        // then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getUsernameTest() {
        // given
        String expected = "sandy";
        user.setUserName(expected);

        // when
        String actual = user.getUserName();

        // then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getConnectedTest() {
        // given
        user.setConnected(true);

        // when; then
        Assert.assertTrue(user.getConnected());
    }

    @Test
    public void getPasswordTest(){
        String expected = "password";
        user.setPassword("password");
        String actual = user.getPassword();
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void getMessagesTest(){
        List<Message> list = new ArrayList<>();
        list.add(new Message());
        list.add(new Message());

        user.setMessages(list);
        Assert.assertEquals(list.size(), user.getMessages().size());
    }

    @Test
    public void getChannelsTest(){
        HashSet<Channel> channels = new HashSet<>();
        channels.add(new Channel());
        channels.add(new Channel());

        user.setChannels(channels);
        Assert.assertEquals(channels.size(), user.getChannels().size());
    }

}
