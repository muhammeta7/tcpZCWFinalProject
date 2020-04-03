package ZCW.ChatApp.models;

import org.junit.Assert;
import org.junit.Test;

public class UserModelTest {

    @Test
    public void getIdTest() {
        // given
        User user = new User();
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
        User user = new User();
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
        User user = new User();
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
        User user = new User();
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
        User user = new User();
        user.setConnected(true);

        // when; then
        Assert.assertTrue(user.isConnected());
    }

}
