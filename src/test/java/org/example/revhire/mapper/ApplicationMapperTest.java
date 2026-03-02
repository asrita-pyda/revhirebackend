package org.example.revhire.mapper;

import org.example.revhire.dto.response.ApplicationResponse;
import org.example.revhire.model.Applications;
import org.example.revhire.model.Job;
import org.example.revhire.model.ResumeFiles;
import org.example.revhire.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationMapperTest {

    private final ApplicationMapper applicationMapper = org.mapstruct.factory.Mappers
            .getMapper(ApplicationMapper.class);

    @Test
    void toDto_Success() {
        Job job = new Job();
        job.setId(10L);
        job.setTitle("Test Job");
        job.setLocation("NYC");

        User seeker = new User();
        seeker.setId(1L);
        seeker.setName("John Doe");
        seeker.setEmail("john@test.com");

        ResumeFiles resumeFile = new ResumeFiles();
        resumeFile.setId(50L);

        Applications app = new Applications();
        app.setId(100L);
        app.setJob(job);
        app.setSeeker(seeker);
        app.setResumeFile(resumeFile);
        LocalDateTime now = LocalDateTime.now();
        app.setAppliedAt(now);

        ApplicationResponse dto = applicationMapper.toDto(app);

        assertNotNull(dto);
        assertEquals(10L, dto.getJobId());
        assertEquals("Test Job", dto.getJobTitle());
        assertEquals("NYC", dto.getLocation());
        assertEquals(1L, dto.getSeekerId());
        assertEquals("John Doe", dto.getSeekerName());
        assertEquals("john@test.com", dto.getSeekerEmail());
        assertEquals(50L, dto.getResumeFileId());
    }

    @Test
    void nullMappings() {
        assertNull(applicationMapper.toDto(null));
    }
}
