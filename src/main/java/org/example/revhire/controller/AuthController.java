package org.example.revhire.controller;

import org.example.revhire.dto.response.ApiResponse;
import org.example.revhire.dto.request.LoginRequest;
import org.example.revhire.dto.request.OtpLoginRequest;
import org.example.revhire.dto.request.RegistrationRequest;
import org.example.revhire.dto.request.ResetPasswordRequest;
import org.example.revhire.dto.response.AuthResponse;
import org.example.revhire.service.EmailService;
import org.example.revhire.service.OtpService;
import org.example.revhire.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Allow all origins for dev
public class AuthController {

    private final UserService userService;
    private final OtpService otpService;
    private final EmailService emailService;
    @Value("${app.otp.fallback-enabled:true}")
    private boolean otpFallbackEnabled;

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

    @PostMapping("/login-otp")
    public ResponseEntity<ApiResponse<AuthResponse>> loginWithOtp(@RequestBody OtpLoginRequest loginRequest) {
        return ResponseEntity.ok(new ApiResponse<>(true, "OTP login successful", userService.loginUserWithOtp(loginRequest)));
    }

    @PostMapping("/send-otp")
    public ResponseEntity<ApiResponse<String>> sendOtp(@RequestParam String email) {
        String normalizedEmail = email == null ? "" : email.trim();
        if (normalizedEmail.isBlank() || !normalizedEmail.matches("(?i)^[\\w.+\\-]+@[\\w\\-]+\\.[a-z]{2,}$")) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false, "Invalid email address", null));
        }
        String otp = otpService.generateAndStore(normalizedEmail);
        try {
            emailService.sendOtpEmail(normalizedEmail, otp);
            return ResponseEntity.ok(new ApiResponse<>(true, "OTP sent to your email", null));
        } catch (RuntimeException ex) {
            if (otpFallbackEnabled) {
                return ResponseEntity.ok(new ApiResponse<>(true,
                        "Email delivery failed (" + ex.getMessage() + "), using OTP fallback for local testing.",
                        otp));
            }
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false, "Failed to send OTP email: " + ex.getMessage(), null));
        }
    }

    @GetMapping("/security-question")
    public ResponseEntity<ApiResponse<String>> getSecurityQuestion(@RequestParam String email) {
        String question = userService.getSecurityQuestion(email == null ? "" : email.trim());
        return ResponseEntity.ok(new ApiResponse<>(true, "Security question retrieved", question));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<String>> resetPassword(@RequestBody ResetPasswordRequest request) {
        userService.resetPassword(request.getEmail(), request.getOtpCode(), request.getNewPassword(),
                request.getSecurityAnswer());
        return ResponseEntity.ok(new ApiResponse<>(true, "Password reset successfully"));
    }
}
