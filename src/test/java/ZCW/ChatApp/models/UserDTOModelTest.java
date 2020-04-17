package ZCW.ChatApp.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserDTOModelTest {

    private UserDTO userDTO;

    @Before
    public void setup(){
        this.userDTO = new UserDTO();
    }

    @Test
    public void getFirstNameTest(){
        // given
        String expected = "Moe";
        userDTO.setFirstName(expected);

        // when
        String actual = userDTO.getFirstName();

        // then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getLastNameTest(){
        // given
        String expected = "Aydin";
        userDTO.setLastName(expected);

        // when
        String actual = userDTO.getLastName();

        // then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getUserNameTest(){
        // given
        String expected = "CoolBeans123";
        userDTO.setUserName(expected);

        // when
        String actual = userDTO.getUserName();

        // then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getPasswordTest(){
        // given
        String expected = "password";
        userDTO.setPassword(expected);

        // when
        String actual = userDTO.getPassword();

        // then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void toStringTest(){
        // given
        String expectedPassword = "password";
        String expectedUserName = "CoolBeans123";
        String expectedFirstName = "Moe";
        String expectedLastName = "Aydin";
        userDTO.setPassword(expectedPassword);
        userDTO.setUserName(expectedUserName);
        userDTO.setFirstName(expectedFirstName);
        userDTO.setLastName(expectedLastName);
        String expected = "UserDTO{" +
                "userName='" + expectedUserName + '\'' +
                ", password='" + expectedPassword + '\'' +
                ", firstName='" + expectedFirstName + '\'' +
                ", lastName='" + expectedLastName + '\'' +
                '}';

        // when
        String actual = userDTO.toString();

        //then
        Assert.assertEquals(expected, actual);
    }
}
