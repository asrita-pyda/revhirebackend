package org.example.revhire.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employer_stats")
public class EmployerStats extends BaseEntity {

    @Id
    @Column(name = "employer_id")
    private Long employerId;

    @Column(name = "total_jobs")
    private Integer totalJobs;

    @Column(name = "active_jobs")
    private Integer activeJobs;

    @Column(name = "total_applications")
    private Integer totalApplications;

    @Column(name = "pending_reviews")
    private Integer pendingReviews;

    public EmployerStats() {
    }

    public EmployerStats(Long employerId, Integer totalJobs, Integer activeJobs,
                         Integer totalApplications, Integer pendingReviews) {
        this.employerId = employerId;
        this.totalJobs = totalJobs;
        this.activeJobs = activeJobs;
        this.totalApplications = totalApplications;
        this.pendingReviews = pendingReviews;
    }

    public Long getEmployerId() {
        return employerId;
    }

    public void setEmployerId(Long employerId) {
        this.employerId = employerId;
    }

    public Integer getTotalJobs() {
        return totalJobs;
    }

    public void setTotalJobs(Integer totalJobs) {
        this.totalJobs = totalJobs;
    }

    public Integer getActiveJobs() {
        return activeJobs;
    }

    public void setActiveJobs(Integer activeJobs) {
        this.activeJobs = activeJobs;
    }

    public Integer getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(Integer totalApplications) {
        this.totalApplications = totalApplications;
    }

    public Integer getPendingReviews() {
        return pendingReviews;
    }

    public void setPendingReviews(Integer pendingReviews) {
        this.pendingReviews = pendingReviews;
    }
}
