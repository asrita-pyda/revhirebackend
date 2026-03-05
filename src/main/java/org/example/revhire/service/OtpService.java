package org.example.revhire.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    private final ConcurrentHashMap<String, OtpEntry> otpStore = new ConcurrentHashMap<>();
    private final SecureRandom random = new SecureRandom();
    private static final int OTP_EXPIRY_MINUTES = 5;

    private record OtpEntry(String code, LocalDateTime expiresAt) {
    }


    public String generateAndStore(String email) {
        String otp = String.format("%06d", random.nextInt(1_000_000));
        otpStore.put(email.toLowerCase(), new OtpEntry(otp, LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES)));
        return otp;
    }


    public boolean verify(String email, String code) {
        if (code == null || email == null)
            return false;
        OtpEntry entry = otpStore.get(email.toLowerCase());
        if (entry == null)
            return false;
        if (LocalDateTime.now().isAfter(entry.expiresAt())) {
            otpStore.remove(email.toLowerCase());
            return false;
        }
        if (entry.code().equals(code.trim())) {
            otpStore.remove(email.toLowerCase());
            return true;
        }
        return false;
    }
}
