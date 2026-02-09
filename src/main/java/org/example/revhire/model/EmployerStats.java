package org.example.revhire.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employer_stats")
public class EmployerStats {

    @Id
    @Column(name = "employer_id")
    private int employerId;

    @Column(name = "total_jobs")
    private int totalJobs;

    @Column(name = "active_jobs")
    private int activeJobs;

    @Column(name = "total_applications")
    private int totalApplications;

    @Column(name = "pending_reviews")
    private int pendingReviews;


    public EmployerStats() {
    }

    public EmployerStats(int employerId, int totalJobs, int activeJobs,
                         int totalApplications, int pendingReviews) {
        this.employerId = employerId;
        this.totalJobs = totalJobs;
        this.activeJobs = activeJobs;
        this.totalApplications = totalApplications;
        this.pendingReviews = pendingReviews;
    }

    public int getEmployerId() {
        return employerId;
    }

    public void setEmployerId(int employerId) {
        this.employerId = employerId;
    }

    public int getTotalJobs() {
        return totalJobs;
    }

    public void setTotalJobs(int totalJobs) {
        this.totalJobs = totalJobs;
    }

    public int getActiveJobs() {
        return activeJobs;
    }

    public void setActiveJobs(int activeJobs) {
        this.activeJobs = activeJobs;
    }

    public int getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(int totalApplications) {
        this.totalApplications = totalApplications;
    }

    public int getPendingReviews() {
        return pendingReviews;
    }

    public void setPendingReviews(int pendingReviews) {
        this.pendingReviews = pendingReviews;
    }
}

