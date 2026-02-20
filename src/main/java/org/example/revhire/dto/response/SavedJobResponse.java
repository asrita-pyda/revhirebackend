package org.example.revhire.dto.response;

import java.time.LocalDateTime;

public class SavedJobResponse {
    private Long id;
    private Long jobId;
    private String jobTitle;
    private String companyName;
    private LocalDateTime savedAt;

    public SavedJobResponse() {
    }

    public SavedJobResponse(Long id, Long jobId, String jobTitle, String companyName, LocalDateTime savedAt) {
        this.id = id;
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.savedAt = savedAt;
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

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalDateTime getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(LocalDateTime savedAt) {
        this.savedAt = savedAt;
    }
}

