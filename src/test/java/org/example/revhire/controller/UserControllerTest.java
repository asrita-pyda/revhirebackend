package org.example.revhire.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.revhire.dto.response.UserResponse;
import org.example.revhire.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private org.example.revhire.config.JwtUtils jwtUtils;

    @MockBean
    private org.example.revhire.repository.UserRepository userRepository;

    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getUserProfile_Success() throws Exception {
        when(userService.getUserProfile(1L)).thenReturn(new UserResponse());
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    void updateUserProfile_Success() throws Exception {
        UserResponse req = new UserResponse();
        when(userService.updateUserProfile(eq(1L), any())).thenReturn(new UserResponse());
        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void changePassword_Success() throws Exception {
        mockMvc.perform(put("/api/users/1/change-password")
                        .param("oldPassword", "old")
                        .param("newPassword", "new"))
                .andExpect(status().isOk());
    }

    @Test
    void activateAccount_Success() throws Exception {
        mockMvc.perform(put("/api/users/1/activate"))
                .andExpect(status().isOk());
    }

    @Test
    void deactivateAccount_Success() throws Exception {
        mockMvc.perform(put("/api/users/1/deactivate"))
                .andExpect(status().isOk());
    }

    @Test
    void getUsersByRole_Success() throws Exception {
        when(userService.getUsersByRole(any())).thenReturn(List.of(new UserResponse()));
        mockMvc.perform(get("/api/users/role/EMPLOYER"))
                .andExpect(status().isOk());
    }

    @Test
    void searchUsers_Success() throws Exception {
        when(userService.searchUsers(anyString())).thenReturn(List.of(new UserResponse()));
        mockMvc.perform(get("/api/users/search").param("query", "test"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllEmployers_Success() throws Exception {
        when(userService.getAllEmployers()).thenReturn(List.of(new UserResponse()));
        mockMvc.perform(get("/api/users/employers"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllSeekers_Success() throws Exception {
        when(userService.getAllSeekers()).thenReturn(List.of(new UserResponse()));
        mockMvc.perform(get("/api/users/seekers"))
                .andExpect(status().isOk());
    }

    @Test
    void getTotalUserCount_Success() throws Exception {
        when(userService.getTotalUserCount()).thenReturn(10L);
        mockMvc.perform(get("/api/users/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(10));
    }

    @Test
    void getUserCountByRole_Success() throws Exception {
        when(userService.getUserCountByRole(any())).thenReturn(5L);
        mockMvc.perform(get("/api/users/count/role/EMPLOYER"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(5));
    }

    @Test
    void bulkToggleStatus_Success() throws Exception {
        mockMvc.perform(patch("/api/users/bulk/toggle-status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1, 2]")
                        .param("active", "true"))
                .andExpect(status().isOk());
    }

    @Test
    void getCurrentProfile_Success() throws Exception {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("test@test.com");
        when(userService.getCurrentUserProfile("test@test.com")).thenReturn(new UserResponse());

        mockMvc.perform(get("/api/users/me").principal(principal))
                .andExpect(status().isOk());
    }

    @Test
    void updateMyProfile_Success() throws Exception {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("test@test.com");
        UserResponse req = new UserResponse();
        when(userService.updateMyProfile(eq("test@test.com"), any())).thenReturn(new UserResponse());

        mockMvc.perform(put("/api/users/me").principal(principal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void updateEmail_Success() throws Exception {
        mockMvc.perform(patch("/api/users/1/email").param("newEmail", "new@test.com"))
                .andExpect(status().isOk());
    }
}

