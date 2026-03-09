package org.example.revhire.dto.request;

public class ResetPasswordRequest {
    private String email;
    private String otpCode;
    private String newPassword;
    private String securityAnswer;

    public ResetPasswordRequest() {
    }

    public ResetPasswordRequest(String email, String otpCode, String newPassword, String securityAnswer) {
        this.email = email;
        this.otpCode = otpCode;
        this.newPassword = newPassword;
        this.securityAnswer = securityAnswer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }
}
