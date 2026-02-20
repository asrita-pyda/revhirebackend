package org.example.revhire.dto.response;

public class EmployerStatsResponse {
    private Long activeJobs;
    private Long totalApplications;
    private Long newApplications; // last 48 hours
    private Long filledPositions;

    public EmployerStatsResponse() {
    }

    public Long getActiveJobs() {
        return activeJobs;
    }

    public void setActiveJobs(Long activeJobs) {
        this.activeJobs = activeJobs;
    }

    public Long getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(Long totalApplications) {
        this.totalApplications = totalApplications;
    }

    public Long getNewApplications() {
        return newApplications;
    }

    public void setNewApplications(Long newApplications) {
        this.newApplications = newApplications;
    }

    public Long getFilledPositions() {
        return filledPositions;
    }

    public void setFilledPositions(Long filledPositions) {
        this.filledPositions = filledPositions;
    }
}

