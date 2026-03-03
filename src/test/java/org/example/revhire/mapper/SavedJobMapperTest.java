package org.example.revhire.mapper;

import org.example.revhire.dto.response.SavedJobResponse;
import org.example.revhire.model.Job;
import org.example.revhire.model.SavedJob;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class SavedJobMapperTest {

    private final SavedJobMapper savedJobMapper = SavedJobMapper.INSTANCE;

    @Test
    void toDto_Success() {
        Job job = new Job();
        job.setId(10L);
        job.setTitle("Software Engineer");

        SavedJob savedJob = new SavedJob();
        savedJob.setId(1L);
        savedJob.setJob(job);
        LocalDateTime now = LocalDateTime.now();
        savedJob.setCreatedAt(now);

        SavedJobResponse dto = savedJobMapper.toDto(savedJob);

        assertNotNull(dto);
        assertEquals(10L, dto.getJobId());
        assertEquals("Software Engineer", dto.getJobTitle());
        assertEquals(now, dto.getSavedAt());
    }

    @Test
    void toDto_NullInput_ReturnsNull() {
        assertNull(savedJobMapper.toDto(null));
    }
}
