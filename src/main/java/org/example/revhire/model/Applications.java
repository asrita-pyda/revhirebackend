package org.example.revhire.model;
import jakarta.persistence.*;
import org.example.revhire.enums.ApplicationStatus;
import java.time.LocalDateTime;

@Entity
@Table(name = "applications", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "job_id", "seeker_id" })
})
public class Applications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seeker_id", nullable = false)
    private User seeker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_file_id")
    private ResumeFiles resumeFile;

    @Column(columnDefinition = "TEXT")
    private String coverLetter;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(name = "applied_at")
    private LocalDateTime appliedAt;

    public Applications() {
    }

    public Applications(Long id, Job job, User seeker, ResumeFiles resumeFile, String coverLetter,
                        ApplicationStatus status) {

        this.id = id;
        this.job = job;
        this.seeker = seeker;
        this.resumeFile = resumeFile;
        this.coverLetter = coverLetter;
        this.status = status != null ? status : ApplicationStatus.APPLIED;
        this.appliedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public User getSeeker() {
        return seeker;
    }

    public void setSeeker(User seeker) {
        this.seeker = seeker;
    }

    public ResumeFiles getResumeFile() {
        return resumeFile;
    }

    public void setResumeFile(ResumeFiles resumeFile) {
        this.resumeFile = resumeFile;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }
}
