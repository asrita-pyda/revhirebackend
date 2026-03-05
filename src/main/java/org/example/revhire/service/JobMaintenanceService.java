package org.example.revhire.service;

import org.example.revhire.enums.JobStatus;
import org.example.revhire.model.Job;
import org.example.revhire.repository.JobRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class JobMaintenanceService {

    private final JobRepository jobRepository;
    private final NotificationService notificationService;

    public JobMaintenanceService(JobRepository jobRepository, NotificationService notificationService) {
        this.jobRepository = jobRepository;
        this.notificationService = notificationService;
    }


    @Scheduled(cron = "0 0 1 * * *")
    public void autoCloseExpiredJobs() {
        List<Job> expiredOpenJobs = jobRepository.findByStatusAndDeadlineBefore(JobStatus.OPEN, LocalDate.now());
        for (Job job : expiredOpenJobs) {
            job.setStatus(JobStatus.CLOSED);
            jobRepository.save(job);
            notificationService.createNotification(
                    job.getEmployer().getId(),
                    "Job \"" + job.getTitle() + "\" was auto-closed because deadline passed.",
                    "JOB_AUTO_CLOSED"
            );
        }
    }
}
