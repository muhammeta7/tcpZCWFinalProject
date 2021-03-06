package ZCW.ChatApp.controllers;

import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.DAOUser;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.HttpHeaders;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.times;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class DAOUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ChannelService channelService;

    // GET
    //===================================================================================================================================
    @WithMockUser(username = "muhammeta7")
    @Test
    @DisplayName("GET /user/1 - Success")
    public void findUserByIDFoundTest() throws Exception {
        Long givenId = 1L;
        DAOUser getUser = new DAOUser(1L,"Moe", "Aydin", "muhammeta7", "password", false);
        given(userService.findById(givenId)).willReturn(Optional.of(getUser));

        mockMvc.perform(get("/users/{id}", givenId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Moe")))
                .andExpect(jsonPath("$.lastName", is("Aydin")))
                .andExpect(jsonPath("$.userName", is("muhammeta7")))
                .andExpect(jsonPath("$.connected", is(false)));
    }
    @WithMockUser(username = "muhammeta7")
    @Test
    @DisplayName("GET /users/1 - Not Found")
    void findByIdNotFoundTest() throws Exception {
        given(userService.findById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isNotFound());
    }
    @WithMockUser(username = "muhammeta7")
    @Test
    @DisplayName("GET /users/username/muhammeta7 - Success")
    public void findUserByUserNameTest() throws Exception {
        String givenName = "muhammeta7";
        DAOUser getUser = new DAOUser(1L,"Moe", "Aydin", "muhammeta7", "password", false);
        given(userService.findUserByUsername(givenName)).willReturn(Optional.of(getUser));

        mockMvc.perform(get("/users/username/{username}", givenName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Moe")))
                .andExpect(jsonPath("$.lastName", is("Aydin")))
                .andExpect(jsonPath("$.userName", is("muhammeta7")))
                .andExpect(jsonPath("$.connected", is(false)));
    }
    @WithMockUser(username = "muhammeta7")
    @Test
    @DisplayName("GET /users")
    public void findAllUsersTest() throws Exception {
        DAOUser user1 = new DAOUser(1L,"Moe", "Aydin", "muhammeta7", "password", false);
        DAOUser user2 = new DAOUser(2L,"Moe", "Aydin", "juju7", "password", false);

        List<DAOUser> userList = new ArrayList<>(Arrays.asList(user1,user2));
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

                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("Moe")))
                .andExpect(jsonPath("$[1].lastName", is("Aydin")))
                .andExpect(jsonPath("$[1].userName", is("juju7")))
                .andExpect(jsonPath("$[1].connected", is(false)));
    }
    @WithMockUser(username = "muhammeta7")
    @Test
    @DisplayName("GET /users/channel/{channelId}")
    public void findAllUsersByChannelTest() throws Exception {
        Long channelId = 1L;
        DAOUser user1 = new DAOUser(1L,"Moe", "Aydin", "muhammeta7", "password", false);
        DAOUser user2 = new DAOUser(2L,"Moe", "Aydin", "juju7", "password", false);

        List<DAOUser> userList = new ArrayList<>(Arrays.asList(user1,user2));
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

                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("Moe")))
                .andExpect(jsonPath("$[1].lastName", is("Aydin")))
                .andExpect(jsonPath("$[1].userName", is("juju7")))
                .andExpect(jsonPath("$[1].connected", is(false)));
    }

    @WithMockUser(username = "muhammeta7")
    @Test
    @DisplayName("GET /users/channels/{userName}")
    public void getAllUserChannelsTest() throws Exception {
        DAOUser user1 = new DAOUser(1L,"Moe", "Aydin", "muhammeta7", "password", false);
        Channel mockChannel1 = new Channel(1L, "Test Channel Name 1", new HashSet<>(), false, false);
        given(userService.findAllChannelsByUser("muhammeta7")).willReturn(new HashSet<>(Collections.singletonList(mockChannel1)));

        mockMvc.perform(get("/users/channels/{userName}", user1.getUserName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].channelName", is("Test Channel Name 1")))
                .andExpect(jsonPath("$[0].isPrivate", is(false)))
                .andExpect(jsonPath("$[0].isDm", is(false)));
    }

    @WithMockUser(username = "muhammeta7")
    @Test
    @DisplayName("GET /users/dms/{userName}")
    public void getAllUserDmsTest() throws Exception {
        DAOUser mockUser = new DAOUser(1L, "Chris", "Farmer", "farmerc", "password", true);
        Channel mockDm = new Channel(2L, "Test 2", new HashSet<>(), true, true);
        given(userService.findAllDmsByUser("farmerc")).willReturn(new HashSet<>(Collections.singletonList(mockDm)));

        mockMvc.perform(get("/users/dms/{userName}", mockUser.getUserName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].channelName", is("Test 2")))
                .andExpect(jsonPath("$[0].isPrivate", is(true)))
                .andExpect(jsonPath("$[0].isDm", is(true)));
    }

    // PUT
    //===================================================================================================================================
    @WithMockUser(username = "muhammeta7")
    @Test
    @DisplayName("PUT /users/1/connect - Success")
    public void connectTest() throws Exception {
        Long givenId = 1L;
        DAOUser putUser = new DAOUser(1L,"Moe", "Aydin", "muhammeta7", "password", false);
        DAOUser mockUser = new DAOUser(1L,"Moe", "Aydin", "muhammeta7", "password", true);

        given(userService.updateConnection(putUser.getId())).willReturn(mockUser);

        mockMvc.perform(put("/users/{id}/connect", givenId)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH, 1)
                .content(asJsonString(putUser)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(jsonPath("$.connected", is(true)));
    }
    @WithMockUser(username = "muhammeta7")
    @Test
    @DisplayName("PUT /users/1/disconnect - Success")
    public void disconnectTest() throws Exception {
        Long givenId = 1L;
        DAOUser putUser = new DAOUser(1L,"Moe", "Aydin", "muhammeta7", "password", true);
        DAOUser mockUser = new DAOUser(1L,"Moe", "Aydin", "muhammeta7", "password", false);

        given(userService.updateConnection(putUser.getId())).willReturn(mockUser);

        mockMvc.perform(put("/users/{id}/disconnect", givenId)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH, 1)
                .content(asJsonString(putUser)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(jsonPath("$.connected", is(false)));
    }
    @WithMockUser(username = "muhammeta7")
    @Test
    @DisplayName("PUT /users/update/username/1 - Success")
    void updateUserNameSuccessTest() throws Exception {
        Long givenId = 1L;
        DAOUser putUser = new DAOUser(1L,"Moe", "Aydin", "muhammeta7", "password", true);
        String newUsername = "anything";
        given(userService.updateUserName(putUser.getId(), newUsername)).willReturn(Optional.of(putUser));

        mockMvc.perform(put("/users/update/username/{id}", givenId)
                .header(HttpHeaders.IF_MATCH, 1)
                .param("username", newUsername))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(jsonPath("$.userName", is("muhammeta7")));
    }
    @WithMockUser(username = "muhammeta7")
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
    @WithMockUser(username = "muhammeta7")
    @Test
    @DisplayName("PUT /users/update/password/1 - Success")
    public void updatePasswordSuccessTest() throws Exception {
        Long givenId = 1L;
        DAOUser putUser = new DAOUser(1L,"Moe", "Aydin", "muhammeta7", "password", true);
        String newPassword = "anything";
        given(userService.updatePassword(putUser.getId(), newPassword)).willReturn(Optional.of(putUser));

        mockMvc.perform(put("/users/update/password/{id}", givenId)
                .header(HttpHeaders.IF_MATCH, 1)
                .param("password", newPassword))
                .andExpect(status().isOk())

                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }
    @WithMockUser(username = "muhammeta7")
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
    @WithMockUser(username = "muhammeta7")
    @Test
    public void joinChannelSuccessTest() throws Exception{
        Long id = 1L;
        DAOUser putUser = new DAOUser(1L,"Moe", "Aydin", "muhammeta7", "password", true);
        Channel mockChannel = new Channel(1L,"Labs", new HashSet<>(), false, false);
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
    @WithMockUser(username = "muhammeta7")
    @Test
    public void joinChannelFailTest() throws Exception{
        Long id = 1L;
        DAOUser putUser = new DAOUser(1L,"Moe", "Aydin", "muhammeta7", "password", true);
        Channel mockChannel = new Channel(1L,"Labs", new HashSet<>(), true, false);
        given(userService.findById(id)).willReturn(Optional.of(putUser));
        given(channelService.findById(mockChannel.getId())).willReturn(Optional.of(mockChannel));
        given(userService.joinChannelById(putUser.getId(), mockChannel.getId())).willReturn(Optional.empty());
        String param ="1";

        mockMvc.perform(put("/users/{id}/join", id)
                .header(HttpHeaders.IF_MATCH, 1)
                .param("channelId", param))

                .andExpect(status().isNotFound());

    }
    @WithMockUser(username = "muhammeta7")
    @Test
    public void leaveChannelSuccessTest() throws Exception{
        Long id = 1L;
        DAOUser putUser = new DAOUser(1L,"Moe", "Aydin", "muhammeta7", "password", true);
        Channel mockChannel = new Channel(1L,"Labs", new HashSet<>(), false, false);
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
    @WithMockUser(username = "muhammeta7")
    @Test
    public void leaveChannelFailTest() throws Exception{
        Long id = 1L;
        DAOUser putUser = new DAOUser(1L,"Moe", "Aydin", "muhammeta7", "password", true);
        Channel mockChannel = new Channel(1L,"Labs", new HashSet<>(), false, false);
        given(userService.findById(id)).willReturn(Optional.of(putUser));
        given(channelService.findById(mockChannel.getId())).willReturn(Optional.of(mockChannel));
        given(userService.leaveChannelById(putUser.getId(), mockChannel.getId())).willReturn(Optional.empty());
        String param ="1";

        mockMvc.perform(put("/users/{id}/leave", id)
                .header(HttpHeaders.IF_MATCH, 1)
                .param("channelId", param))

                .andExpect(status().isNotFound());
    }

    @WithMockUser(username = "muhammeta7")
    @Test
    public void inviteToChannelTest() throws Exception {
        DAOUser mockUser = new DAOUser(1L, "Moe", "Aydin", "muhammeta7", "password", true);
        DAOUser invitedUser = new DAOUser(2L, "Chris", "Farmer", "Farmerc92", "password", true);
        Channel channel = new Channel(1L, "Test", new HashSet<>(Collections.singletonList(mockUser)),  true, false);
        invitedUser.setChannels(new HashSet<>(Collections.singletonList(channel)));
        given(userService.inviteToChannel("muhammeta7", "Test", "Farmerc92")).willReturn(Optional.of(invitedUser));

        mockMvc.perform(put("/users/{userName}/{channelName}/invite/{inviteUserName}", mockUser.getUserName(), channel.getChannelName(), invitedUser.getUserName())
                .header(HttpHeaders.IF_MATCH, "muhammeta7", "Test", "Farmerc92"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.firstName", is("Chris")))
                .andExpect(jsonPath("$.lastName", is("Farmer")))
                .andExpect(jsonPath("$.userName", is("Farmerc92")));
    }

    // DELETE
    //===================================================================================================================================
    @WithMockUser(username = "muhammeta7")
    @Test
    @DisplayName("DELETE /users/delete/1 - Success")
    void deleteUserTest() throws Exception {
        Long givenId = 1L;
        given(userService.deleteUser(givenId)).willReturn(true);

        mockMvc.perform(delete("/users/delete/{id}", givenId))
                .andExpect(status().isOk());
    }
    @WithMockUser(username = "muhammeta7")
    @Test
    @DisplayName("DELETE /users/delete/1 - Not Found")
    void deleteUserNotFoundTest() throws Exception {
        Long givenId = 1L;
        given(userService.deleteUser(givenId)).willReturn(false);

        mockMvc.perform(delete("/users/delete/{id}", givenId))
                .andExpect(status().isNotFound());
    }
    @WithMockUser(username = "muhammeta7")
    @Test
    @DisplayName("DELETE /users/deleteAll Success")
    void deleteAllTest() throws Exception {
        DAOUser user1 = new DAOUser(1L,"Moe", "Aydin", "muhammeta7", "password", false);
        DAOUser user2 = new DAOUser(2L,"Moe", "Aydin", "juju7", "password", false);
        given(userService.create(user1)).willReturn(user1);
        given(userService.create(user2)).willReturn(user2);
        given(userService.deleteAll()).willReturn(true);

        mockMvc.perform(delete("/users/deleteAll"))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteAll();
    }
    @WithMockUser(username = "muhammeta7")
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
