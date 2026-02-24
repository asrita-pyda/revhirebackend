package org.example.revhire.controller;

import org.example.revhire.dto.response.ApiResponse;
import org.example.revhire.dto.request.LoginRequest;
import org.example.revhire.dto.request.RegistrationRequest;
import org.example.revhire.dto.response.AuthResponse;
import org.example.revhire.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Allow all origins for dev
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> registerUser(
            @Valid @RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Registration successful", userService.registerUser(registrationRequest)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", userService.loginUser(loginRequest)));
    }
}
