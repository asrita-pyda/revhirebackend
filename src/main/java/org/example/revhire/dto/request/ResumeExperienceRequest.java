package org.example.revhire.dto.request;


public class ResumeExperienceRequest {
    private String company;
    private String jobTitle;
    private String duration;
    private String description;

    public ResumeExperienceRequest() {
    }

    public ResumeExperienceRequest(String company, String jobTitle, String duration, String description) {
        this.company = company;
        this.jobTitle = jobTitle;
        this.duration = duration;
        this.description = description;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
