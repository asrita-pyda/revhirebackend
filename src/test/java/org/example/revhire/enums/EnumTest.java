package org.example.revhire.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnumTest {

    @Test
    void applicationStatus_AllValues() {
        ApplicationStatus[] values = ApplicationStatus.values();
        assertEquals(5, values.length);
        assertEquals(ApplicationStatus.APPLIED, ApplicationStatus.valueOf("APPLIED"));
        assertEquals(ApplicationStatus.UNDER_REVIEW, ApplicationStatus.valueOf("UNDER_REVIEW"));
        assertEquals(ApplicationStatus.SHORTLISTED, ApplicationStatus.valueOf("SHORTLISTED"));
        assertEquals(ApplicationStatus.REJECTED, ApplicationStatus.valueOf("REJECTED"));
        assertEquals(ApplicationStatus.WITHDRAWN, ApplicationStatus.valueOf("WITHDRAWN"));
    }

    @Test
    void jobStatus_AllValues() {
        JobStatus[] values = JobStatus.values();
        assertEquals(3, values.length);
        assertEquals(JobStatus.OPEN, JobStatus.valueOf("OPEN"));
        assertEquals(JobStatus.CLOSED, JobStatus.valueOf("CLOSED"));
        assertEquals(JobStatus.FILLED, JobStatus.valueOf("FILLED"));
    }

    @Test
    void jobType_AllValues() {
        JobType[] values = JobType.values();
        assertEquals(4, values.length);
        assertEquals(JobType.FULLTIME, JobType.valueOf("FULLTIME"));
        assertEquals(JobType.PARTTIME, JobType.valueOf("PARTTIME"));
        assertEquals(JobType.INTERNSHIP, JobType.valueOf("INTERNSHIP"));
        assertEquals(JobType.CONTRACT, JobType.valueOf("CONTRACT"));
    }

    @Test
    void role_AllValues() {
        Role[] values = Role.values();
        assertEquals(3, values.length);
        assertEquals(Role.JOB_SEEKER, Role.valueOf("JOB_SEEKER"));
        assertEquals(Role.EMPLOYER, Role.valueOf("EMPLOYER"));
        assertEquals(Role.ADMIN, Role.valueOf("ADMIN"));
    }

    @Test
    void applicationStatus_InvalidValue_Throws() {
        assertThrows(IllegalArgumentException.class, () -> ApplicationStatus.valueOf("INVALID"));
    }

    @Test
    void jobStatus_InvalidValue_Throws() {
        assertThrows(IllegalArgumentException.class, () -> JobStatus.valueOf("INVALID"));
    }

    @Test
    void role_InvalidValue_Throws() {
        assertThrows(IllegalArgumentException.class, () -> Role.valueOf("INVALID"));
    }

    @Test
    void jobType_InvalidValue_Throws() {
        assertThrows(IllegalArgumentException.class, () -> JobType.valueOf("INVALID"));
    }
}
