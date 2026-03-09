package org.example.revhire.service;

import org.example.revhire.dto.request.JobPostRequest;
import org.example.revhire.dto.response.*;
import org.example.revhire.enums.JobStatus;
import org.example.revhire.enums.JobType;
import org.example.revhire.enums.Role;
import org.example.revhire.model.*;
import org.example.revhire.repository.EmployerRepository;
import org.example.revhire.repository.JobRepository;
import org.example.revhire.repository.JobSkillRepository;
import org.example.revhire.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final JobSkillRepository jobSkillRepository;
    private final UserRepository userRepository;
    private final EmployerRepository employerRepository;
    private final org.example.revhire.repository.ApplicationRepository applicationRepository;
    private final org.example.revhire.mapper.JobMapper jobMapper;
    private final NotificationService notificationService;

    public JobServiceImpl(JobRepository jobRepository, JobSkillRepository jobSkillRepository,
                          UserRepository userRepository, EmployerRepository employerRepository,
                          org.example.revhire.repository.ApplicationRepository applicationRepository,
                          org.example.revhire.mapper.JobMapper jobMapper,
                          NotificationService notificationService) {
        this.jobRepository = jobRepository;
        this.jobSkillRepository = jobSkillRepository;
        this.userRepository = userRepository;
        this.employerRepository = employerRepository;
        this.applicationRepository = applicationRepository;
        this.jobMapper = jobMapper;
        this.notificationService = notificationService;
    }

    @Override
    @Transactional
    @org.springframework.cache.annotation.CacheEvict(value = "jobs", allEntries = true)
    public JobResponse postJob(JobPostRequest req) {
        User employerUser = userRepository.findById(req.getEmployerId())
                .orElseThrow(() -> new RuntimeException("Employer not found"));

        if (employerUser.getRole() != Role.EMPLOYER) {
            throw new RuntimeException("User is not an employer");
        }

        Job job = new Job();
        job.setEmployer(employerUser);
        job.setTitle(req.getTitle());
        job.setDescription(req.getDescription());
        job.setRequirements(req.getRequirements());
        job.setRequiredEducationLevel(req.getRequiredEducationLevel());
        job.setSkillsRequired(req.getSkillsRequired());
        job.setLocation(req.getLocation());
        job.setSalaryMin(req.getSalaryMin());
        job.setSalaryMax(req.getSalaryMax());
        job.setJobType(req.getJobType());
        job.setExperienceYears(req.getExperienceYears());
        job.setMaxExperienceYears(req.getMaxExperienceYears());
        job.setOpenings(req.getOpenings());
        job.setDeadline(req.getDeadline());

        Job savedJob = jobRepository.save(job);

        if (req.getSkills() != null) {
            for (String skillName : req.getSkills()) {
                JobSkill jobSkill = new JobSkill();
                jobSkill.setJob(savedJob);
                jobSkill.setSkill(skillName);
                jobSkillRepository.save(jobSkill);
            }
        }

        notificationService.createNotification(employerUser.getId(),
                "Job posted successfully: " + savedJob.getTitle(),
                "JOB_POSTED");

        return mapToDto(savedJob);
    }

    @Override
    @org.springframework.cache.annotation.Cacheable(value = "jobs", key = "#id")
    public JobResponse getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        incrementViewCount(id);
        return mapToDto(job);
    }

    @Override
    @org.springframework.cache.annotation.Cacheable(value = "jobs", key = "'all'")
    public List<JobResponse> getAllJobs() {
        return jobRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<JobResponse> searchJobs(String keyword, String location, JobType jobType) {
        String normalizedKeyword = keyword == null ? null : keyword.trim().toLowerCase(Locale.ROOT);
        if (normalizedKeyword != null && normalizedKeyword.isBlank()) {
            normalizedKeyword = null;
        }

        String normalizedLocation = location == null ? null : location.trim().toLowerCase(Locale.ROOT);
        if (normalizedLocation != null && normalizedLocation.isBlank()) {
            normalizedLocation = null;
        }
        final String kw = normalizedKeyword;
        final String loc = normalizedLocation;

        return jobRepository.findAll().stream()
                .filter(j -> j.getStatus() == JobStatus.OPEN)
                .filter(j -> kw == null || matchesKeyword(j, kw))
                .filter(j -> loc == null || (j.getLocation() != null && j.getLocation().toLowerCase().contains(loc)))
                .filter(j -> jobType == null || j.getJobType() == jobType)
                .sorted((j1, j2) -> j2.getCreatedAt().compareTo(j1.getCreatedAt()))
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<JobResponse> getJobsByEmployer(Long employerId) {
        return jobRepository.findByEmployerId(employerId).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    @org.springframework.cache.annotation.CacheEvict(value = "jobs", allEntries = true)
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    @Override
    @org.springframework.cache.annotation.CacheEvict(value = "jobs", allEntries = true)
    public JobResponse updateJobStatus(Long id, JobStatus status) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new RuntimeException("Job not found"));
        job.setStatus(status);
        Job saved = jobRepository.save(job);
        notificationService.createNotification(job.getEmployer().getId(),
                "Job status changed to " + status + " for " + job.getTitle(),
                "JOB_STATUS");
        return mapToDto(saved);
    }

    @Override
    @Transactional
    public void incrementViewCount(Long id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new RuntimeException("Job not found"));
        JobView jobView = new JobView();
        jobView.setJob(job);
        if (job.getJobViews() == null) {
            job.setJobViews(new ArrayList<>());
        }
        job.getJobViews().add(jobView);
        jobRepository.save(job);
    }

    @Override
    public JobResponse closeJob(Long id) {
        return updateJobStatus(id, JobStatus.CLOSED);
    }

    @Override
    public JobResponse reopenJob(Long id) {
        return updateJobStatus(id, JobStatus.OPEN);
    }

    @Override
    public JobResponse markJobAsFilled(Long id) {
        return updateJobStatus(id, JobStatus.FILLED);
    }

    @Override
    @Transactional
    @org.springframework.cache.annotation.CacheEvict(value = "jobs", allEntries = true)
    public JobResponse updateJob(Long id, JobPostRequest req) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new RuntimeException("Job not found"));

        if (req.getTitle() != null)
            job.setTitle(req.getTitle());
        if (req.getDescription() != null)
            job.setDescription(req.getDescription());
        if (req.getRequirements() != null)
            job.setRequirements(req.getRequirements());
        if (req.getRequiredEducationLevel() != null)
            job.setRequiredEducationLevel(req.getRequiredEducationLevel());
        if (req.getSkillsRequired() != null)
            job.setSkillsRequired(req.getSkillsRequired());
        if (req.getLocation() != null)
            job.setLocation(req.getLocation());
        if (req.getSalaryMin() != null)
            job.setSalaryMin(req.getSalaryMin());
        if (req.getSalaryMax() != null)
            job.setSalaryMax(req.getSalaryMax());
        if (req.getJobType() != null)
            job.setJobType(req.getJobType());
        if (req.getExperienceYears() != null)
            job.setExperienceYears(req.getExperienceYears());
        if (req.getMaxExperienceYears() != null)
            job.setMaxExperienceYears(req.getMaxExperienceYears());
        if (req.getOpenings() != null)
            job.setOpenings(req.getOpenings());
        if (req.getDeadline() != null)
            job.setDeadline(req.getDeadline());

        if (req.getSkills() != null) {
            // Remove existing skills
            job.getJobSkills().clear();
            // Add new skills
            for (String skillName : req.getSkills()) {
                JobSkill jobSkill = new JobSkill();
                jobSkill.setJob(job);
                jobSkill.setSkill(skillName);
                job.getJobSkills().add(jobSkill);
            }
        }
        Job saved = jobRepository.save(job);
        notificationService.createNotification(saved.getEmployer().getId(),
                "Job updated: " + saved.getTitle(),
                "JOB_UPDATED");
        return mapToDto(saved);
    }

    @Override
    public List<JobResponse> getRecentJobs() {
        return jobRepository.findAll().stream()
                .filter(j -> j.getStatus() == JobStatus.OPEN)
                .sorted((j1, j2) -> {
                    if (j1.getCreatedAt() == null || j2.getCreatedAt() == null)
                        return 0;
                    return j2.getCreatedAt().compareTo(j1.getCreatedAt());
                })
                .limit(10)
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<JobResponse> getTrendingJobs() {
        return jobRepository.findAll().stream()
                .filter(j -> j.getStatus() == JobStatus.OPEN)
                .sorted((j1, j2) -> Integer.compare(
                        j2.getJobViews() != null ? j2.getJobViews().size() : 0,
                        j1.getJobViews() != null ? j1.getJobViews().size() : 0))
                .limit(10)
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<JobResponse> filterJobs(Integer minSalary, JobType jobType) {
        return jobRepository.findAll().stream()
                .filter(j -> j.getStatus() == JobStatus.OPEN)
                .filter(j -> minSalary == null || (j.getSalaryMin() != null && j.getSalaryMin() >= minSalary))
                .filter(j -> jobType == null || j.getJobType() == jobType)
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllRequiredSkills() {
        return jobRepository.findAll().stream()
                .flatMap(j -> j.getJobSkills() != null ? j.getJobSkills().stream() : java.util.stream.Stream.empty())
                .map(JobSkill::getSkill)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<JobResponse> getJobsBySkill(String skillName) {
        String target = skillName.toLowerCase();
        return jobRepository.findAll().stream()
                .filter(j -> j.getJobSkills() != null && j.getJobSkills().stream()
                        .anyMatch(s -> s.getSkill() != null && s.getSkill().toLowerCase().equals(target)))
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getJobLocations() {
        return jobRepository.findAll().stream()
                .map(Job::getLocation)
                .filter(l -> l != null)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<JobType> getJobTypes() {
        return Arrays.asList(JobType.values());
    }

    @Override
    public long getTotalJobsCount() {
        return jobRepository.count();
    }

    @Override
    public long getActiveJobsCount() {
        return jobRepository.findAll().stream()
                .filter(j -> j.getStatus() == JobStatus.OPEN)
                .count();
    }

    @Override
    @Transactional
    public void bulkDeleteJobs(List<Long> ids) {
        jobRepository.deleteAllById(ids);
    }

    @Override
    public org.example.revhire.dto.response.EmployerStatsResponse getEmployerJobStats(Long employerId) {
        org.example.revhire.dto.response.EmployerStatsResponse stats = new org.example.revhire.dto.response.EmployerStatsResponse();
        List<Job> employerJobs = jobRepository.findAll().stream()
                .filter(j -> j.getEmployer().getId().equals(employerId))
                .toList();

        stats.setActiveJobs(employerJobs.stream().filter(j -> j.getStatus() == JobStatus.OPEN).count());
        stats.setFilledPositions(employerJobs.stream().filter(j -> j.getStatus() == JobStatus.FILLED).count());

        List<Applications> apps = applicationRepository.findAll().stream()
                .filter(a -> employerJobs.stream().anyMatch(j -> j.getId().equals(a.getJob().getId())))
                .toList();

        stats.setTotalApplications((long) apps.size());
        stats.setNewApplications(apps.stream()
                .filter(a -> a.getAppliedAt() != null
                        && a.getAppliedAt().isAfter(java.time.LocalDateTime.now().minusHours(48)))
                .count());

        return stats;
    }

    @Override
    public long getApplicationCountForJob(Long jobId) {
        return applicationRepository.findAll().stream()
                .filter(a -> a.getJob().getId().equals(jobId))
                .count();
    }

    private JobResponse mapToDto(Job job) {
        JobResponse dto = jobMapper.toDto(job);

        employerRepository.findByUserReferenceId(job.getEmployer().getId())
                .ifPresent(emp -> {
                    String companyName = emp.getCompanyName();
                    String website = normalizeWebsite(emp.getWebsite());
                    dto.setCompanyName(companyName);
                    dto.setCompanyWebsite(website);
                    dto.setCompanyLogoUrl(buildCompanyLogoUrl(website, companyName));
                });

        return dto;
    }

    private String normalizeWebsite(String website) {
        if (!StringUtils.hasText(website)) {
            return null;
        }
        String trimmed = website.trim();
        if (trimmed.startsWith("http://") || trimmed.startsWith("https://")) {
            return trimmed;
        }
        return "https://" + trimmed;
    }

    private String buildCompanyLogoUrl(String website, String companyName) {
        String domain = extractDomain(website);
        if (StringUtils.hasText(domain)) {
            return "https://logo.clearbit.com/" + domain;
        }
        String safeName = StringUtils.hasText(companyName) ? companyName.trim() : "Company";
        String encoded = java.net.URLEncoder.encode(safeName, java.nio.charset.StandardCharsets.UTF_8);
        return "https://ui-avatars.com/api/?name=" + encoded
                + "&background=0F172A&color=ffffff&bold=true&size=128";
    }

    private String extractDomain(String website) {
        if (!StringUtils.hasText(website)) {
            return null;
        }
        try {
            java.net.URI uri = new java.net.URI(website);
            String host = uri.getHost();
            if (!StringUtils.hasText(host)) {
                return null;
            }
            return host.toLowerCase(Locale.ROOT);
        } catch (Exception ignored) {
            return null;
        }
    }

    private boolean matchesKeyword(Job job, String keyword) {
        String companyName = employerRepository.findByUserReferenceId(job.getEmployer().getId())
                .map(Employer::getCompanyName)
                .orElse("");

        String jobSkills = job.getJobSkills() == null
                ? ""
                : job.getJobSkills().stream()
                .map(JobSkill::getSkill)
                .filter(StringUtils::hasText)
                .collect(Collectors.joining(" "));

        String searchable = String.join(" ",
                        valueOrEmpty(job.getTitle()),
                        valueOrEmpty(job.getDescription()),
                        valueOrEmpty(job.getRequirements()),
                        valueOrEmpty(job.getSkillsRequired()),
                        valueOrEmpty(companyName),
                        valueOrEmpty(jobSkills))
                .toLowerCase(Locale.ROOT);

        return searchable.contains(keyword);
    }

    private String valueOrEmpty(String value) {
        return value == null ? "" : value;
    }
}
