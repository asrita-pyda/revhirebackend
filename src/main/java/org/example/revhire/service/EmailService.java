package org.example.revhire.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Sends a styled HTML email containing the OTP to the given address.
     */
    public void sendOtpEmail(String toEmail, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Your RevHire Verification Code");
            helper.setText(buildEmailBody(otp), true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send OTP email: " + e.getMessage(), e);
        }
    }

    private String buildEmailBody(String otp) {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                  <meta charset="UTF-8" />
                  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                </head>
                <body style="margin:0;padding:0;background:#f4f6fb;font-family:'Segoe UI',Arial,sans-serif;">
                  <table width="100%%" cellpadding="0" cellspacing="0" style="background:#f4f6fb;padding:40px 0;">
                    <tr>
                      <td align="center">
                        <table width="500" cellpadding="0" cellspacing="0"
                               style="background:#ffffff;border-radius:12px;overflow:hidden;
                                      box-shadow:0 4px 24px rgba(0,0,0,0.08);">
                          <!-- Header -->
                          <tr>
                            <td style="background:linear-gradient(135deg,#4f46e5,#7c3aed);
                                        padding:36px 40px;text-align:center;">
                              <h1 style="margin:0;color:#ffffff;font-size:28px;font-weight:700;
                                          letter-spacing:-0.5px;">RevHire</h1>
                              <p style="margin:8px 0 0;color:rgba(255,255,255,0.85);font-size:14px;">
                                Email Verification
                              </p>
                            </td>
                          </tr>
                          <!-- Body -->
                          <tr>
                            <td style="padding:40px;">
                              <p style="margin:0 0 16px;color:#374151;font-size:16px;">Hello,</p>
                              <p style="margin:0 0 28px;color:#374151;font-size:15px;line-height:1.6;">
                                Use the verification code below to complete your RevHire registration.
                                This code expires in <strong>5 minutes</strong>.
                              </p>
                              <!-- OTP Box -->
                              <div style="background:#f0f0ff;border:2px dashed #6366f1;border-radius:10px;
                                           padding:24px;text-align:center;margin-bottom:28px;">
                                <span style="font-size:40px;font-weight:800;letter-spacing:12px;
                                              color:#4f46e5;font-family:monospace;">%s</span>
                              </div>
                              <p style="margin:0 0 8px;color:#6b7280;font-size:13px;">
                                If you didn't request this code, please ignore this email.
                                Your account is safe.
                              </p>
                            </td>
                          </tr>
                          <!-- Footer -->
                          <tr>
                            <td style="background:#f9fafb;padding:20px 40px;text-align:center;
                                        border-top:1px solid #e5e7eb;">
                              <p style="margin:0;color:#9ca3af;font-size:12px;">
                                © 2024 RevHire · All rights reserved
                              </p>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                </body>
                </html>
                """.formatted(otp);
    }
}
