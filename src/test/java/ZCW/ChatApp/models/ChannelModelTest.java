package ZCW.ChatApp.models;
import org.junit.Assert;
import org.junit.Test;


import java.util.ArrayList;
import java.util.HashSet;

import java.util.List;
import java.util.Set;

public class ChannelModelTest {

    @Test
    public void setAndGetIdTest() {
        // Given
        Long expected = 10L;
        Channel channel = new Channel();

        // When
        channel.setId(expected);
        Long actual = channel.getId();

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void setAndGetNameTest() {
        // Given
        String expected = "expected";
        Channel channel = new Channel();


        // When
        channel.setChannelName(expected);
        String actual = channel.getChannelName();

        // Then
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void getAndSetUsersTest(){
        // Given
        Set<DAOUser> users = new HashSet<>();
        users.add(new DAOUser());
        users.add(new DAOUser());
        Channel channel = new Channel();

        // When
        channel.setUsers(users);


        // Then
        Assert.assertEquals(users,channel.getUsers());
    }

    @Test
    public void getAndSetMessagesTest() {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message());
        messages.add(new Message());
        Channel channel = new Channel();
        channel.setMessages(messages);
        Integer expected = 2;
        Integer actual = channel.getMessages().size();
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void channelPrivacyTest(){
        Channel channel = new Channel();
        channel.setIsPrivate(true);
        Boolean actual = channel.getIsPrivate();
        Assert.assertTrue(actual);
    }

    @Test
    public void channelIsDmTest(){
        Channel channel = new Channel();
        channel.setIsDm(true);
        Boolean actual = channel.getIsDm();
        Assert.assertTrue(actual);
    }

}
