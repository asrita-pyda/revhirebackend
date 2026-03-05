package org.example.revhire.controller;

import org.example.revhire.dto.response.ApiResponse;
import org.example.revhire.dto.request.LoginRequest;
import org.example.revhire.dto.request.RegistrationRequest;
import org.example.revhire.dto.response.AuthResponse;
import org.example.revhire.service.EmailService;
import org.example.revhire.service.OtpService;
import org.example.revhire.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Allow all origins for dev
public class AuthController {

    private final UserService userService;
    private final OtpService otpService;
    private final EmailService emailService;

    public AuthController(UserService userService, OtpService otpService, EmailService emailService) {
        this.userService = userService;
        this.otpService = otpService;
        this.emailService = emailService;
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

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestParam String email) {
        if (email == null || !email.matches("^[\\w.+\\-]+@[\\w\\-]+\\.[a-z]{2,}$")) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false, "Invalid email address", null));
        }
        String otp = otpService.generateAndStore(email);
        emailService.sendOtpEmail(email, otp);
        return ResponseEntity.ok(new ApiResponse<>(true, "OTP sent to your email", null));
    }
}
