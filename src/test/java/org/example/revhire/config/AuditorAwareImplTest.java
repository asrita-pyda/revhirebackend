package org.example.revhire.config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuditorAwareImplTest {

    private final AuditorAwareImpl auditorAware = new AuditorAwareImpl();

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getCurrentAuditor_NoAuthentication_ReturnsSystem() {
        SecurityContextHolder.clearContext();

        Optional<String> auditor = auditorAware.getCurrentAuditor();

        assertTrue(auditor.isPresent());
        assertEquals("SYSTEM", auditor.get());
    }

    @Test
    void getCurrentAuditor_AnonymousUser_ReturnsSystem() {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("anonymousUser", null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        Optional<String> auditor = auditorAware.getCurrentAuditor();

        assertTrue(auditor.isPresent());
        assertEquals("SYSTEM", auditor.get());
    }

    @Test
    void getCurrentAuditor_AuthenticatedUser_ReturnsUsername() {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("admin@example.com", null,
                java.util.Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(auth);

        Optional<String> auditor = auditorAware.getCurrentAuditor();

        assertTrue(auditor.isPresent());
        assertEquals("admin@example.com", auditor.get());
    }
}
