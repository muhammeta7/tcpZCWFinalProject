package ZCW.ChatApp.services;

import ZCW.ChatApp.models.Message;
import ZCW.ChatApp.models.User;
import ZCW.ChatApp.repositories.UserRepository;
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
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

    @Autowired
    private UserService service;

    @MockBean
    private UserRepository repo;

    @Test
    @DisplayName("Test findbyId Success")
    public void findByIdSuccessTest(){
        // Set Up mock object and repo
        User mockUser = new User("Moe", "Aydin", "muhammeta7", "password", false);
        doReturn(Optional.of(mockUser)).when(repo).findById(mockUser.getId());

        // Execute Call
        Optional<User> returnUser = service.findById(mockUser.getId());

        // Check Assertions
        Assertions.assertTrue(returnUser.isPresent(), "No User was found");
        Assertions.assertSame(returnUser.get(), mockUser, "Models don't match");
    }

    @Test
    @DisplayName("Test findById Fail")
    public void findByIdFailTest(){
        // Set up mock repo
        doReturn(Optional.empty()).when(repo).findById(1L);
        // Execute Call
        Optional<User> returnUser = service.findById(1L);
        // Check assertions
        Assertions.assertFalse(returnUser.isPresent(), "User found it shouldn't be.");
    }

    @Test
    @DisplayName("Test findAll")
    public void findAllUsersTest(){
        // Setup mock objects and repo
        User mockUser1 = new User("Moe", "Aydin", "muhammeta7", "password", false);
        User mockUser2 = new User("Jack", "Black", "jack7", "password2", false);
        doReturn(Arrays.asList(mockUser1, mockUser2)).when(repo).findAll();
        // Execute service call
        List<User> returnList = service.findAll();
        // Check Assertions
        Assertions.assertEquals(2, returnList.size(), "findAll should return 2 users");
    }

    @Test
    @DisplayName("Test findAllByUsername")
    public void findUsersByUserNameTest(){
        User mockUser = new User("Moe", "Aydin", "muhammeta7", "password", false);
        doReturn(Optional.of(mockUser)).when(repo).findByUserName(mockUser.getUserName());

        Optional<User> returnUser = service.findUserByUsername(mockUser.getUserName());

        Assertions.assertTrue(returnUser.isPresent(), "No user was found");
        Assertions.assertSame(returnUser.get(), mockUser, "Models don't match");
    }

    @Test
    @DisplayName("Test Find By Firstname")
    public void findUsersByFirstNameTest(){
        User mockUser = new User("Moe", "Aydin", "muhammeta7", "password", false);
        doReturn(Optional.of(mockUser)).when(repo).findByFirstName(mockUser.getFirstName());

        Optional<User> returnUser = service.findUserByFirstName(mockUser.getFirstName());

        Assertions.assertTrue(returnUser.isPresent(), "No user was found");
        Assertions.assertSame(returnUser.get(), mockUser, "Models don't match");
    }

    @Test
    @DisplayName("Test Find By Lastname")
    public void findUsersByLastNameTest(){
        User mockUser = new User("Moe", "Aydin", "muhammeta7", "password", false);
        doReturn(Optional.of(mockUser)).when(repo).findByLastName(mockUser.getLastName());

        Optional<User> returnUser = service.findUserByLastName(mockUser.getLastName());

        Assertions.assertTrue(returnUser.isPresent(), "No user was found");
        Assertions.assertSame(returnUser.get(), mockUser, "Models don't match");
    }

    @Test
    @DisplayName("Test save User")
    public void saveTest() {
        User mockUser = new User("Moe", "Aydin", "muhammeta7", "password", false);
        doReturn(mockUser).when(repo).save(any());

        User returnUser = service.save(mockUser);

        Assertions.assertNotNull(returnUser, "Saved user should not be null");
    }

    @Test
    @DisplayName("Test create User")
    public void createUserTest() throws Exception {
        User mockUser = new User("Moe", "Aydin", "password", "muhammeta7", false);
        doReturn(mockUser).when(repo).save(any());
        // Execute service call
        User returnUser = service.create(mockUser);
        // Check Assertions
        Assertions.assertNotNull(returnUser, "The User should not be null");
    }

//    @Test
//    public void shouldThrowExceptionWithDuplicateUserNames() throws Exception {
//        User mockUser = new User("Moe", "Aydin", "muhammeta7", "password", false);
//        User mockUser2 = new User("Jack", "Black", "muhammeta7", "password2", false);
//        doReturn(mockUser).when(repo).save(any());
//        doReturn(mockUser2).when(repo).save(any());
//
//        User valid = service.create(mockUser);
//        Assertions.assertThrows(Exception.class, () -> service.create(mockUser2));
//    }

    @Test
    @DisplayName("Test connection")
    public void updateConnectionTest(){
        User mockUser = new User("Moe", "Aydin", "password", "muhammeta7", false);
        doReturn(mockUser).when(repo).save(mockUser);
        doReturn(mockUser).when(repo).getOne(1L);

        Boolean actual = service.updateConnection(1L).isConnected();

        Assertions.assertTrue(actual);
    }

    @Test
    @DisplayName("Test update connection fail")
    public void updateConnectionFalse(){
        User mockUser = new User("Moe", "Aydin", "password", "muhammeta7", false);
        doReturn(mockUser).when(repo).save(mockUser);
        doReturn(mockUser).when(repo).getOne(1L);

        service.updateConnection(1L);
        Boolean actual = service.updateConnection(1L).isConnected();

        Assertions.assertFalse(actual);
    }

    @Test
    @DisplayName("Test delete")
    public void deleteUserTest(){
        User mockUser = new User("Moe", "Aydin", "password", "muhammeta7", false);
        doReturn(mockUser).when(repo).save(mockUser);
        doReturn(mockUser).when(repo).getOne(1L);

        Boolean actual = service.deleteUser(1L);

        Assertions.assertTrue(actual);

    }

    @Test
    @DisplayName("Test delete all")
    public void deleteAllTest(){
        User mockUser1 = new User("Moe", "Aydin", "muhammeta7", "password", false);
        User mockUser2 = new User("Jack", "Black", "jack7", "password2", false);
        doReturn(Arrays.asList(mockUser1, mockUser2)).when(repo).findAll();

        List<User> returnUsers = service.findAll();
        Boolean actual = service.deleteAll();

        Assertions.assertTrue(actual);
    }

}
