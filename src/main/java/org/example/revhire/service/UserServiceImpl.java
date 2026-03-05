package org.example.revhire.service;

import org.example.revhire.dto.request.LoginRequest;
import org.example.revhire.dto.request.RegistrationRequest;
import org.example.revhire.dto.response.AuthResponse;
import org.example.revhire.dto.response.UserResponse;
import org.example.revhire.model.Employer;
import org.example.revhire.model.JobSeeker;
import org.example.revhire.enums.Role;
import org.example.revhire.model.User;
import org.example.revhire.repository.EmployerRepository;
import org.example.revhire.repository.JobSeekerRepository;
import org.example.revhire.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmployerRepository employerRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;
    private final org.example.revhire.mapper.UserMapper userMapper;
    private final org.example.revhire.config.JwtUtils jwtUtils;
    private final OtpService otpService;

    public UserServiceImpl(UserRepository userRepository, EmployerRepository employerRepository,
                           JobSeekerRepository jobSeekerRepository,
                           org.springframework.security.crypto.password.PasswordEncoder passwordEncoder,
                           org.example.revhire.mapper.UserMapper userMapper,
                           org.example.revhire.config.JwtUtils jwtUtils,
                           OtpService otpService) {
        this.userRepository = userRepository;
        this.employerRepository = employerRepository;
        this.jobSeekerRepository = jobSeekerRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.jwtUtils = jwtUtils;
        this.otpService = otpService;
    }

    @Override
    @Transactional
    public AuthResponse registerUser(RegistrationRequest req) {
        // Verify OTP before doing anything else
        if (!otpService.verify(req.getEmail(), req.getOtpCode())) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        if (userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(req.getRole());
        user.setPhone(req.getPhone());
        user.setLocation(req.getLocation());
        user.setSecurityQuestion(req.getSecurityQuestion());
        user.setSecurityAnswer(req.getSecurityAnswer());

        User savedUser = userRepository.save(user);

        if (req.getRole() == Role.EMPLOYER) {
            Employer employer = new Employer();
            employer.setUser(savedUser);
            employer.setCompanyName(req.getCompanyName());
            employer.setIndustry(req.getIndustry());
            employer.setCompanySize(req.getCompanySize());
            employer.setDescription(req.getDescription());
            employer.setWebsite(req.getWebsite());
            employer.setLocation(req.getLocation()); // using same location
            employerRepository.save(employer);
        } else if (req.getRole() == Role.JOB_SEEKER) {
            JobSeeker jobSeeker = new JobSeeker();
            jobSeeker.setUser(savedUser);
            jobSeeker.setCurrentStatus(req.getCurrentStatus());
            jobSeeker.setTotalExperience(req.getTotalExperience());
            jobSeekerRepository.save(jobSeeker);
        }

        String token = jwtUtils.generateToken(savedUser.getEmail());

        return new AuthResponse(savedUser.getId(), savedUser.getName(), savedUser.getEmail(), savedUser.getRole(),
                token);
    }

    @Override
    public AuthResponse loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtils.generateToken(user.getEmail());
        return new AuthResponse(user.getId(), user.getName(), user.getEmail(), user.getRole(), token);
    }

    @Override
    public UserResponse getUserProfile(Long userId) {
        User user = getUserById(userId);
        return mapToDto(user);
    }

    @Override
    @Transactional
    public UserResponse updateUserProfile(Long userId, UserResponse dto) {
        User user = getUserById(userId);

        if (dto.getName() != null)
            user.setName(dto.getName());
        if (dto.getPhone() != null)
            user.setPhone(dto.getPhone());
        if (dto.getLocation() != null)
            user.setLocation(dto.getLocation());

        userRepository.save(user);

        if (user.getRole() == Role.EMPLOYER) {
            Employer employer = employerRepository.findById(userId).orElse(new Employer());
            // Update employer fields
            if (dto.getCompanyName() != null)
                employer.setCompanyName(dto.getCompanyName());
            // ... other fields
            employerRepository.save(employer);
        } else if (user.getRole() == Role.JOB_SEEKER) {
            JobSeeker jobSeeker = jobSeekerRepository.findById(userId).orElse(new JobSeeker());
            if (dto.getCurrentStatus() != null)
                jobSeeker.setCurrentStatus(dto.getCurrentStatus());
            jobSeekerRepository.save(jobSeeker);
        }

        return mapToDto(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    @Override
    @Transactional
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = getUserById(userId);
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Invalid old password");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void activateAccount(Long userId) {
        User user = getUserById(userId);
        user.setActive(true);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deactivateAccount(Long userId) {
        User user = getUserById(userId);
        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public List<UserResponse> getUsersByRole(Role role) {
        return userRepository.findAll().stream()
                .filter(u -> u.getRole() == role)
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<UserResponse> searchUsers(String query) {
        String lowerQuery = query.toLowerCase();
        return userRepository.findAll().stream()
                .filter(u -> (u.getName() != null && u.getName().toLowerCase().contains(lowerQuery)) ||
                        (u.getEmail() != null && u.getEmail().toLowerCase().contains(lowerQuery)))
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<UserResponse> getAllEmployers() {
        return getUsersByRole(Role.EMPLOYER);
    }

    @Override
    public List<UserResponse> getAllSeekers() {
        return getUsersByRole(Role.JOB_SEEKER);
    }

    @Override
    public long getTotalUserCount() {
        return userRepository.count();
    }

    @Override
    public long getUserCountByRole(Role role) {
        return userRepository.findAll().stream()
                .filter(u -> u.getRole() == role)
                .count();
    }

    @Override
    @Transactional
    public void bulkToggleUserStatus(List<Long> userIds, boolean active) {
        userIds.forEach(id -> {
            userRepository.findById(id).ifPresent(user -> {
                user.setActive(active);
                userRepository.save(user);
            });
        });
    }

    @Override
    public UserResponse getCurrentUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
        return mapToDto(user);
    }

    @Override
    @Transactional
    public UserResponse updateMyProfile(String email, UserResponse dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
        return updateUserProfile(user.getId(), dto);
    }

    @Override
    @Transactional
    public void updateEmail(Long id, String newEmail) {
        User user = getUserById(id);
        if (userRepository.existsByEmail(newEmail)) {
            throw new RuntimeException("Email already exists: " + newEmail);
        }
        user.setEmail(newEmail);
        userRepository.save(user);
    }

    private UserResponse mapToDto(User user) {
        UserResponse dto = userMapper.toDto(user);

        if (user.getRole() == Role.EMPLOYER) {
            employerRepository.findById(user.getId()).ifPresent(emp -> {
                dto.setCompanyName(emp.getCompanyName());
                dto.setIndustry(emp.getIndustry());
                dto.setCompanySize(emp.getCompanySize());
                dto.setDescription(emp.getDescription());
                dto.setWebsite(emp.getWebsite());
            });
        } else if (user.getRole() == Role.JOB_SEEKER) {
            jobSeekerRepository.findById(user.getId()).ifPresent(seeker -> {
                dto.setCurrentStatus(seeker.getCurrentStatus());
                dto.setTotalExperience(seeker.getTotalExperience());
            });
        }
        return dto;
    }

}
