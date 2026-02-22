package org.example.revhire.controller;

import org.example.revhire.dto.response.ApiResponse;
import org.example.revhire.dto.request.JobPostRequest;
import org.example.revhire.dto.response.JobResponse;
import org.example.revhire.enums.JobStatus;
import org.example.revhire.enums.JobType;
import org.example.revhire.service.JobService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<JobResponse>> postJob(@Valid @RequestBody JobPostRequest jobPostRequest) {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Job posted successfully", jobService.postJob(jobPostRequest)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<JobResponse>> updateJob(@PathVariable Long id,
                                                              @RequestBody JobPostRequest jobPostRequest) {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Job updated successfully", jobService.updateJob(id, jobPostRequest)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<JobResponse>> getJobById(@PathVariable Long id) {
        JobResponse jobResponse = jobService.getJobById(id);
        jobResponse.add(org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo(
                        org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn(JobController.class).getJobById(id))
                .withSelfRel());
        jobResponse.add(org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo(
                        org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn(JobController.class)
                                .updateJobStatus(id, null))
                .withRel("update-status"));
        return ResponseEntity.ok(new ApiResponse<>(true, "Job retrieved", jobResponse));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<JobResponse>>> getAllJobs() {
        List<JobResponse> jobs = jobService.getAllJobs();
        jobs.forEach(job -> job.add(org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo(
                        org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn(JobController.class)
                                .getJobById(job.getId()))
                .withSelfRel()));
        return ResponseEntity.ok(new ApiResponse<>(true, "All jobs retrieved", jobs));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<JobResponse>>> searchJobs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) JobType jobType) {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Search results", jobService.searchJobs(keyword, location, jobType)));
    }

    @GetMapping("/employer/{employerId}")
    public ResponseEntity<ApiResponse<List<JobResponse>>> getJobsByEmployer(@PathVariable Long employerId) {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Employer jobs retrieved", jobService.getJobsByEmployer(employerId)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Job deleted"));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<JobResponse>> updateJobStatus(@PathVariable Long id,
                                                                    @RequestParam JobStatus status) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Status updated", jobService.updateJobStatus(id, status)));
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<ApiResponse<JobResponse>> closeJob(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Job closed", jobService.closeJob(id)));
    }

    @PutMapping("/{id}/reopen")
    public ResponseEntity<ApiResponse<JobResponse>> reopenJob(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Job reopened", jobService.reopenJob(id)));
    }

    @PutMapping("/{id}/mark-filled")
    public ResponseEntity<ApiResponse<JobResponse>> markAsFilled(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Job marked as filled", jobService.markJobAsFilled(id)));
    }

    @PostMapping("/{id}/increment-view")
    public ResponseEntity<ApiResponse<Void>> incrementViewCount(@PathVariable Long id) {
        jobService.incrementViewCount(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "View count incremented"));
    }

    @GetMapping("/recent")
    public ResponseEntity<ApiResponse<List<JobResponse>>> getRecentJobs() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Recent jobs retrieved", jobService.getRecentJobs()));
    }

    @GetMapping("/trending")
    public ResponseEntity<ApiResponse<List<JobResponse>>> getTrendingJobs() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Trending jobs retrieved", jobService.getTrendingJobs()));
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<JobResponse>>> filterJobs(
            @RequestParam(required = false) Integer minSalary,
            @RequestParam(required = false) JobType jobType) {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Filtered jobs retrieved", jobService.filterJobs(minSalary, jobType)));
    }

    @GetMapping("/skills")
    public ResponseEntity<ApiResponse<List<String>>> getAllSkills() {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "All required skills retrieved", jobService.getAllRequiredSkills()));
    }

    @GetMapping("/by-skill")
    public ResponseEntity<ApiResponse<List<JobResponse>>> getJobsBySkill(@RequestParam String skill) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Jobs for skill retrieved", jobService.getJobsBySkill(skill)));
    }

    @GetMapping("/locations")
    public ResponseEntity<ApiResponse<List<String>>> getJobLocations() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Job locations retrieved", jobService.getJobLocations()));
    }

    @GetMapping("/types")
    public ResponseEntity<ApiResponse<List<JobType>>> getJobTypes() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Job types retrieved", jobService.getJobTypes()));
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> getTotalJobsCount() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Total jobs count retrieved", jobService.getTotalJobsCount()));
    }

    @GetMapping("/count/active")
    public ResponseEntity<ApiResponse<Long>> getActiveJobsCount() {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Active jobs count retrieved", jobService.getActiveJobsCount()));
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<ApiResponse<Void>> bulkDeleteJobs(@RequestBody List<Long> ids) {
        jobService.bulkDeleteJobs(ids);
        return ResponseEntity.ok(new ApiResponse<>(true, "Jobs deleted in bulk"));
    }

    @GetMapping("/{id}/application-count")
    public ResponseEntity<ApiResponse<Long>> getApplicationCount(@PathVariable Long id) {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Application count retrieved", jobService.getApplicationCountForJob(id)));
    }
}
