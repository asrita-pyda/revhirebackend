package org.example.revhire.service;

import org.example.revhire.dto.request.ApplicationRequest;
import org.example.revhire.dto.request.BulkStatusUpdateRequest;
import org.example.revhire.dto.response.*;
import org.example.revhire.enums.ApplicationStatus;
import org.example.revhire.model.*;
import org.example.revhire.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final ResumeFileRepository resumeFileRepository;
    private final ResumeObjectiveRepository resumeObjectiveRepository;
    private final ResumeEducationRepository resumeEducationRepository;
    private final ResumeExperienceRepository resumeExperienceRepository;
    private final ResumeSkillRepository resumeSkillRepository;
    private final ResumeProjectRepository resumeProjectRepository;
    private final ResumeCertificationRepository resumeCertificationRepository;
    private final ApplicationStatusHistoryRepository statusHistoryRepo;

    private final ApplicationNoteRepository noteRepo;
    private final NotificationService notificationService;
    private final WithdrawalReasonsRepository withdrawalReasonsRepository;
    private final org.example.revhire.mapper.ApplicationMapper applicationMapper;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, JobRepository jobRepository,
                                  UserRepository userRepository, ResumeFileRepository resumeFileRepository,
                                  ResumeObjectiveRepository resumeObjectiveRepository,
                                  ResumeEducationRepository resumeEducationRepository,
                                  ResumeExperienceRepository resumeExperienceRepository,
                                  ResumeSkillRepository resumeSkillRepository,
                                  ResumeProjectRepository resumeProjectRepository,
                                  ResumeCertificationRepository resumeCertificationRepository,
                                  ApplicationStatusHistoryRepository statusHistoryRepo, ApplicationNoteRepository noteRepo,
                                  NotificationService notificationService,
                                  WithdrawalReasonsRepository withdrawalReasonsRepository,
                                  org.example.revhire.mapper.ApplicationMapper applicationMapper) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.resumeFileRepository = resumeFileRepository;
        this.resumeObjectiveRepository = resumeObjectiveRepository;
        this.resumeEducationRepository = resumeEducationRepository;
        this.resumeExperienceRepository = resumeExperienceRepository;
        this.resumeSkillRepository = resumeSkillRepository;
        this.resumeProjectRepository = resumeProjectRepository;
        this.resumeCertificationRepository = resumeCertificationRepository;
        this.statusHistoryRepo = statusHistoryRepo;
        this.noteRepo = noteRepo;
        this.notificationService = notificationService;
        this.withdrawalReasonsRepository = withdrawalReasonsRepository;
        this.applicationMapper = applicationMapper;
    }

    @Override
    @Transactional
    public ApplicationResponse applyForJob(ApplicationRequest req) {
        Job job = jobRepository.findById(req.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found"));

        User seeker = userRepository.findById(req.getSeekerId())
                .orElseThrow(() -> new RuntimeException("Seeker not found"));

        Applications applications = applicationRepository.findAll().stream()
                .filter(a -> a.getJob().getId().equals(req.getJobId()) && a.getSeeker().getId().equals(req.getSeekerId()))
                .findFirst()
                .orElse(null);

        if (applications != null &&
                applications.getStatus() != ApplicationStatus.REJECTED &&
                applications.getStatus() != ApplicationStatus.WITHDRAWN) {
            throw new RuntimeException("Already applied for this job");
        }

        if (applications == null) {
            applications = new Applications();
            applications.setJob(job);
            applications.setSeeker(seeker);
        }

        if (!hasAnyResumeData(seeker.getId()) && !hasStructuredResumeSummary(req.getCoverLetter())) {
            throw new RuntimeException(
                    "Please upload a resume or create your resume in Resume Builder before applying.");
        }

        applications.setCoverLetter(req.getCoverLetter());

        if (req.getResumeFileId() != null) {
            ResumeFiles resumeFile = resumeFileRepository.findById(req.getResumeFileId())
                    .orElseThrow(() -> new RuntimeException("Resume file not found"));
            if (resumeFile.getUser() == null || !resumeFile.getUser().getId().equals(seeker.getId())) {
                throw new RuntimeException("Selected resume file does not belong to this seeker.");
            }
            applications.setResumeFile(resumeFile);
        } else {
            // Auto-attach latest uploaded resume when caller does not pass resumeFileId.
            List<ResumeFiles> files = resumeFileRepository.findByUserId(seeker.getId());
            if (!files.isEmpty()) {
                applications.setResumeFile(files.get(files.size() - 1));
            }
        }

        applications.setStatus(ApplicationStatus.APPLIED);
        applications.setAppliedAt(java.time.LocalDateTime.now());

        Applications savedApp = applicationRepository.save(applications);

        // Notify Employer
        notificationService.createNotification(job.getEmployer().getId(),
                "New application received for " + job.getTitle() + " from " + seeker.getName(),
                "NEW_APPLICATION");

        // Notify Seeker
        notificationService.createNotification(seeker.getId(),
                "Application submitted for " + job.getTitle(),
                "APPLICATION_SUBMITTED");

        return applicationMapper.toDto(savedApp);
    }

    private boolean hasAnyResumeData(Long seekerId) {
        if (!resumeFileRepository.findByUserId(seekerId).isEmpty()) {
            return true;
        }
        if (resumeObjectiveRepository.findByUserId(seekerId).isPresent()) {
            return true;
        }
        if (!resumeEducationRepository.findByUserId(seekerId).isEmpty()) {
            return true;
        }
        if (!resumeExperienceRepository.findByUserId(seekerId).isEmpty()) {
            return true;
        }
        if (!resumeSkillRepository.findByUserId(seekerId).isEmpty()) {
            return true;
        }
        if (!resumeProjectRepository.findByUserId(seekerId).isEmpty()) {
            return true;
        }
        return !resumeCertificationRepository.findByUserId(seekerId).isEmpty();
    }

    private boolean hasStructuredResumeSummary(String coverLetter) {
        if (coverLetter == null || coverLetter.isBlank()) {
            return false;
        }

        String lower = coverLetter.toLowerCase();
        if (lower.contains("resume builder draft: present")) {
            return true;
        }

        String education = extractStructuredField(coverLetter, "Education:");
        String experience = extractStructuredField(coverLetter, "Experience:");
        String skills = extractStructuredField(coverLetter, "Skills:");

        return isMeaningful(education) || isMeaningful(experience) || isMeaningful(skills);
    }

    private String extractStructuredField(String text, String label) {
        for (String line : text.split("\\r?\\n")) {
            if (line == null) {
                continue;
            }
            String trimmed = line.trim();
            if (trimmed.startsWith(label)) {
                return trimmed.substring(label.length()).trim();
            }
        }
        return "";
    }

    private boolean isMeaningful(String value) {
        if (value == null || value.isBlank()) {
            return false;
        }
        String lower = value.trim().toLowerCase();
        return !lower.equals("n/a")
                && !lower.equals("not specified")
                && !lower.equals("no experience listed")
                && !lower.equals("na");
    }

    @Override
    public List<ApplicationResponse> getApplicationsBySeeker(Long seekerId) {
        return applicationRepository.findBySeekerId(seekerId).stream().map(applicationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApplicationResponse> getApplicationsByJob(Long jobId) {
        return applicationRepository.findByJobId(jobId).stream().map(applicationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ApplicationResponse updateApplicationStatus(Long applicationId, ApplicationStatus status) {
        Applications applications = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        String oldStatus = applications.getStatus().toString();
        applications.setStatus(status);
        Applications savedApplications = applicationRepository.save(applications);

        // Audit Trail
        ApplicationStatusHistory history = new ApplicationStatusHistory();
        history.setApplication(savedApplications);
        history.setOldStatus(oldStatus);
        history.setNewStatus(status.toString());
        statusHistoryRepo.save(history);

        // Notification
        notificationService.createNotification(applications.getSeeker().getId(),
                "Your application for " + applications.getJob().getTitle() + " has been updated to " + status,
                "APPLICATION_UPDATE");

        return applicationMapper.toDto(savedApplications);
    }

    @Override
    @Transactional
    public void withdrawApplication(Long applicationId, String reason) {
        Applications applications = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        applications.setStatus(ApplicationStatus.WITHDRAWN);
        applicationRepository.save(applications);

        // Save Reason
        if (reason != null && !reason.trim().isEmpty()) {
            WithdrawalReasons withdrawalReason = new WithdrawalReasons(applications, reason);
            withdrawalReasonsRepository.save(withdrawalReason);
        }

        // Notification to Employer
        notificationService.createNotification(applications.getJob().getEmployer().getId(),
                "A candidate has withdrawn from " + applications.getJob().getTitle(),
                "APPLICATION_WITHDRAWN");

        // Notify Seeker
        notificationService.createNotification(applications.getSeeker().getId(),
                "You withdrew your application for " + applications.getJob().getTitle(),
                "WITHDRAWAL_CONFIRMED");
    }

    @Override
    public void addNote(Long applicationId, String noteText) {
        Applications applications = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        ApplicationNotes note = new ApplicationNotes();
        note.setApplication(applications);
        note.setNote(noteText);
        noteRepo.save(note);
    }

    @Override
    public List<ApplicationNotes> getNotes(Long applicationId) {
        return noteRepo.findByApplicationId(applicationId);
    }

    @Override
    @Transactional
    public void bulkUpdateStatus(BulkStatusUpdateRequest request) {
        for (Long id : request.getApplicationIds()) {
            updateApplicationStatus(id, request.getStatus());
        }
    }

    @Override
    public ApplicationResponse getFullApplicationDetails(Long id) {
        Applications app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        // Details are already in the object if fetched eagerly or via lazy loading in
        // trans
        return applicationMapper.toDto(app);
    }

    @Override
    public List<ApplicationStatusHistory> getApplicationStatusHistory(Long applicationId) {
        return statusHistoryRepo.findAll().stream()
                .filter(h -> h.getApplication().getId().equals(applicationId))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteApplicationNote(Long noteId) {
        noteRepo.deleteById(noteId.intValue());
    }

    @Override
    public List<ApplicationResponse> getActiveApplications(Long seekerId) {
        return applicationRepository.findBySeekerId(seekerId).stream()
                .filter(a -> a.getStatus() != ApplicationStatus.REJECTED
                        && a.getStatus() != ApplicationStatus.WITHDRAWN)
                .map(applicationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApplicationResponse> getApplicationsByJobAndStatus(Long jobId, ApplicationStatus status) {
        return applicationRepository.findByJobId(jobId).stream()
                .filter(a -> a.getStatus() == status)
                .map(applicationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public long getTotalApplicationCount() {
        return applicationRepository.count();
    }

    @Override
    public long getTodayApplicationCount() {
        java.time.LocalDate today = java.time.LocalDate.now();
        return applicationRepository.findAll().stream()
                .filter(a -> a.getAppliedAt() != null && a.getAppliedAt().toLocalDate().equals(today))
                .count();
    }

    @Override
    public List<ApplicationResponse> searchApplications(String query) {
        String q = query.toLowerCase();
        return applicationRepository.findAll().stream()
                .filter(a -> (a.getSeeker().getName() != null && a.getSeeker().getName().toLowerCase().contains(q)) ||
                        (a.getJob().getTitle() != null && a.getJob().getTitle().toLowerCase().contains(q)))
                .map(applicationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<WithdrawalReasons> getWithdrawalReasons() {
        return withdrawalReasonsRepository.findAll();
    }
}
