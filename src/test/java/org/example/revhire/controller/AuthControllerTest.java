package org.example.revhire.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.revhire.dto.request.LoginRequest;
import org.example.revhire.dto.request.RegistrationRequest;
import org.example.revhire.dto.response.AuthResponse;
import org.example.revhire.service.EmailService;
import org.example.revhire.service.OtpService;
import org.example.revhire.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

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

    @MockBean
    private OtpService otpService;

    @MockBean
    private EmailService emailService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerUser_Success() throws Exception {
        RegistrationRequest req = new RegistrationRequest();
        req.setEmail("test@test.com");
        req.setPassword("password");
        req.setName("Test");
        req.setRole(org.example.revhire.enums.Role.JOB_SEEKER);

        when(userService.registerUser(any())).thenReturn(new AuthResponse(1L, "Test", "test@test.com", null, "token"));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.token").value("token"));
    }

    @Test
    void loginUser_Success() throws Exception {
        LoginRequest req = new LoginRequest();
        req.setEmail("test@test.com");
        req.setPassword("password");

        when(userService.loginUser(any())).thenReturn(new AuthResponse(1L, "Test", "test@test.com", null, "token"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.token").value("token"));
    }
}

