package org.example.revhire.service;

import org.example.revhire.dto.request.LoginRequest;
import org.example.revhire.dto.request.RegistrationRequest;
import org.example.revhire.dto.response.AuthResponse;
import org.example.revhire.dto.response.UserResponse;
import org.example.revhire.model.User;
import org.example.revhire.enums.Role;
import java.util.List;

public interface UserService {
    AuthResponse registerUser(RegistrationRequest registrationRequest);

    AuthResponse loginUser(LoginRequest loginRequest);

    UserResponse getUserProfile(Long userId);

    UserResponse updateUserProfile(Long userId, UserResponse userResponse);

    void changePassword(Long userId, String oldPassword, String newPassword);

    void activateAccount(Long userId);

    void deactivateAccount(Long userId);

    User getUserById(Long userId);

    void bulkToggleUserStatus(List<Long> ids, boolean active);

    void updateEmail(Long userId, String newEmail);

    List<UserResponse> getUsersByRole(Role role);

    List<UserResponse> searchUsers(String query);

    List<UserResponse> getAllEmployers();

    List<UserResponse> getAllSeekers();

    long getTotalUserCount();

    long getUserCountByRole(Role role);

    UserResponse getCurrentUserProfile(String email);

    UserResponse updateMyProfile(String email, UserResponse userResponse);
}
