package ZCW.ChatApp.controllers;

import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.User;
import ZCW.ChatApp.services.ChannelService;
import ZCW.ChatApp.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.HttpHeaders;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.times;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ChannelService channelService;

    @Test
    @DisplayName("POST /user - Success")
    public void createUserTest() throws Exception {
        User postUser = new User(1L,"Moe", "Aydin", "muhammeta7", "password", false);
        User mockUser = new User(1L,"Moe", "Aydin", "muhammeta7", "password", false);
        given(userService.create(postUser)).willReturn(mockUser);
        mockMvc.perform(
                     post("/users/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(postUser))
                )

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header().string(HttpHeaders.LOCATION, "/create/1"))

                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Moe")))
                .andExpect(jsonPath("$.lastName", is("Aydin")))
                .andExpect(jsonPath("$.userName", is("muhammeta7")))
                .andExpect(jsonPath("$.connected", is(false)))
                .andExpect(jsonPath("$.password", is("password")));
    }

    // TODO Test create user failing

    // GET
    //===================================================================================================================================

    @Test
    @DisplayName("GET /user/1 - Success")
    public void findUserByIDFoundTest() throws Exception {
        Long givenId = 1L;
        User getUser = new User(1L,"Moe", "Aydin", "muhammeta7", "password", false);
        given(userService.findById(givenId)).willReturn(Optional.of(getUser));

        mockMvc.perform(get("/users/{id}", givenId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Moe")))
                .andExpect(jsonPath("$.lastName", is("Aydin")))
                .andExpect(jsonPath("$.userName", is("muhammeta7")))
                .andExpect(jsonPath("$.connected", is(false)))
                .andExpect(jsonPath("$.password", is("password")));
    }

    @Test
    @DisplayName("GET /users/1 - Not Found")
    void findByIdNotFoundTest() throws Exception {
        given(userService.findById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /users/username/muhammeta7 - Success")
    public void findUserByUserNameTest() throws Exception {
        String givenName = "muhammeta7";
        User getUser = new User(1L,"Moe", "Aydin", "muhammeta7", "password", false);
        given(userService.findUserByUsername(givenName)).willReturn(Optional.of(getUser));

        mockMvc.perform(get("/users/username/{username}", givenName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Moe")))
                .andExpect(jsonPath("$.lastName", is("Aydin")))
                .andExpect(jsonPath("$.userName", is("muhammeta7")))
                .andExpect(jsonPath("$.connected", is(false)))
                .andExpect(jsonPath("$.password", is("password")));
    }

    @Test
    @DisplayName("GET /users")
    public void findAllUsersTest() throws Exception {
        User user1 = new User(1L,"Moe", "Aydin", "muhammeta7", "password", false);
        User user2 = new User(2L,"Moe", "Aydin", "juju7", "password", false);

        List<User> userList = new ArrayList<>(Arrays.asList(user1,user2));
        given(userService.findAll()).willReturn(userList);

        mockMvc.perform(get("/users"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("Moe")))
                .andExpect(jsonPath("$[0].lastName", is("Aydin")))
                .andExpect(jsonPath("$[0].userName", is("muhammeta7")))
                .andExpect(jsonPath("$[0].connected", is(false)))
                .andExpect(jsonPath("$[0].password", is("password")))

                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("Moe")))
                .andExpect(jsonPath("$[1].lastName", is("Aydin")))
                .andExpect(jsonPath("$[1].userName", is("juju7")))
                .andExpect(jsonPath("$[1].connected", is(false)))
                .andExpect(jsonPath("$[1].password", is("password")));
    }

    @Test
    @DisplayName("GET /users/channel/{channelId}")
    public void findAllUsersByChannelTest() throws Exception {
        Long channelId = 1L;
        User user1 = new User(1L,"Moe", "Aydin", "muhammeta7", "password", false);
        User user2 = new User(2L,"Moe", "Aydin", "juju7", "password", false);

        List<User> userList = new ArrayList<>(Arrays.asList(user1,user2));
        given(userService.findUsersByChannel(channelId)).willReturn(userList);

        mockMvc.perform(get("/users/channel/{channelId}", channelId))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("Moe")))
                .andExpect(jsonPath("$[0].lastName", is("Aydin")))
                .andExpect(jsonPath("$[0].userName", is("muhammeta7")))
                .andExpect(jsonPath("$[0].connected", is(false)))
                .andExpect(jsonPath("$[0].password", is("password")))

                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("Moe")))
                .andExpect(jsonPath("$[1].lastName", is("Aydin")))
                .andExpect(jsonPath("$[1].userName", is("juju7")))
                .andExpect(jsonPath("$[1].connected", is(false)))
                .andExpect(jsonPath("$[1].password", is("password")));
    }

    // PUT
    //===================================================================================================================================

    @Test
    @DisplayName("PUT /users/1/connect - Success")
    public void connectTest() throws Exception {
        Long givenId = 1L;
        User putUser = new User(1L,"Moe", "Aydin", "muhammeta7", "password", false);
        User mockUser = new User(1L,"Moe", "Aydin", "muhammeta7", "password", true);

        given(userService.updateConnection(putUser.getId())).willReturn(mockUser);

        mockMvc.perform(put("/users/{id}/connect", givenId)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH, 1)
                .content(asJsonString(putUser)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(jsonPath("$.connected", is(true)));
    }

    @Test
    @DisplayName("PUT /users/1/disconnect - Success")
    public void disconnectTest() throws Exception {
        Long givenId = 1L;
        User putUser = new User(1L,"Moe", "Aydin", "muhammeta7", "password", true);
        User mockUser = new User(1L,"Moe", "Aydin", "muhammeta7", "password", false);

        given(userService.updateConnection(putUser.getId())).willReturn(mockUser);

        mockMvc.perform(put("/users/{id}/disconnect", givenId)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH, 1)
                .content(asJsonString(putUser)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(jsonPath("$.connected", is(false)));
    }

    @Test
    @DisplayName("PUT /users/update/username/1 - Success")
    void updateUserNameSuccessTest() throws Exception {
        Long givenId = 1L;
        User putUser = new User(1L,"Moe", "Aydin", "muhammeta7", "password", true);
        String newUsername = "anything";
        given(userService.updateUserName(putUser.getId(), newUsername)).willReturn(Optional.of(putUser));

        mockMvc.perform(put("/users/update/username/{id}", givenId)
                .header(HttpHeaders.IF_MATCH, 1)
                .param("username", newUsername))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(jsonPath("$.userName", is("muhammeta7")));
    }

    @Test
    @DisplayName("PUT /users/update/username/1 - Fail")
    void updateUserNameFailTest() throws Exception {
        Long givenId = 1L;
        String newUsername = "anything";
        given(userService.updateUserName(givenId, newUsername)).willReturn(Optional.empty());

        mockMvc.perform(put("/users/update/username/{id}", givenId)
                .header(HttpHeaders.IF_MATCH, 1)
                .param("username", newUsername))

                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /users/update/password/1 - Success")
    public void updatePasswordSuccessTest() throws Exception {
        Long givenId = 1L;
        User putUser = new User(1L,"Moe", "Aydin", "muhammeta7", "password", true);
        String newPassword = "anything";
        given(userService.updatePassword(putUser.getId(), newPassword)).willReturn(Optional.of(putUser));

        mockMvc.perform(put("/users/update/password/{id}", givenId)
                .header(HttpHeaders.IF_MATCH, 1)
                .param("password", newPassword))
                .andExpect(status().isOk())

                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.password", is("password")));
    }

    @Test
    @DisplayName("PUT /users/update/password/1 - Fail")
    void updatePasswordFailTest() throws Exception {
        Long givenId = 1L;
        String newPassword = "anything";
        given(userService.updatePassword(givenId, newPassword)).willReturn(Optional.empty());

        mockMvc.perform(put("/users/update/password/{id}", givenId)
                .header(HttpHeaders.IF_MATCH, 1)
                .param("password", newPassword))

                .andExpect(status().isNotFound());
    }

    @Test
    public void joinChannelSuccessTest() throws Exception{
        Long id = 1L;
        User putUser = new User(1L,"Moe", "Aydin", "muhammeta7", "password", true);
        Channel mockChannel = new Channel(1L,"Labs", new HashSet<>(), false);
        given(userService.findById(id)).willReturn(Optional.of(putUser));
        given(channelService.findById(mockChannel.getId())).willReturn(Optional.of(mockChannel));
        given(userService.joinChannelById(putUser.getId(), mockChannel.getId())).willReturn(Optional.of(putUser));
        String param ="1";

        mockMvc.perform(put("/users/{id}/join", id)
                .header(HttpHeaders.IF_MATCH, 1)
                .param("channelId", param))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void joinChannelFailTest() throws Exception{
        Long id = 1L;
        User putUser = new User(1L,"Moe", "Aydin", "muhammeta7", "password", true);
        Channel mockChannel = new Channel(1L,"Labs", new HashSet<>(), true);
        given(userService.findById(id)).willReturn(Optional.of(putUser));
        given(channelService.findById(mockChannel.getId())).willReturn(Optional.of(mockChannel));
        given(userService.joinChannelById(putUser.getId(), mockChannel.getId())).willReturn(Optional.empty());
        String param ="1";

        mockMvc.perform(put("/users/{id}/join", id)
                .header(HttpHeaders.IF_MATCH, 1)
                .param("channelId", param))

                .andExpect(status().isNotFound());

    }

    @Test
    public void leaveChannelSuccessTest() throws Exception{
        Long id = 1L;
        User putUser = new User(1L,"Moe", "Aydin", "muhammeta7", "password", true);
        Channel mockChannel = new Channel(1L,"Labs", new HashSet<>(), false);
        given(userService.findById(id)).willReturn(Optional.of(putUser));
        given(channelService.findById(mockChannel.getId())).willReturn(Optional.of(mockChannel));
        given(userService.leaveChannelById(putUser.getId(), mockChannel.getId())).willReturn(Optional.of(putUser));
        String param ="1";

        mockMvc.perform(put("/users/{id}/leave", id)
                .header(HttpHeaders.IF_MATCH, 1)
                .param("channelId", param))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void leaveChannelFailTest() throws Exception{
        Long id = 1L;
        User putUser = new User(1L,"Moe", "Aydin", "muhammeta7", "password", true);
        Channel mockChannel = new Channel(1L,"Labs", new HashSet<>(), false);
        given(userService.findById(id)).willReturn(Optional.of(putUser));
        given(channelService.findById(mockChannel.getId())).willReturn(Optional.of(mockChannel));
        given(userService.leaveChannelById(putUser.getId(), mockChannel.getId())).willReturn(Optional.empty());
        String param ="1";

        mockMvc.perform(put("/users/{id}/leave", id)
                .header(HttpHeaders.IF_MATCH, 1)
                .param("channelId", param))

                .andExpect(status().isNotFound());
    }

    // DELETE
    //===================================================================================================================================

    @Test
    @DisplayName("DELETE /users/delete/1 - Success")
    void deleteUserTest() throws Exception {
        Long givenId = 1L;
        given(userService.deleteUser(givenId)).willReturn(true);

        mockMvc.perform(delete("/users/delete/{id}", givenId))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /users/delete/1 - Not Found")
    void deleteUserNotFoundTest() throws Exception {
        Long givenId = 1L;
        given(userService.deleteUser(givenId)).willReturn(false);

        mockMvc.perform(delete("/users/delete/{id}", givenId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /users/deleteAll Success")
    void deleteAllTest() throws Exception {
        User user1 = new User(1L,"Moe", "Aydin", "muhammeta7", "password", false);
        User user2 = new User(2L,"Moe", "Aydin", "juju7", "password", false);
        given(userService.create(user1)).willReturn(user1);
        given(userService.create(user2)).willReturn(user2);
        given(userService.deleteAll()).willReturn(true);

        mockMvc.perform(delete("/users/deleteAll"))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteAll();
    }

    @Test
    @DisplayName("DELETE /users/deleteAll False")
    void deleteAllFalseTest() throws Exception {
        given(userService.deleteAll()).willReturn(false);

        mockMvc.perform(delete("/users/deleteAll"))
                .andExpect(status().isNotFound());

    }

    static String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
