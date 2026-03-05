package org.example.revhire.mapper;

import org.example.revhire.dto.response.UserResponse;
import org.example.revhire.enums.Role;
import org.example.revhire.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Test
    void toDto_Success() {
        User user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setRole(Role.EMPLOYER);
        user.setPhone("1234567890");

        UserResponse dto = userMapper.toDto(user);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Test User", dto.getName());
        assertEquals("test@example.com", dto.getEmail());
        assertEquals(Role.EMPLOYER, dto.getRole());
        assertEquals("1234567890", dto.getPhone());
    }

    @Test
    void toEntity_Success() {
        UserResponse dto = new UserResponse();
        dto.setId(2L);
        dto.setName("Seeker User");
        dto.setEmail("seeker@example.com");
        dto.setRole(Role.JOB_SEEKER);

        User user = userMapper.toEntity(dto);

        assertNotNull(user);
        assertEquals(2L, user.getId());
        assertEquals("Seeker User", user.getName());
        assertEquals("seeker@example.com", user.getEmail());
        assertEquals(Role.JOB_SEEKER, user.getRole());
    }

    @Test
    void nullMappings() {
        assertNull(userMapper.toDto(null));
        assertNull(userMapper.toEntity(null));
    }
}
