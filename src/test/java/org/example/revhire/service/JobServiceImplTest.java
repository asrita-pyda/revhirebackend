package org.example.revhire.service;

import org.example.revhire.dto.request.JobPostRequest;
import org.example.revhire.dto.response.JobResponse;
import org.example.revhire.enums.JobStatus;
import org.example.revhire.enums.Role;
import org.example.revhire.model.Employer;
import org.example.revhire.model.Job;
import org.example.revhire.model.User;
import org.example.revhire.repository.EmployerRepository;
import org.example.revhire.repository.JobRepository;
import org.example.revhire.repository.JobSkillRepository;
import org.example.revhire.repository.UserRepository;
import org.example.revhire.repository.ApplicationRepository;
import org.example.revhire.mapper.JobMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.example.revhire.model.JobSkill;
import org.example.revhire.model.JobView;
import org.example.revhire.model.Applications;
import org.example.revhire.dto.response.EmployerStatsResponse;
import org.example.revhire.enums.JobType;

class JobServiceImplTest {

    @Mock
    private JobRepository jobRepository;
    @Mock
    private JobSkillRepository jobSkillRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EmployerRepository employerRepository;
    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private JobMapper jobMapper;
    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private JobServiceImpl jobService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getJobById_Success() {
        Job job = new Job();
        job.setId(10L);
        User employer = new User();
        employer.setId(1L);
        job.setEmployer(employer);

        when(jobRepository.findById(10L)).thenReturn(Optional.of(job));
        when(jobMapper.toDto(job)).thenReturn(new JobResponse());
        when(employerRepository.findById(1L)).thenReturn(Optional.of(new Employer()));

        JobResponse response = jobService.getJobById(10L);
        assertNotNull(response);
        verify(jobRepository, atLeastOnce()).save(any(Job.class)); // From incrementViewCount
    }

    @Test
    void getAllJobs_Success() {
        when(jobRepository.findAll()).thenReturn(new ArrayList<>());
        List<JobResponse> result = jobService.getAllJobs();
        assertNotNull(result);
    }

    @Test
    void searchJobs_Success() {
        Job job = new Job();
        job.setStatus(JobStatus.OPEN);
        job.setTitle("Java Developer");
        job.setDescription("Backend");
        job.setLocation("Remote");
        job.setJobType(JobType.FULLTIME);
        job.setCreatedAt(LocalDateTime.now());
        User emp = new User();
        emp.setId(1L);
        job.setEmployer(emp);

        when(jobRepository.findAll()).thenReturn(List.of(job));
        when(jobMapper.toDto(any())).thenReturn(new JobResponse());
        when(employerRepository.findById(any())).thenReturn(Optional.of(new Employer()));

        List<JobResponse> results = jobService.searchJobs("java", "remote", JobType.FULLTIME);
        assertEquals(1, results.size());
    }

    @Test
    void getJobsByEmployer_Success() {
        when(jobRepository.findByEmployerId(1L)).thenReturn(new ArrayList<>());
        List<JobResponse> result = jobService.getJobsByEmployer(1L);
        assertNotNull(result);
    }

    @Test
    void deleteJob_Success() {
        jobService.deleteJob(10L);
        verify(jobRepository).deleteById(10L);
    }

    @Test
    void updateJobStatus_Success() {
        Job job = new Job();
        job.setId(10L);
        job.setStatus(JobStatus.OPEN);
        User employer = new User();
        employer.setId(1L);
        job.setEmployer(employer);

        when(jobRepository.findById(10L)).thenReturn(Optional.of(job));
        when(jobRepository.save(any(Job.class))).thenReturn(job);
        when(jobMapper.toDto(any())).thenReturn(new JobResponse());
        when(employerRepository.findById(any())).thenReturn(Optional.of(new Employer()));

        JobResponse response = jobService.updateJobStatus(10L, JobStatus.CLOSED);
        assertNotNull(response);
        verify(jobRepository).save(any(Job.class));
    }

    @Test
    void incrementViewCount_Success() {
        Job job = new Job();
        job.setJobViews(new ArrayList<>());
        User emp = new User();
        emp.setId(1L);
        job.setEmployer(emp);
        when(jobRepository.findById(10L)).thenReturn(Optional.of(job));

        jobService.incrementViewCount(10L);

        assertEquals(1, job.getJobViews().size());
        verify(jobRepository).save(job);
    }

    @Test
    void updateJob_Success() {
        Job job = new Job();
        job.setJobSkills(new ArrayList<>());
        User emp = new User();
        emp.setId(1L);
        job.setEmployer(emp);
        when(jobRepository.findById(10L)).thenReturn(Optional.of(job));
        when(jobRepository.save(any())).thenReturn(job);
        when(jobMapper.toDto(any())).thenReturn(new JobResponse());
        when(employerRepository.findById(any())).thenReturn(Optional.of(new Employer()));

        JobPostRequest req = new JobPostRequest();
        req.setTitle("New Title");
        req.setSkills(new ArrayList<>());
        req.getSkills().add("Java");

        jobService.updateJob(10L, req);

        assertEquals("New Title", job.getTitle());
        assertEquals(1, job.getJobSkills().size());
    }

    @Test
    void getRecentJobs_Success() {
        Job job = new Job();
        job.setStatus(JobStatus.OPEN);
        job.setCreatedAt(LocalDateTime.now());
        User emp = new User();
        emp.setId(1L);
        job.setEmployer(emp);
        when(jobRepository.findAll()).thenReturn(List.of(job));
        when(jobMapper.toDto(any())).thenReturn(new JobResponse());
        when(employerRepository.findById(any())).thenReturn(Optional.of(new Employer()));

        List<JobResponse> result = jobService.getRecentJobs();
        assertEquals(1, result.size());
    }

    @Test
    void getTrendingJobs_Success() {
        Job job = new Job();
        job.setStatus(JobStatus.OPEN);
        job.setJobViews(List.of(new JobView()));
        User emp = new User();
        emp.setId(1L);
        job.setEmployer(emp);
        when(jobRepository.findAll()).thenReturn(List.of(job));
        when(jobMapper.toDto(any())).thenReturn(new JobResponse());
        when(employerRepository.findById(any())).thenReturn(Optional.of(new Employer()));

        List<JobResponse> result = jobService.getTrendingJobs();
        assertEquals(1, result.size());
    }

    @Test
    void filterJobs_Success() {
        Job job = new Job();
        job.setStatus(JobStatus.OPEN);
        job.setSalaryMin(50000);
        User emp = new User();
        emp.setId(1L);
        job.setEmployer(emp);
        when(jobRepository.findAll()).thenReturn(List.of(job));
        when(jobMapper.toDto(any())).thenReturn(new JobResponse());
        when(employerRepository.findById(any())).thenReturn(Optional.of(new Employer()));

        List<JobResponse> result = jobService.filterJobs(40000, null);
        assertEquals(1, result.size());
    }

    @Test
    void getAllRequiredSkills_Success() {
        Job job = new Job();
        JobSkill skill = new JobSkill();
        skill.setSkill("Java");
        job.setJobSkills(List.of(skill));
        when(jobRepository.findAll()).thenReturn(List.of(job));

        List<String> result = jobService.getAllRequiredSkills();
        assertEquals(1, result.size());
        assertEquals("Java", result.get(0));
    }

    @Test
    void getJobsBySkill_Success() {
        Job job = new Job();
        JobSkill skill = new JobSkill();
        skill.setSkill("Java");
        job.setJobSkills(List.of(skill));
        User emp = new User();
        emp.setId(1L);
        job.setEmployer(emp);
        when(jobRepository.findAll()).thenReturn(List.of(job));
        when(jobMapper.toDto(any())).thenReturn(new JobResponse());
        when(employerRepository.findById(any())).thenReturn(Optional.of(new Employer()));

        List<JobResponse> result = jobService.getJobsBySkill("JAVA");
        assertEquals(1, result.size());
    }

    @Test
    void getJobLocations_Success() {
        Job job = new Job();
        job.setLocation("Remote");
        when(jobRepository.findAll()).thenReturn(List.of(job));
        List<String> result = jobService.getJobLocations();
        assertEquals(1, result.size());
    }

    @Test
    void getJobTypes_Success() {
        Job job = new Job();
        job.setJobType(JobType.FULLTIME);
        when(jobRepository.findAll()).thenReturn(List.of(job));
        List<JobType> result = jobService.getJobTypes();
        assertEquals(JobType.values().length, result.size());
        assertTrue(result.contains(JobType.FULLTIME));
    }

    @Test
    void counts_Success() {
        when(jobRepository.count()).thenReturn(5L);
        assertEquals(5L, jobService.getTotalJobsCount());

        Job job = new Job();
        job.setStatus(JobStatus.OPEN);
        when(jobRepository.findAll()).thenReturn(List.of(job));
        assertEquals(1L, jobService.getActiveJobsCount());
    }

    @Test
    void bulkDeleteJobs_Success() {
        jobService.bulkDeleteJobs(List.of(1L, 2L));
        verify(jobRepository).deleteAllById(any());
    }

    @Test
    void getEmployerJobStats_Success() {
        User employer = new User();
        employer.setId(1L);
        Job job = new Job();
        job.setId(10L);
        job.setEmployer(employer);
        job.setStatus(JobStatus.OPEN);
        Applications app = new Applications();
        app.setJob(job);
        app.setAppliedAt(LocalDateTime.now());

        when(jobRepository.findAll()).thenReturn(List.of(job));
        when(applicationRepository.findAll()).thenReturn(List.of(app));

        EmployerStatsResponse stats = jobService.getEmployerJobStats(1L);
        assertNotNull(stats);
        assertEquals(1, stats.getActiveJobs());
        assertEquals(1, stats.getTotalApplications());
    }

    @Test
    void getApplicationCountForJob_Success() {
        Job job = new Job();
        job.setId(10L);
        Applications app = new Applications();
        app.setJob(job);
        when(applicationRepository.findAll()).thenReturn(List.of(app));

        long count = jobService.getApplicationCountForJob(10L);
        assertEquals(1, count);
    }

    @Test
    void postJob_Success() {
        JobPostRequest req = new JobPostRequest();
        req.setEmployerId(1L);
        req.setSkills(List.of("Java"));

        User employerUser = new User();
        employerUser.setId(1L);
        employerUser.setRole(Role.EMPLOYER);
        when(userRepository.findById(1L)).thenReturn(Optional.of(employerUser));
        when(employerRepository.findById(any())).thenReturn(Optional.of(new Employer()));

        Job job = new Job();
        job.setEmployer(employerUser);
        when(jobRepository.save(any())).thenReturn(job);
        when(jobMapper.toDto(any())).thenReturn(new JobResponse());

        JobResponse result = jobService.postJob(req);
        assertNotNull(result);
        verify(jobRepository).save(any());
        verify(jobSkillRepository, atLeastOnce()).save(any());
    }

    @Test
    void closeJob_Success() {
        Job job = new Job();
        User employer = new User();
        employer.setId(1L);
        job.setEmployer(employer);
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));
        when(jobRepository.save(any())).thenReturn(job);
        when(employerRepository.findById(any())).thenReturn(Optional.of(new Employer()));
        when(jobMapper.toDto(any())).thenReturn(new JobResponse());
        jobService.closeJob(1L);
        assertEquals(JobStatus.CLOSED, job.getStatus());
    }

    @Test
    void reopenJob_Success() {
        Job job = new Job();
        User employer = new User();
        employer.setId(1L);
        job.setEmployer(employer);
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));
        when(jobRepository.save(any())).thenReturn(job);
        when(employerRepository.findById(any())).thenReturn(Optional.of(new Employer()));
        when(jobMapper.toDto(any())).thenReturn(new JobResponse());
        jobService.reopenJob(1L);
        assertEquals(JobStatus.OPEN, job.getStatus());
    }

    @Test
    void markJobAsFilled_Success() {
        Job job = new Job();
        User employer = new User();
        employer.setId(1L);
        job.setEmployer(employer);
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));
        when(jobRepository.save(any())).thenReturn(job);
        when(employerRepository.findById(any())).thenReturn(Optional.of(new Employer()));
        when(jobMapper.toDto(any())).thenReturn(new JobResponse());
        jobService.markJobAsFilled(1L);
        assertEquals(JobStatus.FILLED, job.getStatus());
    }

    @Test
    void updateJob_PartialUpdate() {
        Job job = new Job();
        job.setTitle("Old Title");
        User emp = new User();
        emp.setId(1L);
        job.setEmployer(emp);
        when(jobRepository.findById(10L)).thenReturn(Optional.of(job));
        when(employerRepository.findById(any())).thenReturn(Optional.of(new Employer()));

        JobPostRequest req = new JobPostRequest();
        req.setTitle("New Title");
        // Other fields null to test branches

        when(jobRepository.save(any())).thenReturn(job);
        when(jobMapper.toDto(any())).thenReturn(new JobResponse());

        jobService.updateJob(10L, req);
        assertEquals("New Title", job.getTitle());
    }

    @Test
    void updateJob_AllFields() {
        Job job = new Job();
        User emp = new User();
        emp.setId(1L);
        job.setEmployer(emp);
        job.setJobSkills(new ArrayList<>());
        when(jobRepository.findById(10L)).thenReturn(Optional.of(job));
        when(employerRepository.findById(any())).thenReturn(Optional.of(new Employer()));

        JobPostRequest req = new JobPostRequest();
        req.setEmployerId(1L);
        req.setTitle("Title");
        req.setDescription("Desc");
        req.setRequirements("Req");
        req.setSkillsRequired("Skills");
        req.setLocation("Loc");
        req.setSalaryMin(100);
        req.setSalaryMax(200);
        req.setJobType(JobType.FULLTIME);
        req.setExperienceYears(1);
        req.setOpenings(5);
        req.setDeadline(java.time.LocalDate.now());
        req.setSkills(List.of("Java"));

        when(jobRepository.save(any())).thenReturn(job);
        when(jobMapper.toDto(any())).thenReturn(new JobResponse());

        jobService.updateJob(10L, req);

        assertNotNull(job.getTitle());
        assertEquals("Title", job.getTitle());
        assertEquals("Desc", job.getDescription());
    }

    // --- Negative & Edge Case Tests ---

    @Test
    void postJob_EmployerNotFound_Throws() {
        JobPostRequest req = new JobPostRequest();
        req.setEmployerId(99L);
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> jobService.postJob(req));
    }

    @Test
    void postJob_NotEmployerRole_Throws() {
        JobPostRequest req = new JobPostRequest();
        req.setEmployerId(1L);
        User user = new User();
        user.setId(1L);
        user.setRole(Role.JOB_SEEKER);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        assertThrows(RuntimeException.class, () -> jobService.postJob(req));
    }

    @Test
    void postJob_NullSkills_Success() {
        JobPostRequest req = new JobPostRequest();
        req.setEmployerId(1L);
        req.setSkills(null); // no skills

        User employerUser = new User();
        employerUser.setId(1L);
        employerUser.setRole(Role.EMPLOYER);
        when(userRepository.findById(1L)).thenReturn(Optional.of(employerUser));

        Job job = new Job();
        job.setEmployer(employerUser);
        when(jobRepository.save(any())).thenReturn(job);
        when(jobMapper.toDto(any())).thenReturn(new JobResponse());
        when(employerRepository.findById(any())).thenReturn(Optional.of(new Employer()));

        JobResponse result = jobService.postJob(req);
        assertNotNull(result);
        verify(jobSkillRepository, never()).save(any());
    }

    @Test
    void getJobById_NotFound_Throws() {
        when(jobRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> jobService.getJobById(99L));
    }

    @Test
    void updateJobStatus_NotFound_Throws() {
        when(jobRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> jobService.updateJobStatus(99L, JobStatus.CLOSED));
    }

    @Test
    void incrementViewCount_NotFound_Throws() {
        when(jobRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> jobService.incrementViewCount(99L));
    }

    @Test
    void incrementViewCount_NullViews_CreatesNewList() {
        Job job = new Job();
        job.setJobViews(null); // null views
        User emp = new User();
        emp.setId(1L);
        job.setEmployer(emp);
        when(jobRepository.findById(10L)).thenReturn(Optional.of(job));

        jobService.incrementViewCount(10L);

        assertNotNull(job.getJobViews());
        assertEquals(1, job.getJobViews().size());
        verify(jobRepository).save(job);
    }

    @Test
    void updateJob_NotFound_Throws() {
        when(jobRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> jobService.updateJob(99L, new JobPostRequest()));
    }

    @Test
    void searchJobs_NullFilters_ReturnsAll() {
        Job job = new Job();
        job.setStatus(JobStatus.OPEN);
        job.setTitle("Java Developer");
        job.setCreatedAt(LocalDateTime.now());
        User emp = new User();
        emp.setId(1L);
        job.setEmployer(emp);
        when(jobRepository.findAll()).thenReturn(List.of(job));
        when(jobMapper.toDto(any())).thenReturn(new JobResponse());
        when(employerRepository.findById(any())).thenReturn(Optional.of(new Employer()));

        List<JobResponse> results = jobService.searchJobs(null, null, null);
        assertEquals(1, results.size());
    }

    @Test
    void getRecentJobs_NullCreatedAt_HandlesGracefully() {
        Job job1 = new Job();
        job1.setStatus(JobStatus.OPEN);
        job1.setCreatedAt(null); // null createdAt
        User emp1 = new User();
        emp1.setId(1L);
        job1.setEmployer(emp1);

        Job job2 = new Job();
        job2.setStatus(JobStatus.OPEN);
        job2.setCreatedAt(LocalDateTime.now());
        User emp2 = new User();
        emp2.setId(2L);
        job2.setEmployer(emp2);

        when(jobRepository.findAll()).thenReturn(List.of(job1, job2));
        when(jobMapper.toDto(any())).thenReturn(new JobResponse());
        when(employerRepository.findById(any())).thenReturn(Optional.of(new Employer()));

        List<JobResponse> result = jobService.getRecentJobs();
        assertEquals(2, result.size());
    }

    @Test
    void getTrendingJobs_NullViews_HandlesGracefully() {
        Job job = new Job();
        job.setStatus(JobStatus.OPEN);
        job.setJobViews(null); // null views
        User emp = new User();
        emp.setId(1L);
        job.setEmployer(emp);

        when(jobRepository.findAll()).thenReturn(List.of(job));
        when(jobMapper.toDto(any())).thenReturn(new JobResponse());
        when(employerRepository.findById(any())).thenReturn(Optional.of(new Employer()));

        List<JobResponse> result = jobService.getTrendingJobs();
        assertEquals(1, result.size());
    }

    @Test
    void filterJobs_ClosedStatus_Excluded() {
        Job job = new Job();
        job.setStatus(JobStatus.CLOSED);
        when(jobRepository.findAll()).thenReturn(List.of(job));

        List<JobResponse> result = jobService.filterJobs(null, null);
        assertEquals(0, result.size());
    }

    @Test
    void getAllRequiredSkills_NullSkillsList_HandlesGracefully() {
        Job job = new Job();
        job.setJobSkills(null); // null skills
        when(jobRepository.findAll()).thenReturn(List.of(job));

        List<String> result = jobService.getAllRequiredSkills();
        assertEquals(0, result.size());
    }

    @Test
    void getJobsBySkill_NoMatch_ReturnsEmpty() {
        Job job = new Job();
        JobSkill skill = new JobSkill();
        skill.setSkill("Python");
        job.setJobSkills(List.of(skill));
        User emp = new User();
        emp.setId(1L);
        job.setEmployer(emp);
        when(jobRepository.findAll()).thenReturn(List.of(job));

        List<JobResponse> result = jobService.getJobsBySkill("Ruby");
        assertEquals(0, result.size());
    }

    @Test
    void getJobLocations_NullLocation_Excluded() {
        Job job = new Job();
        job.setLocation(null);
        when(jobRepository.findAll()).thenReturn(List.of(job));

        List<String> result = jobService.getJobLocations();
        assertEquals(0, result.size());
    }

    @Test
    void getJobTypes_NullType_Excluded() {
        Job job = new Job();
        job.setJobType(null);
        when(jobRepository.findAll()).thenReturn(List.of(job));

        List<JobType> result = jobService.getJobTypes();
        assertEquals(JobType.values().length, result.size());
    }

    @Test
    void getActiveJobsCount_ClosedJobs_ReturnZero() {
        Job job = new Job();
        job.setStatus(JobStatus.CLOSED);
        when(jobRepository.findAll()).thenReturn(List.of(job));
        assertEquals(0L, jobService.getActiveJobsCount());
    }

    @Test
    void getEmployerJobStats_NoJobs_ReturnsZeros() {
        when(jobRepository.findAll()).thenReturn(new ArrayList<>());
        when(applicationRepository.findAll()).thenReturn(new ArrayList<>());

        EmployerStatsResponse stats = jobService.getEmployerJobStats(99L);
        assertNotNull(stats);
        assertEquals(0, stats.getActiveJobs());
        assertEquals(0, stats.getTotalApplications());
    }

    @Test
    void searchJobs_KeywordNotMatching_ReturnsEmpty() {
        Job job = new Job();
        job.setStatus(JobStatus.OPEN);
        job.setTitle("Python Developer");
        job.setDescription("Backend py");
        job.setLocation("NYC");
        job.setCreatedAt(LocalDateTime.now());
        User emp = new User();
        emp.setId(1L);
        job.setEmployer(emp);
        when(jobRepository.findAll()).thenReturn(List.of(job));

        List<JobResponse> result = jobService.searchJobs("java", null, null);
        assertEquals(0, result.size());
    }

    @Test
    void searchJobs_LocationNotMatching_ReturnsEmpty() {
        Job job = new Job();
        job.setStatus(JobStatus.OPEN);
        job.setTitle("Java Developer");
        job.setLocation("NYC");
        job.setCreatedAt(LocalDateTime.now());
        User emp = new User();
        emp.setId(1L);
        job.setEmployer(emp);
        when(jobRepository.findAll()).thenReturn(List.of(job));

        List<JobResponse> result = jobService.searchJobs(null, "chicago", null);
        assertEquals(0, result.size());
    }

    @Test
    void searchJobs_JobTypeMismatch_ReturnsEmpty() {
        Job job = new Job();
        job.setStatus(JobStatus.OPEN);
        job.setJobType(JobType.FULLTIME);
        job.setCreatedAt(LocalDateTime.now());
        User emp = new User();
        emp.setId(1L);
        job.setEmployer(emp);
        when(jobRepository.findAll()).thenReturn(List.of(job));

        List<JobResponse> result = jobService.searchJobs(null, null, JobType.PARTTIME);
        assertEquals(0, result.size());
    }

    @Test
    void filterJobs_SalaryBelowMin_ReturnsEmpty() {
        Job job = new Job();
        job.setStatus(JobStatus.OPEN);
        job.setSalaryMin(30000);
        User emp = new User();
        emp.setId(1L);
        job.setEmployer(emp);
        when(jobRepository.findAll()).thenReturn(List.of(job));

        List<JobResponse> result = jobService.filterJobs(50000, null);
        assertEquals(0, result.size());
    }

    @Test
    void filterJobs_JobTypeFilter_Success() {
        Job job = new Job();
        job.setStatus(JobStatus.OPEN);
        job.setJobType(JobType.FULLTIME);
        User emp = new User();
        emp.setId(1L);
        job.setEmployer(emp);
        when(jobRepository.findAll()).thenReturn(List.of(job));
        when(jobMapper.toDto(any())).thenReturn(new JobResponse());
        when(employerRepository.findById(any())).thenReturn(Optional.of(new Employer()));

        List<JobResponse> result = jobService.filterJobs(null, JobType.FULLTIME);
        assertEquals(1, result.size());
    }

    @Test
    void getEmployerJobStats_FilledAndNewApps() {
        User employer = new User();
        employer.setId(1L);
        Job job1 = new Job();
        job1.setId(10L);
        job1.setEmployer(employer);
        job1.setStatus(JobStatus.FILLED);
        Job job2 = new Job();
        job2.setId(11L);
        job2.setEmployer(employer);
        job2.setStatus(JobStatus.OPEN);
        Applications app1 = new Applications();
        app1.setJob(job1);
        app1.setAppliedAt(LocalDateTime.now().minusDays(5)); // old app
        Applications app2 = new Applications();
        app2.setJob(job2);
        app2.setAppliedAt(LocalDateTime.now()); // recent app

        when(jobRepository.findAll()).thenReturn(List.of(job1, job2));
        when(applicationRepository.findAll()).thenReturn(List.of(app1, app2));

        EmployerStatsResponse stats = jobService.getEmployerJobStats(1L);
        assertEquals(1, stats.getFilledPositions());
        assertEquals(1, stats.getActiveJobs());
        assertEquals(2, stats.getTotalApplications());
        assertEquals(1, stats.getNewApplications());
    }
}
