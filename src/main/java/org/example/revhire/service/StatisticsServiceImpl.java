package org.example.revhire.service;

import org.example.revhire.dto.response.*;
import org.example.revhire.enums.JobStatus;
import org.example.revhire.enums.ApplicationStatus;
import org.example.revhire.model.*;
import org.example.revhire.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final ResumeObjectiveRepository resumeRepository;

    public StatisticsServiceImpl(JobRepository jobRepository,
                                 ApplicationRepository applicationRepository,
                                 UserRepository userRepository,
                                 ResumeObjectiveRepository resumeRepository) {
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.resumeRepository = resumeRepository;
    }

    @Override
    public EmployerStatsResponse getEmployerStats(Long employerId) {
        EmployerStatsResponse stats = new EmployerStatsResponse();
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
                .filter(a -> a.getStatus() == ApplicationStatus.APPLIED)
                .count());

        return stats;
    }

    @Override
    public PlatformStatsResponse getPlatformOverview() {
        PlatformStatsResponse stats = new PlatformStatsResponse();

        List<User> users = userRepository.findAll();
        List<Job> jobs = jobRepository.findAll();
        List<Applications> apps = applicationRepository.findAll();

        stats.setTotalUsers((long) users.size());
        stats.setTotalJobs((long) jobs.size());
        stats.setTotalApplications((long) apps.size());
        stats.setTotalResumes(resumeRepository.count());

        stats.setUsersByRole(users.stream()
                .collect(Collectors.groupingBy(u -> u.getRole().name(), Collectors.counting())));

        stats.setJobsByStatus(jobs.stream()
                .collect(Collectors.groupingBy(j -> j.getStatus().name(), Collectors.counting())));

        LocalDateTime todayStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        stats.setTodayApplications(apps.stream()
                .filter(a -> a.getAppliedAt() != null && a.getAppliedAt().isAfter(todayStart))
                .count());

        return stats;
    }

    @Override
    public JobStatsResponse getJobAnalytics() {
        JobStatsResponse stats = new JobStatsResponse();
        List<Job> jobs = jobRepository.findAll();

        stats.setTotalJobs((long) jobs.size());
        stats.setOpenJobs(jobs.stream().filter(j -> j.getStatus() == JobStatus.OPEN).count());
        stats.setFilledJobs(jobs.stream().filter(j -> j.getStatus() == JobStatus.FILLED).count());

        stats.setJobsByType(jobs.stream()
                .collect(Collectors.groupingBy(j -> j.getJobType().name(), Collectors.counting())));

        stats.setJobsByLocation(jobs.stream()
                .filter(j -> j.getLocation() != null)
                .collect(Collectors.groupingBy(Job::getLocation, Collectors.counting())));

        stats.setJobsByExperience(jobs.stream()
                .collect(Collectors.groupingBy(j -> String.valueOf(j.getExperienceYears()),
                        Collectors.counting())));

        return stats;
    }

    @Override
    public List<Map<String, Object>> getApplicationTrends() {
        List<Applications> apps = applicationRepository.findAll();


        Map<String, Long> byStatus = apps.stream()
                .collect(Collectors.groupingBy(a -> a.getStatus().name(), Collectors.counting()));

        return byStatus.entrySet().stream()
                .map(e -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("status", e.getKey());
                    m.put("count", e.getValue());
                    return m;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getEmployerActivity(Long employerId) {
        Map<String, Object> activity = new HashMap<>();
        List<Job> employerJobs = jobRepository.findAll().stream()
                .filter(j -> j.getEmployer().getId().equals(employerId))
                .toList();

        activity.put("totalJobs", employerJobs.size());
        activity.put("activeJobs", employerJobs.stream().filter(j -> j.getStatus() == JobStatus.OPEN).count());

        long totalApps = applicationRepository.findAll().stream()
                .filter(a -> employerJobs.stream().anyMatch(j -> j.getId().equals(a.getJob().getId())))
                .count();
        activity.put("totalApplicationsReceived", totalApps);

        return activity;
    }

    @Override
    public Map<String, Object> getSeekerEngagement(Long userId) {
        Map<String, Object> engagement = new HashMap<>();
        List<Applications> seekerApps = applicationRepository.findAll().stream()
                .filter(a -> a.getSeeker().getId().equals(userId))
                .toList();

        engagement.put("totalApplications", seekerApps.size());
        engagement.put("shortlistedCount", seekerApps.stream()
                .filter(a -> a.getStatus() == ApplicationStatus.SHORTLISTED)
                .count());
        engagement.put("rejectedCount", seekerApps.stream()
                .filter(a -> a.getStatus() == ApplicationStatus.REJECTED)
                .count());

        return engagement;
    }
}
