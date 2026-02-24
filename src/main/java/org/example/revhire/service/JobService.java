package org.example.revhire.service;

import org.example.revhire.dto.request.JobPostRequest;
import org.example.revhire.dto.response.JobResponse;
import org.example.revhire.enums.JobStatus;
import org.example.revhire.enums.JobType;

import java.util.List;

public interface JobService {
    JobResponse postJob(JobPostRequest jobPostRequest);

    JobResponse getJobById(Long id);

    List<JobResponse> getAllJobs();

    List<JobResponse> searchJobs(String keyword, String location, JobType jobType);

    List<JobResponse> getJobsByEmployer(Long employerId);

    void deleteJob(Long id);

    JobResponse updateJobStatus(Long id, JobStatus status);

    void incrementViewCount(Long id);

    JobResponse closeJob(Long id);

    JobResponse reopenJob(Long id);

    JobResponse markJobAsFilled(Long id);

    JobResponse updateJob(Long id, JobPostRequest jobPostRequest);

    List<JobResponse> getRecentJobs();

    List<JobResponse> getTrendingJobs();

    List<JobResponse> filterJobs(Integer minSalary, JobType jobType);

    List<String> getAllRequiredSkills();

    List<JobResponse> getJobsBySkill(String skillName);

    List<String> getJobLocations();

    List<JobType> getJobTypes();

    long getTotalJobsCount();

    long getActiveJobsCount();

    void bulkDeleteJobs(List<Long> ids);

    org.example.revhire.dto.response.EmployerStatsResponse getEmployerJobStats(Long employerId);

    long getApplicationCountForJob(Long jobId);
}
