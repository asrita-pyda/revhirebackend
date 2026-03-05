package org.example.revhire.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilsTest {

    private JwtUtils jwtUtils;

    @BeforeEach
    void setUp() throws Exception {
        jwtUtils = new JwtUtils();
        // Set jwtExpirationMs via reflection since @Value won't be injected in plain unit tests.
        Field field = JwtUtils.class.getDeclaredField("jwtExpirationMs");
        field.setAccessible(true);
        field.setInt(jwtUtils, 3600000);
    }

    @Test
    void generateToken_ReturnsNonNullToken() {
        String token = jwtUtils.generateToken("test@example.com");
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void extractUsername_ReturnsCorrectEmail() {
        String email = "test@example.com";
        String token = jwtUtils.generateToken(email);

        String extractedEmail = jwtUtils.extractUsername(token);
        assertEquals(email, extractedEmail);
    }

    @Test
    void validateToken_ValidToken_ReturnsTrue() {
        String email = "test@example.com";
        String token = jwtUtils.generateToken(email);

        assertTrue(jwtUtils.validateToken(token, email));
    }

    @Test
    void validateToken_WrongEmail_ReturnsFalse() {
        String token = jwtUtils.generateToken("test@example.com");

        assertFalse(jwtUtils.validateToken(token, "wrong@example.com"));
    }

    @Test
    void extractExpiration_ReturnsDateInFuture() {
        String token = jwtUtils.generateToken("test@example.com");

        Date expiration = jwtUtils.extractExpiration(token);
        assertNotNull(expiration);
        assertTrue(expiration.after(new Date()));
    }

    @Test
    void generateToken_DifferentEmailsProduceDifferentTokens() {
        String token1 = jwtUtils.generateToken("user1@example.com");
        String token2 = jwtUtils.generateToken("user2@example.com");

        assertNotEquals(token1, token2);
    }

    @Test
    void extractUsername_MultipleExtractions_ReturnsSameResult() {
        String email = "consistency@example.com";
        String token = jwtUtils.generateToken(email);

        assertEquals(email, jwtUtils.extractUsername(token));
        assertEquals(email, jwtUtils.extractUsername(token));
    }
}
