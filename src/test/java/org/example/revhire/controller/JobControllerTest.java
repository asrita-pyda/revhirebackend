package org.example.revhire.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.revhire.dto.request.JobPostRequest;
import org.example.revhire.dto.response.JobResponse;
import org.example.revhire.enums.JobType;
import org.example.revhire.service.JobService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JobController.class)
@AutoConfigureMockMvc(addFilters = false)
class JobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobService jobService;

    @MockBean
    private org.example.revhire.config.JwtUtils jwtUtils;

    @MockBean
    private org.example.revhire.repository.UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void postJob_Success() throws Exception {
        JobPostRequest req = new JobPostRequest();
        req.setEmployerId(1L);
        req.setTitle("Java Developer");
        req.setJobType(JobType.FULLTIME);
        when(jobService.postJob(any())).thenReturn(new JobResponse());
        mockMvc.perform(post("/api/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void updateJob_Success() throws Exception {
        JobPostRequest req = new JobPostRequest();
        when(jobService.updateJob(eq(1L), any())).thenReturn(new JobResponse());
        mockMvc.perform(put("/api/jobs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void getJobById_Success() throws Exception {
        JobResponse resp = new JobResponse();
        resp.setId(1L);
        when(jobService.getJobById(1L)).thenReturn(resp);
        mockMvc.perform(get("/api/jobs/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllJobs_Success() throws Exception {
        JobResponse resp = new JobResponse();
        resp.setId(1L);
        when(jobService.getAllJobs()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/jobs"))
                .andExpect(status().isOk());
    }

    @Test
    void searchJobs_Success() throws Exception {
        when(jobService.searchJobs(any(), any(), any())).thenReturn(List.of(new JobResponse()));
        mockMvc.perform(get("/api/jobs/search")
                        .param("keyword", "java")
                        .param("location", "NYC")
                        .param("jobType", "FULLTIME"))
                .andExpect(status().isOk());
    }

    @Test
    void getJobsByEmployer_Success() throws Exception {
        when(jobService.getJobsByEmployer(1L)).thenReturn(List.of(new JobResponse()));
        mockMvc.perform(get("/api/jobs/employer/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteJob_Success() throws Exception {
        mockMvc.perform(delete("/api/jobs/1"))
                .andExpect(status().isOk());
    }

    @Test
    void updateJobStatus_Success() throws Exception {
        when(jobService.updateJobStatus(eq(1L), any())).thenReturn(new JobResponse());
        mockMvc.perform(patch("/api/jobs/1/status").param("status", "OPEN"))
                .andExpect(status().isOk());
    }

    @Test
    void closeJob_Success() throws Exception {
        when(jobService.closeJob(1L)).thenReturn(new JobResponse());
        mockMvc.perform(put("/api/jobs/1/close"))
                .andExpect(status().isOk());
    }

    @Test
    void reopenJob_Success() throws Exception {
        when(jobService.reopenJob(1L)).thenReturn(new JobResponse());
        mockMvc.perform(put("/api/jobs/1/reopen"))
                .andExpect(status().isOk());
    }

    @Test
    void markAsFilled_Success() throws Exception {
        when(jobService.markJobAsFilled(1L)).thenReturn(new JobResponse());
        mockMvc.perform(put("/api/jobs/1/mark-filled"))
                .andExpect(status().isOk());
    }

    @Test
    void incrementViewCount_Success() throws Exception {
        mockMvc.perform(post("/api/jobs/1/increment-view"))
                .andExpect(status().isOk());
    }

    @Test
    void getRecentJobs_Success() throws Exception {
        when(jobService.getRecentJobs()).thenReturn(List.of(new JobResponse()));
        mockMvc.perform(get("/api/jobs/recent"))
                .andExpect(status().isOk());
    }

    @Test
    void getTrendingJobs_Success() throws Exception {
        when(jobService.getTrendingJobs()).thenReturn(List.of(new JobResponse()));
        mockMvc.perform(get("/api/jobs/trending"))
                .andExpect(status().isOk());
    }

    @Test
    void filterJobs_Success() throws Exception {
        when(jobService.filterJobs(anyInt(), any())).thenReturn(List.of(new JobResponse()));
        mockMvc.perform(get("/api/jobs/filter").param("minSalary", "1000").param("jobType", "CONTRACT"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllSkills_Success() throws Exception {
        when(jobService.getAllRequiredSkills()).thenReturn(List.of("Java"));
        mockMvc.perform(get("/api/jobs/skills"))
                .andExpect(status().isOk());
    }

    @Test
    void getJobsBySkill_Success() throws Exception {
        when(jobService.getJobsBySkill("Java")).thenReturn(List.of(new JobResponse()));
        mockMvc.perform(get("/api/jobs/by-skill").param("skill", "Java"))
                .andExpect(status().isOk());
    }

    @Test
    void getJobLocations_Success() throws Exception {
        when(jobService.getJobLocations()).thenReturn(List.of("NYC"));
        mockMvc.perform(get("/api/jobs/locations"))
                .andExpect(status().isOk());
    }

    @Test
    void getJobTypes_Success() throws Exception {
        when(jobService.getJobTypes()).thenReturn(List.of(JobType.FULLTIME));
        mockMvc.perform(get("/api/jobs/types"))
                .andExpect(status().isOk());
    }

    @Test
    void getTotalJobsCount_Success() throws Exception {
        when(jobService.getTotalJobsCount()).thenReturn(10L);
        mockMvc.perform(get("/api/jobs/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(10));
    }

    @Test
    void getActiveJobsCount_Success() throws Exception {
        when(jobService.getActiveJobsCount()).thenReturn(5L);
        mockMvc.perform(get("/api/jobs/count/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(5));
    }

    @Test
    void bulkDeleteJobs_Success() throws Exception {
        mockMvc.perform(delete("/api/jobs/bulk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1, 2]"))
                .andExpect(status().isOk());
    }

    @Test
    void getApplicationCount_Success() throws Exception {
        when(jobService.getApplicationCountForJob(1L)).thenReturn(3L);
        mockMvc.perform(get("/api/jobs/1/application-count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(3));
    }
}
