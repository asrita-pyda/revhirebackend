package org.example.revhire.service;

import org.example.revhire.dto.request.LoginRequest;
import org.example.revhire.dto.request.RegistrationRequest;
import org.example.revhire.dto.response.AuthResponse;
import org.example.revhire.enums.Role;
import org.example.revhire.model.User;
import org.example.revhire.repository.EmployerRepository;
import org.example.revhire.repository.JobSeekerRepository;
import org.example.revhire.repository.UserRepository;
import org.example.revhire.mapper.UserMapper;
import org.example.revhire.model.Employer;
import org.example.revhire.model.JobSeeker;
import org.example.revhire.config.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.example.revhire.dto.response.UserResponse;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private EmployerRepository employerRepository;
    @Mock
    private JobSeekerRepository jobSeekerRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserMapper userMapper;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private OtpService otpService;
    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        lenient().when(otpService.verify(anyString(), nullable(String.class))).thenReturn(true);
    }

    @Test
    void registerUser_Success() {
        RegistrationRequest req = new RegistrationRequest();
        req.setEmail("test@test.com");
        req.setPassword("password");
        req.setRole(Role.EMPLOYER);
        req.setName("Test User");
        req.setOtpCode("123456");

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail("test@test.com");
        savedUser.setRole(Role.EMPLOYER);

        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(jwtUtils.generateToken(anyString())).thenReturn("mockToken");

        AuthResponse response = userService.registerUser(req);

        assertNotNull(response);
        assertEquals("mockToken", response.getToken());
        verify(userRepository).save(any(User.class));
        verify(employerRepository).save(any());
    }

    @Test
    void loginUser_InvalidPassword() {
        LoginRequest req = new LoginRequest();
        req.setEmail("test@test.com");
        req.setPassword("wrong");

        User user = new User();
        user.setPassword("encoded");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "encoded")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> userService.loginUser(req));
    }

    @Test
    void getUserProfile_Success() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(new UserResponse());

        UserResponse response = userService.getUserProfile(1L);
        assertNotNull(response);
    }

    @Test
    void updateUserProfile_EmployerSuccess() {
        User user = new User();
        user.setId(1L);
        user.setRole(Role.EMPLOYER);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(employerRepository.findByUserReferenceId(1L)).thenReturn(Optional.of(new Employer()));
        when(userMapper.toDto(user)).thenReturn(new UserResponse());

        UserResponse dto = new UserResponse();
        dto.setName("New Name");
        dto.setCompanyName("New Co");

        UserResponse result = userService.updateUserProfile(1L, dto);
        assertNotNull(result);
        assertEquals("New Name", user.getName());
        verify(employerRepository).save(any());
    }

    @Test
    void changePassword_Success() {
        User user = new User();
        user.setPassword("oldEncoded");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("old", "oldEncoded")).thenReturn(true);
        when(passwordEncoder.encode("new")).thenReturn("newEncoded");

        userService.changePassword(1L, "old", "new");
        assertEquals("newEncoded", user.getPassword());
    }

    @Test
    void accountStatus_Success() {
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.activateAccount(1L);
        assertTrue(user.isActive());

        userService.deactivateAccount(1L);
        assertFalse(user.isActive());
    }

    @Test
    void searchUsers_Success() {
        User user = new User();
        user.setName("John");
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toDto(user)).thenReturn(new UserResponse());

        List<UserResponse> result = userService.searchUsers("john");
        assertEquals(1, result.size());
    }

    @Test
    void counts_Success() {
        when(userRepository.count()).thenReturn(10L);
        assertEquals(10L, userService.getTotalUserCount());

        User user = new User();
        user.setRole(Role.EMPLOYER);
        when(userRepository.findAll()).thenReturn(List.of(user));
        assertEquals(1, userService.getUserCountByRole(Role.EMPLOYER));
    }

    @Test
    void bulkToggle_Success() {
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.bulkToggleUserStatus(List.of(1L), true);
        assertTrue(user.isActive());
    }

    @Test
    void getCurrentUserProfile_Success() {
        User user = new User();
        user.setEmail("test@test.com");
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(new UserResponse());

        UserResponse result = userService.getCurrentUserProfile("test@test.com");
        assertNotNull(result);
    }

    @Test
    void updateEmail_Success() {
        User user = new User();
        user.setEmail("old@test.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail("new@test.com")).thenReturn(false);

        userService.updateEmail(1L, "new@test.com");
        assertEquals("new@test.com", user.getEmail());
    }

    @Test
    void updateUserProfile_SeekerSuccess() {
        User user = new User();
        user.setId(1L);
        user.setRole(Role.JOB_SEEKER);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(jobSeekerRepository.findById(1L)).thenReturn(Optional.of(new JobSeeker()));
        when(userMapper.toDto(user)).thenReturn(new UserResponse());

        UserResponse dto = new UserResponse();
        dto.setName("Seeker Name");
        dto.setCurrentStatus("Looking");

        UserResponse result = userService.updateUserProfile(1L, dto);
        assertNotNull(result);
        assertEquals("Seeker Name", user.getName());
    }

    @Test
    void updateMyProfile_Success() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setRole(Role.EMPLOYER);
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(employerRepository.findByUserReferenceId(any())).thenReturn(Optional.of(new Employer()));
        when(userMapper.toDto(user)).thenReturn(new UserResponse());

        UserResponse result = userService.updateMyProfile("test@test.com", new UserResponse());
        assertNotNull(result);
    }

    @Test
    void lambdas_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.getUserProfile(1L));
        assertThrows(RuntimeException.class, () -> userService.updateUserProfile(1L, new UserResponse()));
        assertThrows(RuntimeException.class, () -> userService.changePassword(1L, "a", "b"));
    }

    @Test
    void registerUser_DuplicateEmail_Throws() {
        RegistrationRequest req = new RegistrationRequest();
        req.setEmail("existing@test.com");
        req.setOtpCode("123456");
        when(userRepository.existsByEmail("existing@test.com")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> userService.registerUser(req));
    }

    @Test
    void loginUser_UserNotFound_Throws() {
        LoginRequest req = new LoginRequest();
        req.setEmail("missing@test.com");
        req.setPassword("pass");

        when(userRepository.findByEmail("missing@test.com")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.loginUser(req));
    }

    @Test
    void loginUser_ValidCredentials_Success() {
        LoginRequest req = new LoginRequest();
        req.setEmail("test@test.com");
        req.setPassword("correct");

        User user = new User();
        user.setId(1L);
        user.setName("Test");
        user.setEmail("test@test.com");
        user.setPassword("encoded");
        user.setRole(Role.EMPLOYER);

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("correct", "encoded")).thenReturn(true);
        when(jwtUtils.generateToken("test@test.com")).thenReturn("validToken");

        AuthResponse response = userService.loginUser(req);
        assertNotNull(response);
        assertEquals("validToken", response.getToken());
        assertEquals("test@test.com", response.getEmail());
    }

    @Test
    void changePassword_WrongOldPassword_Throws() {
        User user = new User();
        user.setPassword("encoded");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "encoded")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> userService.changePassword(1L, "wrong", "new"));
    }

    @Test
    void updateEmail_DuplicateEmail_Throws() {
        User user = new User();
        user.setEmail("old@test.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail("taken@test.com")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> userService.updateEmail(1L, "taken@test.com"));
    }

    @Test
    void getCurrentUserProfile_NotFound_Throws() {
        when(userRepository.findByEmail("missing@test.com")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.getCurrentUserProfile("missing@test.com"));
    }

    @Test
    void updateMyProfile_NotFound_Throws() {
        when(userRepository.findByEmail("missing@test.com")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.updateMyProfile("missing@test.com", new UserResponse()));
    }

    @Test
    void registerUser_JobSeeker_Success() {
        RegistrationRequest req = new RegistrationRequest();
        req.setEmail("seeker@test.com");
        req.setPassword("password");
        req.setRole(Role.JOB_SEEKER);
        req.setName("Seeker");
        req.setCurrentStatus("Looking");
        req.setTotalExperience(2);
        req.setOtpCode("123456");

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        User savedUser = new User();
        savedUser.setId(2L);
        savedUser.setEmail("seeker@test.com");
        savedUser.setRole(Role.JOB_SEEKER);
        savedUser.setName("Seeker");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(jwtUtils.generateToken(anyString())).thenReturn("seekerToken");

        AuthResponse response = userService.registerUser(req);
        assertNotNull(response);
        verify(jobSeekerRepository).save(any());
    }

    @Test
    void getAllEmployers_Success() {
        User employer = new User();
        employer.setRole(Role.EMPLOYER);
        when(userRepository.findAll()).thenReturn(List.of(employer));
        when(userMapper.toDto(employer)).thenReturn(new UserResponse());

        List<UserResponse> result = userService.getAllEmployers();
        assertEquals(1, result.size());
    }

    @Test
    void getAllSeekers_Success() {
        User seeker = new User();
        seeker.setRole(Role.JOB_SEEKER);
        when(userRepository.findAll()).thenReturn(List.of(seeker));
        when(userMapper.toDto(seeker)).thenReturn(new UserResponse());

        List<UserResponse> result = userService.getAllSeekers();
        assertEquals(1, result.size());
    }

    @Test
    void getUserById_Success() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void getUserById_NotFound_Throws() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.getUserById(99L));
    }

    @Test
    void mapToDto_EmployerWithNoEntry() {
        User user = new User();
        user.setId(1L);
        user.setRole(Role.EMPLOYER);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(employerRepository.findByUserReferenceId(1L)).thenReturn(Optional.empty());
        when(userMapper.toDto(user)).thenReturn(new UserResponse());

        UserResponse result = userService.getUserProfile(1L);
        assertNotNull(result);
    }

    @Test
    void mapToDto_SeekerWithNoEntry() {
        User user = new User();
        user.setId(1L);
        user.setRole(Role.JOB_SEEKER);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(jobSeekerRepository.findById(1L)).thenReturn(Optional.empty());
        when(userMapper.toDto(user)).thenReturn(new UserResponse());

        UserResponse result = userService.getUserProfile(1L);
        assertNotNull(result);
    }

    @Test
    void searchUsers_ByEmail_Success() {
        User user = new User();
        user.setEmail("findme@example.com");
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toDto(user)).thenReturn(new UserResponse());

        List<UserResponse> result = userService.searchUsers("findme");
        assertEquals(1, result.size());
    }

    @Test
    void searchUsers_NoMatch_ReturnsEmpty() {
        User user = new User();
        user.setName("John");
        user.setEmail("john@example.com");
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserResponse> result = userService.searchUsers("zzz");
        assertEquals(0, result.size());
    }
}
