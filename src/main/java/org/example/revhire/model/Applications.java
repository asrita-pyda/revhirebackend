package org.example.revhire.model;


import jakarta.persistence.*;
import org.example.revhire.enums.ApplicationStatus;

import java.time.LocalDateTime;

    @Entity
    @Table(
            name = "applications",
            uniqueConstraints = {
                    @UniqueConstraint(columnNames = {"job_id", "seeker_id"})
            }
    )
    public class Applications {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "job_id", nullable = false)
        private Long jobId;

        @Column(name = "seeker_id", nullable = false)
        private Long seekerId;

        @Column(name = "resume_file_id")
        private Long resumeFileId;

        @Column(columnDefinition = "TEXT")
        private String coverLetter;

        @Enumerated(EnumType.STRING)
        private ApplicationStatus status;

        @Column(name = "applied_at")
        private LocalDateTime appliedAt;

        public Applications() {

        }

        public Applications(Long jobId, Long seekerId, Long resumeFileId,
                           String coverLetter, ApplicationStatus status) {
            this.jobId = jobId;
            this.seekerId = seekerId;
            this.resumeFileId = resumeFileId;
            this.coverLetter = coverLetter;
            this.status = status != null ? status : ApplicationStatus.APPLIED;
            this.appliedAt = LocalDateTime.now();
        }


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getJobId() {
            return jobId;
        }

        public void setJobId(Long jobId) {
            this.jobId = jobId;
        }

        public Long getSeekerId() {
            return seekerId;
        }

        public void setSeekerId(Long seekerId) {
            this.seekerId = seekerId;
        }

        public Long getResumeFileId() {
            return resumeFileId;
        }

        public void setResumeFileId(Long resumeFileId) {
            this.resumeFileId = resumeFileId;
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


