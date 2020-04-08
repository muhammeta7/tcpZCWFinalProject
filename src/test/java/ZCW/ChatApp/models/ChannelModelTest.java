package ZCW.ChatApp.models;
import org.junit.Assert;
import org.junit.Test;


import java.util.ArrayList;
import java.util.HashSet;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        Set<User> users = new HashSet<>();
        users.add(new User());
        users.add(new User());
        Channel channel = new Channel();

        // When
        channel.setUsers(users);


        // Then
        Assert.assertEquals(users,channel.getUsers());
    }

    @Test
    public void getAndSetMessagesTest() {
        // Given
        List<Message> messages = new ArrayList<>();
        messages.add(new Message());
        messages.add(new Message());
    }

    @Test
    public void channelPrivacyTest(){
        Channel channel = new Channel();
        channel.setPrivate(true);
        Boolean actual = channel.getPrivate();
        Assert.assertTrue(actual);
    }

}
