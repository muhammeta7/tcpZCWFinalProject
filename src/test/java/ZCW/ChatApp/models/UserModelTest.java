package ZCW.ChatApp.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UserModelTest {
    private User user;

    @Before
    public void setup(){
        this.user = new User();
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
        Assert.assertTrue(user.isConnected());
    }

    @Test
    public void getPasswordTest(){
        String expected = "password";
        user.setPassword("password");
        String actual = user.getPassword();
        Assert.assertEquals(expected, actual);

    }

    // TODO Test set Messages and Channels

}
