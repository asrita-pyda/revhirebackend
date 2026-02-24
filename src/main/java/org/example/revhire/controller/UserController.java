package org.example.revhire.controller;

import org.example.revhire.dto.response.ApiResponse;
import org.example.revhire.dto.response.UserResponse;
import org.example.revhire.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.revhire.enums.Role;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserProfile(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Profile retrieved", userService.getUserProfile(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserProfile(@PathVariable Long id,
                                                                       @RequestBody UserResponse userResponse) {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Profile updated", userService.updateUserProfile(id, userResponse)));
    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<ApiResponse<String>> changePassword(@PathVariable Long id,
                                                              @RequestParam String oldPassword, @RequestParam String newPassword) {
        userService.changePassword(id, oldPassword, newPassword);
        return ResponseEntity.ok(new ApiResponse<>(true, "Password changed successfully"));
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<String>> activateAccount(@PathVariable Long id) {
        userService.activateAccount(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Account activated"));
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<String>> deactivateAccount(@PathVariable Long id) {
        userService.deactivateAccount(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Account deactivated"));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getUsersByRole(@PathVariable Role role) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Users by role retrieved", userService.getUsersByRole(role)));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<UserResponse>>> searchUsers(@RequestParam String query) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Search results", userService.searchUsers(query)));
    }

    @GetMapping("/employers")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllEmployers() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Employers retrieved", userService.getAllEmployers()));
    }

    @GetMapping("/seekers")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllSeekers() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Seekers retrieved", userService.getAllSeekers()));
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> getTotalUserCount() {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Total users count retrieved", userService.getTotalUserCount()));
    }

    @GetMapping("/count/role/{role}")
    public ResponseEntity<ApiResponse<Long>> getUserCountByRole(@PathVariable Role role) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Role count retrieved", userService.getUserCountByRole(role)));
    }

    @PatchMapping("/bulk/toggle-status")
    public ResponseEntity<ApiResponse<Void>> bulkToggleStatus(@RequestBody List<Long> ids,
                                                              @RequestParam boolean active) {
        userService.bulkToggleUserStatus(ids, active);
        return ResponseEntity.ok(new ApiResponse<>(true, "Status updated for multiple users"));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentProfile(java.security.Principal principal) {
        return ResponseEntity.ok(new ApiResponse<>(true, "My profile retrieved",
                userService.getCurrentUserProfile(principal.getName())));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> updateMyProfile(java.security.Principal principal,
                                                                     @RequestBody UserResponse userResponse) {
        return ResponseEntity.ok(new ApiResponse<>(true, "My profile updated",
                userService.updateMyProfile(principal.getName(), userResponse)));
    }

    @PatchMapping("/{id}/email")
    public ResponseEntity<ApiResponse<Void>> updateEmail(@PathVariable Long id, @RequestParam String newEmail) {
        userService.updateEmail(id, newEmail);
        return ResponseEntity.ok(new ApiResponse<>(true, "Email updated successfully"));
    }
}

