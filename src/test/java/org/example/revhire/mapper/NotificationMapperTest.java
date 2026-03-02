package org.example.revhire.mapper;

import org.example.revhire.dto.response.NotificationResponse;
import org.example.revhire.model.Notification;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class NotificationMapperTest {

    private final NotificationMapper notificationMapper = NotificationMapper.INSTANCE;

    @Test
    void toDto_Success() {
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setMessage("Test Notification");
        notification.setType("INFO");
        LocalDateTime now = LocalDateTime.now();
        notification.setCreatedAt(now);

        NotificationResponse dto = notificationMapper.toDto(notification);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Test Notification", dto.getMessage());
        assertEquals("INFO", dto.getType());
        assertEquals(now, dto.getCreatedAt());
    }

    @Test
    void toDto_NullInput_ReturnsNull() {
        assertNull(notificationMapper.toDto(null));
    }
}
