package org.example.revhire.mapper;

import org.example.revhire.dto.response.JobResponse;
import org.example.revhire.model.Job;
import org.example.revhire.model.JobSkill;
import org.example.revhire.model.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JobMapperTest {

    private final JobMapper jobMapper = JobMapper.INSTANCE;

    @Test
    void toDto_Success() {
        User employer = new User();
        employer.setId(1L);
        employer.setName("Tech Corp");

        Job job = new Job();
        job.setId(10L);
        job.setTitle("Developer");
        job.setEmployer(employer);

        JobSkill skill = new JobSkill();
        skill.setSkill("Java");
        job.setJobSkills(List.of(skill));

        JobResponse dto = jobMapper.toDto(job);

        assertNotNull(dto);
        assertEquals(1L, dto.getEmployerId());
        assertEquals("Tech Corp", dto.getEmployerName());
        assertEquals(1, dto.getSkills().size());
        assertEquals("Java", dto.getSkills().get(0));
    }

    @Test
    void toEntity_Success() {
        JobResponse resp = new JobResponse();
        resp.setId(10L);
        resp.setTitle("Dev");

        Job job = jobMapper.toEntity(resp);
        assertNotNull(job);
        assertEquals(10L, job.getId());
        assertEquals("Dev", job.getTitle());
    }

    @Test
    void nullMappings() {
        assertNull(jobMapper.toDto(null));
        assertNull(jobMapper.toEntity(null));
        assertNull(jobMapper.mapSkills(null));
    }
}
